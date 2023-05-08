package com.pbr.scala

/**
 * https://learnxinyminutes.com/docs/scala/
 *
 * @author Lawrence Peng
 */
object TestScala {

  def main(args: Array[String]): Unit = {
    //    println(sum1(1, 2, 3))
    //
    //    println(sum2(1, 2, 3))
    //
    //    (5 to 1 by -1) foreach (println)

  }

  def add(n: Int, m: Int): Int = n + m //式3.1

  def sum1(ns: Int*): Int = ns.foldLeft(0)(add) //式3.2

  def sum2(ns: Int*): Int = ns.foldLeft(0)((n, m) => return n + m) //式3.3

}

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