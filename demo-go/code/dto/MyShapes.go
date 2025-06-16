package dto

// Shape Go 的继承看上去是依赖方法签名。
type Shape interface {
	Area() float64
}

type Rectangle struct {
	Width  float64
	Height float64
}

func (thisObject Rectangle) Area() float64 {
	return thisObject.Width * thisObject.Height
}

type Circle struct {
	Radius float64
}

func (thisObject Circle) Area() float64 {
	return 3.14 * thisObject.Radius * thisObject.Radius
}
