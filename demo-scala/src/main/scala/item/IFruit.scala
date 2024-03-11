package item

import java.time.LocalDateTime


trait IFruit {

  def getName: String

}

class Apple extends IFruit {

  // 成员变量: 只有 trait 和 abstract 的 class 可以不用初始化成员变量, 其他都需要初始化, 这一点和 java 是不同的,
  var name: String = null

  override def getName: String = if (this.name == null) "Apple " + LocalDateTime.now() else this.name

}
