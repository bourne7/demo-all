package com.pbr.akka.official.greet;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import com.pbr.akka.official.greet.message.Greet;
import com.pbr.akka.official.greet.message.Greeted;
import com.pbr.akka.official.greet.message.SayHello;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HelloWorldMain extends AbstractBehavior<SayHello> {

    public static Behavior<SayHello> create() {
        log.info("2.1 factory");
        // 注意这里是传入了一个工厂方法。
        return Behaviors.setup(context -> new HelloWorldMain(context));
    }

    private final ActorRef<Greet> greetActor;

    private HelloWorldMain(ActorContext<SayHello> context) {
        super(context);
        log.info("2.2.1 spawn build factory，线程不安全。");
        greetActor = context.spawn(HelloWorld.create(), "GreeterActor_By_HelloWorldMain");
        log.info("2.2.2 end spawn");
    }

    // 这个方法并非和 create 同一个线程执行，但是仍然会在创建的时候就执行，而不是收到消息的时候。也就是至少这里没有用延时加载。
    @Override
    public Receive<SayHello> createReceive() {
        // Implement this to define how messages and signals are processed.
        log.info("2.3");
        return super.newReceiveBuilder().onMessage(SayHello.class, this::_onStart).build();
    }

    /**
     * 这里等于是收到了一个命令以后，会重新新建消息，然后发出去。
     */
    private Behavior<SayHello> _onStart(SayHello message) {

        log.info("2.4 创建接收者，注意这里是多例创建，可能是故意这么做的，为了演示 {}", message);
        ActorRef<Greeted> sendMessageToActor = super.getContext().spawn(HelloWorldBot.create(5), message.name);

        log.info("2.5 然后发送消息出去，注意这里才规定好了消息接收者处理完以后，需要将消息送到哪里去。 {}", message);
        greetActor.tell(new Greet(message.name, sendMessageToActor));

        return this;
    }
}