package com.pbr.akka.official.greet;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import com.pbr.akka.official.greet.message.Greet;
import com.pbr.akka.official.greet.message.Greeted;
import lombok.extern.slf4j.Slf4j;

/**
 * <a href="https://doc.akka.io/docs/akka/current/typed/actors.html">Akka Actors</a>
 *
 * @author Lawrence Peng
 */
@Slf4j
public class HelloWorld extends AbstractBehavior<Greet> {

    /**
     * 这个方法需要手动调用。目的是创建一个工厂。实际的 Actor 是延时创建的，会等到第一个消息到达的时候才创建。标志就是
     * createReceive 被调用了
     */
    public static Behavior<Greet> create() {
        log.info("3.1 创建打招呼者");
        // 这里包含了一个隐式的参数：ActorContext，会等到创建的时候才赋值。
        // 这里也是交给别的线程完成的。
        return Behaviors.setup(HelloWorld::new);
    }

    private HelloWorld(ActorContext<Greet> context) {
        super(context);
        log.info("3.2");
    }

    @Override
    public Receive<Greet> createReceive() {
        log.info("3.3");
        return super.newReceiveBuilder().onMessage(Greet.class, this::_onGreet).build();
    }

    private Behavior<Greet> _onGreet(Greet message) {

        log.info("3.4 Hello {}!", message.whom);

        // 收到了一个消息以后，手动调用需要返回的消息。这里可以简单包装一下，成为自动回复。
        message.replyToActorRef.tell(new Greeted(message.whom, getContext().getSelf()));

        return this;
    }
}