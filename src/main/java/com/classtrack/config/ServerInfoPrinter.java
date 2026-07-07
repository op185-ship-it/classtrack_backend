package com.classtrack.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.net.InetAddress;

@Component
public class ServerInfoPrinter implements CommandLineRunner {

    @Value("${server.port:8080}")
    private int port;

    @Value("${flask.service.url}")
    private String flaskUrl;

    @Value("${esp32.cam.url}")
    private String esp32Url;

    @Override
    public void run(String... args) throws Exception {

        String localhost = "http://localhost:" + port;

        String networkIp = InetAddress.getLocalHost().getHostAddress();
        String networkUrl = "http://" + networkIp + ":" + port;

        System.out.println();
        System.out.println("==========================================");
        System.out.println(" Spring Boot Started Successfully");
        System.out.println("------------------------------------------");
        System.out.println(" Local URL   : " + localhost);
        System.out.println(" Network URL : " + networkUrl);
        System.out.println(" esp32  URL : " + esp32Url);
        System.out.println(" flask  URL : " + flaskUrl);
        System.out.println("==========================================");
    }
}