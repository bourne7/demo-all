import item.Marker

import java.util
import scala.collection.mutable

/**
 * @author Lawrence Peng
 */
object TestCollection {

  def main(args: Array[String]): Unit = {

    // testCollection()
    // testLambda()
     testGroupBy()

    println("=" * 20)
    val numbers = Set(1, 2, 3, 4, 5, 6, 7)


    //    var result = for {v <- Set(1, 2, 3, 4, 5, 6, 7);  y = case (v, v+" string") if v < 7} yield v
    //
    //    result.toSeq.collect()

    var a: Map[Int, String] = (for (name <- numbers) yield {
      (name, name + " String")
    }).toMap


    println(a.getClass)

  }

  def testCollection(): Unit = {
    println("Hello world!")

    val map: Map[String, Char] = Map("A" -> 'A')
    println(map)

    val javaMap: util.Map[String, Any] = new util.HashMap[String, Any]()
    javaMap.put("A", 'a')
    println(javaMap)

    val map2: mutable.Map[String, Any] = new mutable.HashMap[String, Any]()
    map2.put("A", 'a')
    println(map2)
  }

  def testLambda(): Unit = {
    val aListOfNumbers = List(1, 2, 3, 4, 10, 20, 100)
    aListOfNumbers foreach ((x: Int) => println(x))
    aListOfNumbers foreach (x => println(x))
    aListOfNumbers foreach println
  }

  def testGroupBy(): Unit = {

    val sports = Seq("baseball", "ice hockey", "football", "basketball", "110m hurdles", "field hockey")

    var a: Map[Char, Seq[String]] = sports.groupBy((v: String) => v.charAt(0))

    println(a)
  }

}
