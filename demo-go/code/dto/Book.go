package dto

import "time"

type Book struct {
	Initialized bool
	Name        string
	Author      string
	Price       float64
	CreateTime  time.Time
}

func (book *Book) Init() {
	book.CreateTime = time.Now()
}
