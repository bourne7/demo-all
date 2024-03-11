package com.pbr.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author pbr
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class FruitDTO extends BaseUserAndTimeDTO {

    private static final AtomicLong FRUIT_ID = new AtomicLong();

    @NotNull
    private String fruitName;

    private Long fruitId;

    public static FruitDTO getNewInstance() {
        FruitDTO fruitDTO = new FruitDTO();
        fruitDTO.setFruitId(FRUIT_ID.getAndIncrement());
        fruitDTO.setCreateTime(new Date());
        return fruitDTO;
    }

}
