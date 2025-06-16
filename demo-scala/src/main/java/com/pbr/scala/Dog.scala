package com.pbr.scala

/**
 * @author Lawrence Peng
 */
class Dog(br: String) {

  // another constructor
  def this() {
    this("")
    println("No last name or age given.")
  }

  // Constructor code here
  var breed: String = ""

  // Define a method called bark, returning a String
  def bark = "Woof, woof!"

  // Values and methods are assumed public. "protected" and "private" keywords
  // are also available.
  private def sleep(hours: Int) =
    println(s"I'm sleeping for $hours hours")

}


object Dog {

  def allKnownBreeds = {
    List("pitbull", "shepherd", "retriever")
  }

  def createDog(breed: String) = {
    new Dog(breed)
  }

  def createDog2() = {
    new Dog()
  }

  def main(args: Array[String]): Unit = {
    createDog2()

  }
}

// case class could not have same name as normal Class Dog
case class Person(name: String, phoneNumber: String)

object MyTestObject{

  // Create a new instance. Note cases classes don't need "new"
  val george: Person = Person("George", "1234")
  val kate: Person = Person("Kate", "4567")

  // With case classes, you get a few perks for free, like getters:
  george.phoneNumber // => "1234"

  // Per field equality (no need to override .equals)
  Person("George", "1234") == Person("Kate", "1236") // => false

  // Easy way to copy
  // otherGeorge == Person("George", "9876")
  val otherGeorge: Person = george.copy(phoneNumber = "9876")

}



trait Composition {
  def compose(): String
}

class Score extends Composition {
  override def compose(): String = s"The score is composed by"
}
