package org.example.aprocmd.service;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.aprocmd.domain.command.Command;
import org.example.aprocmd.domain.command.CommandHelper;
import org.example.aprocmd.util.ByteUtil;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.netty.tcp.TcpClient;

import java.time.LocalDateTime;
import java.util.Arrays;


@Slf4j
@Service
@RequiredArgsConstructor
public class TestService {

    private Channel channel;
    private TcpClient tcpClient;

    private final CommandHelper commandHelper;

    public Mono<String> test() {
        tcpClient = TcpClient.create()
                .host("192.168.1.2")
                .port(9013)
                .doOnConnected(conn -> {
                    this.channel = conn.channel();
                    log.info("Connected");
                    byte[] startCommand = commandHelper.createPacket(Command.ST,
                            LocalDateTime.of(2025, 3, 21, 16, 23, 0));
                    log.info("Start command: " + Arrays.toString(startCommand));
                    sendMessage(startCommand);
                });

       tcpClient
                .handle((in, out) -> {
                    // 서버로 메시지 전송
                    return in.receive()
                            .asByteArray()
                            .doOnNext(response -> {
                                log.info("서버 응답: {} " + ByteUtil.byteArrayToHexString(response));
                            }).then();
                })
                .connect()
                .doOnTerminate(() -> System.out.println("연결 종료"))
                .subscribe();

        return Mono.just("Test");
    }

    void sendMessage(byte[] message) {
        ByteBuf byteBuf = Unpooled.buffer(16);
        ByteBuf hexStringToByte = byteBuf.writeBytes(message);
        ByteBuf writeBytes = channel.alloc().buffer().writeBytes(hexStringToByte);
        channel.writeAndFlush(writeBytes).addListener((event) -> log.info("Send message: " + event.get()));
    }
}
