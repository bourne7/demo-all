package com.example.demo.testing;


import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LombokMain {

    public static void main(String[] args) throws Exception {

        LombokDTO lombokDTO;

        lombokDTO = LombokDTO.builder().name("a").build();
        System.out.println(lombokDTO);

        lombokDTO = new LombokDTO("b");
        System.out.println(lombokDTO);

        /**
         * result:
         * LombokDTO(name=a, qty=51)
         * LombokDTO(name=b, qty=0)
         *
         * Generated class will remove default value of @Builder.Default
         */

    }
}
