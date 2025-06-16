package com.example;

import akka.actor.typed.ActorSystem;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class AkkaQuickstart {

    public static void main(String[] args) {

        log.info("Hello Akka!");

        // actor-system
        final ActorSystem<GreeterMain.SayHello> greeterMain = ActorSystem.create(GreeterMain.create(), "helloakka");

        // main-send-messages
        greeterMain.tell(new GreeterMain.SayHello("Charles"));

        try {
            System.out.println(System.lineSeparator() + ">>> Press ENTER to exit <<<" + System.lineSeparator());
            System.in.read();
        } catch (IOException ignored) {

        } finally {
            greeterMain.terminate();
        }
    }

}
