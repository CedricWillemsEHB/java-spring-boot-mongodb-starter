package com.mongodb.starter;

import com.ehb.dnd.Server.GameServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApplicationStarter {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationStarter.class, args);
       /* GameServer gs = new GameServer();
        gs.acceptConnections();*/
    }
}
