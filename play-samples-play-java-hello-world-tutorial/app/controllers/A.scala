package controllers

import akka.dispatch.CachingConfig.nonExistingPathEntry.config

/**
 * @author Lawrence Peng
 */
class A {
  val asyncMode = config.getString("task-peeking-mode") match {
    case "FIFO" => true
    case "LIFO" ⇒ false
    case unsupported ⇒ throw new IllegalArgumentException("Cannot instantiate ForkJoinExecutorServiceFactory. " +
      """"task-peeking-mode" in "fork-join-executor" section could only set to "FIFO" or "LIFO".""")
  }


}
