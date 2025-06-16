package com.example;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

public class Greeter extends AbstractBehavior<Greeter.Greet> {

    private Greeter(ActorContext<Greet> context) {
        super(context);
    }

    public static Behavior<Greet> create() {
        return Behaviors.setup(Greeter::new);
    }

    @Override
    public Receive<Greet> createReceive() {

        return newReceiveBuilder().onMessage(Greet.class, this::onGreet).build();

    }

    private Behavior<Greet> onGreet(Greet command) {

        getContext().getLog().info("Hello {}!", command.whom());

        //#greeter-send-message
        command.replyTo().tell(new Greeted(command.whom(), getContext().getSelf()));

        return this;
    }

    public record Greet(String whom, ActorRef<Greeted> replyTo) {
    }

    public record Greeted(String whom, ActorRef<Greet> from) {
    }
}

