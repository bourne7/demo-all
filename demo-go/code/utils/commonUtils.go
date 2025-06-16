package utils

import "fmt"

func PrintTypeI(value interface{}) {
	fmt.Printf("Value: %v, Type: %T\n", value, value)
}

func PrintType(value any) {
	fmt.Printf("Value: %v, Type: %T\n", value, value)
}
