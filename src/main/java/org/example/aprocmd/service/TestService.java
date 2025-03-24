package org.example.aprocmd.service;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.aprocmd.domain.command.CommandHelper;
import org.example.aprocmd.util.ByteUtil;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.netty.tcp.TcpClient;


@Slf4j
@Service
@RequiredArgsConstructor
public class TestService {

    private Channel channel;
    private TcpClient tcpClient;

    private final CommandHelper commandHelper;
    private final RequestCommandService requestCommandService;

    public Mono<String> test() {
        tcpClient = TcpClient.create()
                .host("192.168.1.2")
                .port(9013)
                .doOnConnected(conn -> {
                    this.channel = conn.channel();
                    log.info("Connected");
                    //byte[] startCommand = commandService.createPacket(Command.ST,
                    //LocalDateTime.of(2025, 3, 21, 16, 23, 0));
                    // log.info("Start command: " + Arrays.toString(startCommand));
                    sendMessage(null);
                });

        tcpClient
                .handle((in, out) -> {
                    // 서버로 메시지 전송
                    return in.receive()
                            .asByteArray()
                            .doOnNext(response -> {
                                log.info("서버 응답: {} ", ByteUtil.byteArrayToHexString(response));
                            }).then();
                })
                .connect()
                .doOnTerminate(() -> System.out.println("연결 종료"))
                .subscribe();

        return Mono.just("Test");
    }

    void sendMessage(byte[] message) {
        String example = "02fe5354080001011903110e23364503";
        byte[] bytes = ByteUtil.hexStringToByteArray(example);
        ByteBuf byteBuf = Unpooled.buffer(16);
        ByteBuf hexStringToByte = byteBuf.writeBytes(bytes);
        ByteBuf writeBytes = channel.alloc().buffer().writeBytes(hexStringToByte);
        channel.writeAndFlush(writeBytes).addListener((event) -> log.info("Send message: " + event.get()));
    }
}
