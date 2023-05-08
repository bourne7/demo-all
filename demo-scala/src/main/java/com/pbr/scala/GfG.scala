package com.pbr.scala

import com.pbr.scala.MoneySyntax.RichMoney

// Scala program of utilizing
// None with Scala's Option

// Creating object
object GfG {

  // Main method
  def main(args: Array[String]) {
    // Applying None with
    // Scala's Option
    val option: Option[Int] = None

    var i = option.getOrElse(51)
    // 注意，这里用 null 也是可以的，目前感觉多此一举。

    // Displays output
    println(option)

    var myList = "A" :: "B" :: "B" :: Nil

    myList = "C" :: myList

    myList = myList :+ "D"

    println(myList)

  }

}

class GfG[A] {

  /**
   * 都是语法糖。下面这2种写法是一样的。
   *
   * 要注意这里用了 implicit ev: Null <:< A1
   * ev 表示一个
   */
  @inline final def orNull[A1 >: A](implicit ev: Null <:< A1): A1 = getOrElse(ev(null))

  @inline final def getOrElse[A1 >: A](defaultValue: => A1): A1 = defaultValue

}

