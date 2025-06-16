package com.example.demo.testing;

import lombok.Builder;
import lombok.Data;

/**
 * @author Lawrence Peng
 */
@Data
@Builder
public class LombokDTO {

    private String name;

    @Builder.Default
    private int qty = 51;

    public LombokDTO() {
    }

    public LombokDTO(String name) {
        this.name = name;
    }

    public LombokDTO(String name, int qty) {
        this.name = name;
        this.qty = qty;
    }


}
