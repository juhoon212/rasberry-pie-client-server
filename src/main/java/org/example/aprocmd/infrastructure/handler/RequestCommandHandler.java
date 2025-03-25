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
import reactor.netty.tcp.TcpClient;


import static org.example.aprocmd.util.CommandUtil.*;


@Slf4j
@Component
@RequiredArgsConstructor
public class RequestCommandHandler {
    private Channel channel;


    public Flux<SocketResponseDto> sendMessage(final byte[] message, final int dataLength) {
        TcpClient tcpClient = TcpClient.create()
                .host(HOST)
                .port(PORT)
                .doOnConnected(conn -> {
                    conn.addHandlerLast(new ReadTimeoutHandler(20));
                    this.channel = conn.channel();
                    log.info("Connected");
                    ByteBuf byteBuf = Unpooled.buffer(dataLength);
                    ByteBuf writeBytes = byteBuf.writeBytes(message);
                    ByteBuf messageByteBuf = channel.alloc().buffer().writeBytes(writeBytes);
                    channel.writeAndFlush(messageByteBuf)
                            .addListener((event) -> log.info("Send message: {}", ByteUtil.byteArrayToHexString(message)));
                })
                .doOnDisconnected(conn -> log.info("Request Connection disconnected"));


        return tcpClient
                .connect()
                .flatMapMany(
                        conn -> conn
                                .inbound()
                                .receive()
                                .asByteArray()
                                .flatMap(response -> {
                                    String hexString = ByteUtil.byteArrayToHexString(response);
                                    SocketResponseDto socketResponseDto = new SocketResponseDto(
                                            hexString, DateUtil.getCurrentLocalDateTime());
                                    return Mono.just(socketResponseDto);
                                })
                                .doOnNext(response -> {
                                    log.info("Server response: {}", response.data());
                                }));
    }
}
