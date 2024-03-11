package com.pbr.akka.official.greet.message;

import akka.actor.typed.ActorRef;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public final class Greeted {

    public final String whom;

    public final ActorRef<Greet> fromActorRef;

}
