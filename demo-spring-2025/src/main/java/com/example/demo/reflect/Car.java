package com.example.demo.reflect;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 这个类也写了单元测试。
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Car implements InterfaceTransport {

    private String name = "Init Name";
    private int speed;

    @Override
    public String info() {
        return "Name is " + this.name + ", and speed is " + this.speed + ".";
    }

    private String infoPrivate() {
        return "Private: Name is " + this.name + ", and speed is " + this.speed + ".";
    }

}
