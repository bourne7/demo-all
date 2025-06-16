package item


// 伴生对象，与类名字相同，可以访问类的私有属性和方法
object Marker {

  println("创建 object Marker " + this)

  private val markerMap: Map[String, Marker] = Map(
    "red" -> new Marker("red"),
    "blue" -> new Marker("blue"),
    "green" -> new Marker("green")
  )

  // apply 是特殊的方法, 等同于构造方法.
  def apply(color: String): Marker = {
    markerMap.get(color).orNull
  }

  def getMarker(color: String): Marker = {
    if (markerMap.contains(color)) markerMap(color) else null
  }

  def main(args: Array[String]): Unit = {
    println(Marker("red"))
    // 单例函数调用，省略了.(点)符号
    println(Marker getMarker "blue")
  }
}

// 私有构造方法
class Marker private(val color: String) {

  var size: Int = 1

  def apply(size: Int): Unit = {

    this.size = size

  }

  println("创建 class Marker " + this)

  override def toString: String = {
    return "颜色标记：" + color
  }

}


case class Person(name: String, phoneNumber: String)