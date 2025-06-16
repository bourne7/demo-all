package main

import (
	"demo-go/code/dto"
	"demo-go/code/network"
	"demo-go/code/utils"
	"errors"
	"fmt"
	"github.com/yuin/gopher-lua"
	"gopkg.in/natefinch/lumberjack.v2"
	"io"
	"log"
	"os"
	"reflect"
	"strings"
	"sync"
	"time"
	"unsafe"
)

func main() {

	//testLuaScript()
	//testError()
	//testPassPointerAsKey()
	//testCompareStringAddress()
	//testSyncMapConcurrent2()
	//testSyncMapConcurrent()
	//testSyncMap()
	//testPointer()
	//testLog()
	//testRedis()
	//testObjectInit()
	//funcMonotonic()
	//testDate()
	//demoPointer()
	//funcCalcByChannel()
	//testInterface()
	//testGoroutine()

}

func testLuaScript() {
	// create a new Lua state
	L := lua.NewState()
	defer L.Close()

	// execute some Lua code
	if err := L.DoString("print('Hello, world!')"); err != nil {
		panic(err)
	}
}

func testError() {
	err := errors.New("MyError")

	log.Printf("=== %s", err)
}

func testPassPointerAsKey() {
	var accessCountMap = sync.Map{}

	//book1 := dto.Book{
	//	Name: "book1",
	//}

	book1 := "book1"

	book2 := &book1

	accessCountMap.Store(*book2, 1)
	accessCountMap.Store("test", 1)
	accessCountMap.Store(book2, 1)
	accessCountMap.Store(&book2, 1)
	accessCountMap.Store(111, 1)

	// go through the map and print the key and value
	accessCountMap.Range(func(key, value interface{}) bool {
		utils.PrintType(key)
		return true
	})

}

func testCompareStringAddress() {
	// https://blog.csdn.net/weixin_39612110/article/details/111364012
	str1 := "Hello, World!"
	str2 := "Hello, World!"

	// 这两个地址并不相同
	fmt.Printf("str address: %p, %p\n", &str1, &str2)
	fmt.Printf("str ==: %t\n", &str1 == &str2)

	x1 := (*reflect.StringHeader)(unsafe.Pointer(&str1))
	x2 := (*reflect.StringHeader)(unsafe.Pointer(&str2))

	// 底层都是指向相同的 []byte
	fmt.Printf("data add: %#v, %#v\n", x1.Data, x2.Data)
}

func testSyncMapConcurrent2() {
	var syncMap sync.Map
	var wg sync.WaitGroup
	for i := 0; i < 1000; i++ {
		wg.Add(1)
		go func(i int) {
			syncMap.Store(i, i)
			wg.Done()
		}(i)
	}
	wg.Wait()
}

func testSyncMapConcurrent() {
	var map1 sync.Map

	for i := 0; i < 1000; i++ {
		go func(i int) {
			map1.Store(i, i)
		}(i)
	}

	time.Sleep(time.Second)

	count := 0
	map1.Range(func(k, v interface{}) bool {
		count++
		return true
	})

	fmt.Println("map1 size:", count)

	var map2 = make(map[int]int)

	for i := 0; i < 1000; i++ {
		go func(i int) {
			map2[i] = i
		}(i)
	}

	delete(map2, 1)

	time.Sleep(time.Second)

	fmt.Println("map2 size:", len(map2))
}

func testSyncMap() {
	var syncMap sync.Map

	syncMap.Store("key1", "value1")
	syncMap.Store("key2", "value2")

	value, hasValue := syncMap.Load("key1")
	if hasValue {
		fmt.Println("Value of key1:", value)
	}

	syncMap.Delete("key2")

	log.Println(syncMap)
}

func testPointer() {
	str := "hello"
	strPointer := &str

	var str2 string = *strPointer

	log.Println("Type strPointer ", reflect.TypeOf(strPointer))
	log.Println("strPointer ", *strPointer)
	log.Println("====================")
	log.Printf("*strPointer %s", *strPointer)
	log.Printf("strPointer %s", strPointer)
	log.Printf("&strPointer %s", &strPointer)
	log.Println("====================")
	log.Printf("str2 %s", str2)
}

func testLog() {
	// Set up the logger
	var logger *lumberjack.Logger
	logger = &lumberjack.Logger{
		Filename: "logs/app.log",
		MaxSize:  100, // megabytes
		MaxAge:   365, // days
	}
	defer utils.CloseLogger(logger)

	fmt.Printf("logger %T\n", logger)

	// Set up the logger to write to both file and console
	log.SetOutput(io.MultiWriter(logger, os.Stdout))
	// 自定义日期格式。但是还是无法阻止原有的日期输出。
	// log.SetOutput(io.MultiWriter(utils.MyLogWriter{}, io.MultiWriter(logger, io.MultiWriter(os.Stdout))))
	log.SetFlags(log.Ldate | log.Ltime)

	// Write some log messages
	log.Println("Starting application...")

	log.Println(strings.Repeat("=", 50))

	for i := 0; i < 10; i++ {
		log.Println("i ", i)
	}
}

func testRedis() {
	_ = network.RedisTest()
	network.RedisSet("foo", "bar")
	value := network.RedisGet("foo")
	log.Println("foo", value)
}

func testObjectInit() {
	var book1 = dto.Book{}

	fmt.Println(book1.CreateTime)

	book1.Init()

	fmt.Println(book1.CreateTime)
}

func funcMonotonic() {
	fmt.Println("Sleeping for 1 second...", time.Now())
	time.Sleep(1 * time.Second)
	fmt.Println("Done sleeping!", time.Now())
}

func testDate() {
	dateStr := "2022-04-23 12:34:56"
	layout := "2006-01-02 15:04:05" // The layout string must specify the date format

	var date, err = time.Parse(layout, dateStr)
	if err != nil {
		fmt.Println("Failed to parse date:", err)
		return
	}
	fmt.Println("Date: ", date)

	// get date as string
	var dateStr2 = time.Now().Format("2006-01-02 15:04")
	fmt.Println("dateStr2: ", dateStr2)

	i := (time.Now().Second() + 100000) / 10
	fmt.Println("i: ", i)

}

func demoPointer() {
	var a int = 20 /* 声明实际变量 */
	var ip *int    /* 声明指针变量 */

	ip = &a /* 指针变量的存储地址 */

	fmt.Printf("a 变量的地址是: %x\n", &a)

	/* 指针变量的存储地址 */
	fmt.Printf("ip 变量储存的指针地址: %x\n", ip)

	/* 使用指针访问值 */
	fmt.Printf("*ip 变量的值: %d\n", *ip)
}

func _sum(s []int, c chan int) {
	sum := 0
	for _, v := range s {
		sum += v
	}
	c <- sum // 把 _sum 发送到通道 c
}

func funcCalcByChannel() {
	s := []int{7, 2, 8, -9, 4, 0}

	c := make(chan int)
	go _sum(s[:len(s)/2], c)
	go _sum(s[len(s)/2:], c)
	x, y := <-c, <-c // 从通道 c 中接收

	fmt.Println(x, y, x+y)
}

func testInterface() {
	var s dto.Shape

	s = dto.Rectangle{Width: 10, Height: 5}
	fmt.Println("矩形面积: %f", s.Area())

	s = dto.Circle{Radius: 3}
	fmt.Println("圆形面积: %f", s.Area())

	fmt.Println("=====================================")

	r1 := dto.Rectangle{Width: 11, Height: 5}
	fmt.Printf("r1 width: %f\n", r1.Width)
	fmt.Printf("r1 address: %p\n", &r1)

	_testPoint(r1)

	fmt.Printf("r1 width: %f\n", r1.Width)
}

func _testPoint(input dto.Rectangle) {

	fmt.Printf("input width 1: %f\n", input.Width)
	fmt.Printf("input address 1: %p\n", &input)
	input.Width = 100
	fmt.Printf("input width 2: %f\n", input.Width)
	fmt.Printf("input address 2: %p\n", &input)
}

func testGoroutine() {
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
