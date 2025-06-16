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
 * @author Lawrence Peng
 */
@Slf4j
public class HelloWorldBot extends AbstractBehavior<Greeted> {

    public static Behavior<Greeted> create(int max) {
        log.info("4.1 创建 Bot");
        return Behaviors.setup(context -> new HelloWorldBot(context, max));
    }

    private final int max;

    /**
     * https://doc.akka.io/docs/akka/current/typed/actors.html#introduction-to-actors
     * <p>
     * 神奇的单例 + 单个消息消费。 所以这里不用 concurrent 包里面的东西，效率更高。
     * <p>
     * Note how this Actor manages the counter with an instance variable.
     * No concurrency guards such as synchronized or AtomicInteger are needed since an actor instance processes
     * one message at a time.
     */
    private int greetingCounter;

    private HelloWorldBot(ActorContext<Greeted> context, int max) {
        super(context);
        log.info("4.2");
        this.max = max;
    }

    @Override
    public Receive<Greeted> createReceive() {
        log.info("4.3");
        return newReceiveBuilder().onMessage(Greeted.class, this::_onGreeted).build();
    }

    private Behavior<Greeted> _onGreeted(Greeted message) {

        greetingCounter++;

        getContext().getLog().info("4.4 Greeting {} for {}", greetingCounter, message.whom);

        if (greetingCounter == max) {

            return Behaviors.stopped();

        } else {

            message.fromActorRef.tell(new Greet(message.whom, getContext().getSelf()));

            return this;
        }
    }
}