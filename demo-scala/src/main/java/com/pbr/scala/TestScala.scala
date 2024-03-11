package com.pbr.scala

/**
 * https://learnxinyminutes.com/docs/scala/
 *
 * @author Lawrence Peng
 */
object TestScala {

  def main(args: Array[String]): Unit = {
    //    println(sum1(1, 2, 3, 4))
    //    println(sum2(1, 2, 3))

    // (5 to 1 by -1) foreach println

    // testImplicit()

    //    testTypeConstraint()

    // 1 :: List(2, 3) = List(2, 3).::(1) = List(1, 2, 3)
    val consList = "A" :: "B" :: Nil

    println(consList)

  }

  private def testTypeConstraint(): Unit = {
    def foo[T](parameter1: T)(implicit ev: T <:< Serializable): T = parameter1
    // foo(1) // compile error
    foo("hi")

    def bar[T <: Serializable](parameter1: T): T = parameter1
    // bar(1) // compile error
    bar("hi")


    // 这里可以看出 <:< 和 <: 的区别： <:< 可以限制参数的类型， <: 也能。
    // 为啥要2个呢？我看到源码的解释是：<: 会覆盖全局泛型，而 <:< 不会。好吧，这个确实是一个理由。但是我认为还不够强，总不能因为这个就加这么复杂的一个机制吧？

    // 使用方便，<:< 用于隐式， <: 用于泛型的类型约束。
    def feedAnimal[A, B](parameter1: A)(implicit ev: A <:< Cat): Unit = {
      println("Feeding the " + parameter1.getClass.getSimpleName)
    }

    feedAnimal(new Cat)
    //    feedAnimal(new Animal)


    def feedAnimal2[A <: Cat, B](parameter1: A): Unit = {
      println("Feeding the " + parameter1.getClass.getSimpleName)
    }

    feedAnimal2(new Cat)
    //    feedAnimal2(new Animal)
    //    feedAnimal2("1")
  }

  def concat[T <: String](a: List[T], b: List[T])(implicit ev: T <:< String): String = a.mkString + b.mkString


  private def testImplicit(): Unit = {
    // https://www.baeldung.com/scala/implicit-parameters
    implicit val prefix: String = "["
    implicit val suffix: String = "]"
    // 部分的隐式参数也是无法编译通过的。
    //    printWithPrefix("Hello, world!")(prefix = "[[")
    // 可以全隐式简介传参
    printWithPrefix("Hello, world!")(prefix, suffix)
    // 可以全隐式直接赋值，不过这样是没有意义的，还不如普通参数
    printWithPrefix("Hello, world!")("[", "]")
    // 可以指定名称进行传参
    printWithPrefix("Hello, world!")(prefix = "[", suffix = "]")
  }

  def add(n: Int, m: Int): Int = n + m //式3.1

  def sum1(ns: Int*): Int = ns.foldLeft(0)(add) //式3.2

  // 谨慎使用 return https://www.cnblogs.com/binbingg/p/14107456.html
  // 和 Java 的不同，这里的 return 会穿透到更上层，导致无法继续累加。虽然直觉上这种和上面 3.2 的写法一样，但是实际效果完全不同。
  def sum2(ns: Int*): Int = ns.foldLeft(0)((n, m) => return n + m) //式3.3

  def greet(name: String)(implicit greeting: String, greeting2: String): String = s"$greeting, $greeting2, $name!"


  // test implicit
  private def printWithPrefix(msg: String)(implicit prefix: String, suffix: String): Unit = {
    println(s"$prefix$msg$suffix")
  }
}

class ClassA

class ClassB extends ClassA

class Animal

class Cat extends Animal

// https://www.jianshu.com/p/32bc7f69af8c
// 声明一个枚举名称为 EnumerationDemo
object EnumerationDemo extends Enumeration {
  // 声明了一个类型别名。和这个枚举名字相同，其实并不是必须的，也可以定义成其他名称。但是为了代码的可读性，一般来说定义成相同的。
  type EnumerationDemo = Value

  val red, green, blue, black = Value
  val yellow = Value(100, "Yellow")
}


object ABC {
  def main(args: Array[String]): Unit = {
    val abc = new ABC("B")
    abc.info()
  }
}

class ABC(var str: String = "A") {
  def info(): Unit = {
    println("info: " + str)
  }
}