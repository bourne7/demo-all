package com.pbr.akka.official.greet;

import akka.actor.typed.ActorSystem;
import com.pbr.akka.official.greet.message.SayHello;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Lawrence Peng
 */
@Slf4j
public class Main {

    public static void main(String[] args) {

        log.info("Starting...");

        log.info("1 Create system and the first actor. This could be the guardian");
        // 里面的执行是交给别的线程完成的。
        final ActorSystem<SayHello> system =
                ActorSystem.create(HelloWorldMain.create(), "ActorSystem_Main");

        log.info("1.2 Send World");
        // 在执行这句以后，就会开始实例化 SayHelloActor
        system.tell(new SayHello("World"));

        log.info("1.3 Send Akka 注意这里是异步发送，这2条消息会瞬间发出，不会阻塞。此时 Main 方法就没了。");
        system.tell(new SayHello("Akka"));

        system.terminate();

        log.info("Ended...");
    }
}
