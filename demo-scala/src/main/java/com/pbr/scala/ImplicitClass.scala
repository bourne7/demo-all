package com.pbr.scala

// 这里不能同时存在 import com.pbr.scala.MoneySyntax.RichMoney

import com.pbr.scala.MoneySyntax.RichMoney2

/**
 * https://www.baeldung.com/scala/implicit-classes
 *
 * @author Lawrence Peng
 */
class ImplicitClass {

}

sealed trait Currency

object Currency {
  case object EUR extends Currency

  case object USD extends Currency

  case object GBP extends Currency
}

case class Money(amount: Double, currency: Currency)

object MoneySyntax {

  implicit class RichMoney(val amount: Double) extends AnyVal {
    def euros: Money = Money(amount, Currency.EUR)

    def dollars: Money = Money(amount, Currency.USD)

    def pounds: Money = Money(amount, Currency.GBP)
  }

  /**
   * 这里就相当于 Java 里面的引入了静态方法。由于省略了方法名，只用参数进行分派，所以相同参数智能注入一个。我认为不太实用。
   * Unfortunately, not all classes can be implicit classes because
   *
   * They cannot be defined as top-level objects
   * They cannot take multiple non-implicit arguments in their constructor
   * We cannot use implicit classes with case classes
   * There cannot be any member or object in scope with the same name as the implicit class
   */
  implicit class RichMoney2(val amount: Double) extends AnyVal {
    def euros: Money = Money(amount, Currency.EUR)

    def dollars: Money = Money(amount, Currency.USD)

    def pounds: Money = Money(amount, Currency.GBP)
  }
}

object MyMainClass {
  val amount: Double = 30.5

  // 下面这3种调用形式一样
  amount.dollars
  amount.euros
  amount.pounds

  MoneySyntax.RichMoney(amount).dollars
  MoneySyntax.RichMoney(amount).euros
  MoneySyntax.RichMoney(amount).pounds

  Money(amount, Currency.USD)
  Money(amount, Currency.EUR)
  Money(amount, Currency.GBP)

  var `a` = 1
}
