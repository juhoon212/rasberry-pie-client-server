package org.example.aprocmd.domain.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.aprocmd.util.ByteUtil;
import org.springframework.stereotype.Component;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.netty.tcp.TcpClient;

import static org.example.aprocmd.util.CommandUtil.*;


@Slf4j
@Component
@RequiredArgsConstructor
public class RequestCommandHandler {
    private Channel channel;

    public Mono<?> sendMessage(final byte[] message, final int dataLength) {
        TcpClient tcpClient = TcpClient.create()
                .host(HOST)
                .port(PORT)
                .doOnConnected(conn -> {
                    this.channel = conn.channel();
                    log.info("Connected");
                    ByteBuf byteBuf = Unpooled.buffer(dataLength);
                    ByteBuf writeBytes = byteBuf.writeBytes(message);
                    ByteBuf messageByteBuf = channel.alloc().buffer().writeBytes(writeBytes);
                    channel.writeAndFlush(messageByteBuf).addListener(
                            (event) -> log.info("Send message: {}", ByteUtil.byteArrayToHexString(message)));
                })
                .doOnDisconnected(conn -> log.info("Request Connection disconnected"));

        tcpClient
                .handle((in, out) -> in.receive()
                        .asByteArray()
                        .doOnNext(response -> {
                            log.info("Server response: {}", ByteUtil.byteArrayToHexString(response));
                        })
                        .then())
                .connect()
                .doOnSuccess(s -> log.info("Response Connection Success"))
                .doOnError(e -> log.error("Response Error: ", e))
                .subscribeOn(Schedulers.boundedElastic())
                .subscribe();




        return Mono.empty();

    }
}
