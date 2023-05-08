package main

import "fmt"

type Shape interface {
	area() float64
}

type Rectangle struct {
	width  float64
	height float64
}

func (thisObject Rectangle) area() float64 {
	return thisObject.width * thisObject.height
}

type Circle struct {
	radius float64
}

func (thisObject Circle) area() float64 {
	return 3.14 * thisObject.radius * thisObject.radius
}

func main() {
	var s Shape

	s = Rectangle{width: 10, height: 5}
	fmt.Println("矩形面积: %f", s.area())

	s = Circle{radius: 3}
	fmt.Println("圆形面积: %f", s.area())

	fmt.Println("=====================================")
	r1 := Rectangle{width: 11, height: 5}
	fmt.Printf("r1 width: %f\n", r1.width)
	fmt.Printf("r1 address: %p\n", &r1)
	testPoint(r1)
	fmt.Printf("r1 width: %f\n", r1.width)
}

func testPoint(input Rectangle) {

	fmt.Printf("input width 1: %f\n", input.width)
	fmt.Printf("input address 1: %p\n", &input)
	input.width = 100
	fmt.Printf("input width 2: %f\n", input.width)
	fmt.Printf("input address 2: %p\n", &input)
}
