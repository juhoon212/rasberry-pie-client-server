package org.example.aprocmd.infrastructure.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.aprocmd.infrastructure.dto.SocketResponseDto;
import org.example.aprocmd.util.ByteUtil;
import org.example.aprocmd.util.DateUtil;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.Connection;
import reactor.netty.tcp.TcpClient;


import static org.example.aprocmd.util.CommandUtil.*;


@Slf4j
@Component
@RequiredArgsConstructor
public class RequestCommandHandler {
    private Channel channel;

    public Mono<? extends Connection> sendMessage(final byte[] message, final int dataLength) {
        return TcpClient.create()
                .host(HOST)
                .port(PORT)
                .doOnConnected(conn -> {
                    conn.addHandlerLast(new ReadTimeoutHandler(20));
                    this.channel = conn.channel();
                    log.info("Connected");
                    sendByteBuf(message, dataLength);
                })
                .doOnDisconnected(conn -> log.info("Request Connection disconnected"))
                .connect();
    }

    private void sendByteBuf(byte[] message, int dataLength) {
        ByteBuf byteBuf = Unpooled.buffer(dataLength);
        ByteBuf writeBytes = byteBuf.writeBytes(message);
        ByteBuf messageByteBuf = channel.alloc().buffer().writeBytes(writeBytes);
        channel.writeAndFlush(messageByteBuf)
                .addListener((event) -> log.info("Send message: {}", ByteUtil.byteArrayToHexString(message)));
    }
}
