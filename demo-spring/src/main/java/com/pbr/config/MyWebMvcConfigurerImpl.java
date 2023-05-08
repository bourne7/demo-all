package com.pbr.config;

import com.fasterxml.jackson.core.json.JsonWriteFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pbr.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
import java.util.Optional;


@Configuration
@EnableWebMvc
@Slf4j
public class MyWebMvcConfigurerImpl implements WebMvcConfigurer {

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        log.info("Empty converters");
        // 这里如果添加了一个 converter，效果会是后面的同类型的 converter 会不起作用。
        // converters.add(new MappingJackson2HttpMessageConverter(JsonUtils.OBJECT_MAPPER_FOR_WEB));
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        log.info("Default converters, normally there will be 7. converters: {}", converters.size());

        // 在这里去遍历 converters ，去改变里面的对象，只不过这样会麻烦一些，但是会让这个 List 更加确定。
        Optional<HttpMessageConverter<?>> first = converters.stream().filter(v -> v instanceof MappingJackson2HttpMessageConverter).findFirst();

        if (first.isPresent()) {
            log.info("MappingJackson2HttpMessageConverter existed! Modify its ObjectMapper.");

            // 如果找到了就直接篡改 ObjectMapper 里面的内容。
            ObjectMapper objectMapper = ((MappingJackson2HttpMessageConverter) first.get()).getObjectMapper();

            // 要注意，这里和 JsonUtils 的相同参数相比，少了一个适配层。
            objectMapper.enable(JsonWriteFeature.WRITE_NUMBERS_AS_STRINGS.mappedFeature());

            // 如果你希望同时使用 JsonUtils 的全局统一配置的话，可以使用这个。这套配置也可以不用，但是传日期这些就需要自行定义好格式了。
            JsonUtils.config(objectMapper);
        } else {
            log.info("No MappingJackson2HttpMessageConverter! Build a new one.");
            converters.add(new MappingJackson2HttpMessageConverter(JsonUtils.OBJECT_MAPPER_FOR_WEB));
        }
    }
}
