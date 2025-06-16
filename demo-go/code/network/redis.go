package network

import (
	"context"
	"github.com/redis/go-redis/v9"
	"log"
	"sync"
)

var (
	myRedisClient *redis.Client
	once          sync.Once
)

func GetRedisClient() *redis.Client {

	once.Do(func() {
		myRedisClient = redis.NewClient(&redis.Options{
			Addr:     "172.18.81.101:6379",
			Password: "",
			DB:       0,
		})
	})

	return myRedisClient
}

func RedisTest() bool {

	log.Printf("MyRedisClient %p\n", GetRedisClient())

	// Ping the Redis server to check the connection
	pong, err := GetRedisClient().Ping(context.Background()).Result()

	if err != nil {
		log.Println("Failed to connect to Redis")
		return false
	}
	log.Println("Connected to Redis:", pong)
	return true
}

func RedisSet(key string, value string) {

	log.Printf("MyRedisClient %p\n", GetRedisClient())

	err := GetRedisClient().Set(context.Background(), key, value, 0).Err()
	if err != nil {
		panic(err)
	}
}

func RedisGet(key string) string {
	value, err := GetRedisClient().Get(context.Background(), key).Result()
	if err != nil {
		panic(err)
	}

	log.Println("key", key, "value", value)
	return value
}
