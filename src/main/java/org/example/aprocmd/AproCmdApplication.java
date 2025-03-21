package org.example.aprocmd;

import org.example.aprocmd.socket.TcpServerBootStraper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class AproCmdApplication {

    public static void main(String[] args) {
        SpringApplication.run(AproCmdApplication.class, args);

    }

}
