package main

import (
	"fmt"
	"time"
)

func main() {
	c1 := make(chan string)
	c2 := make(chan string)

	go func() {
		time.Sleep(2 * time.Second)
		println("f1")
		c1 <- "one"
	}()
	go func() {
		time.Sleep(2 * time.Second)
		println("f2")
		c2 <- "two"
		println("f2.1")
	}()

	select {
	case msg1 := <-c1:
		fmt.Println("received", msg1)
	case msg2 := <-c2:
		fmt.Println("received", msg2)
	}
}
