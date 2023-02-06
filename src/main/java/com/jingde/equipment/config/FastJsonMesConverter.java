package com.jingde.equipment.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.http.MediaType;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

// 使用阿里 FastJson 作为JSON MessageConverter
public class FastJsonMesConverter extends FastJsonHttpMessageConverter {
    public FastJsonMesConverter() {
        // 在这里配置 fastjson 特性
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        // 自定义时间格式
        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");

        // List字段如果为null，输出为[]，而非null。
        fastJsonConfig.setSerializerFeatures(SerializerFeature.WriteNullListAsEmpty);
        // Boolean字段如果为null，输出为false，而非null。
        fastJsonConfig.setSerializerFeatures(SerializerFeature.WriteNullBooleanAsFalse);
        // 是否输出值为null的字段，默认为false。
    fastJsonConfig.setSerializerFeatures(SerializerFeature.WriteMapNullValue);

        // 字符类型字段如果为null，输出为""，而非null。
//    fastJsonConfig.setSerializerFeatures(SerializerFeature.WriteNullStringAsEmpty);

        this.setFastJsonConfig(fastJsonConfig);
        this.setDefaultCharset(StandardCharsets.UTF_8);

        //升级最新版本需加=============================================================
        List<MediaType> supportedMediaTypes = new ArrayList<>();
        supportedMediaTypes.add(MediaType.APPLICATION_JSON);
        supportedMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        supportedMediaTypes.add(MediaType.APPLICATION_ATOM_XML);
        supportedMediaTypes.add(MediaType.APPLICATION_FORM_URLENCODED);
        supportedMediaTypes.add(MediaType.APPLICATION_OCTET_STREAM);
        supportedMediaTypes.add(MediaType.APPLICATION_PDF);
        supportedMediaTypes.add(MediaType.APPLICATION_RSS_XML);
        supportedMediaTypes.add(MediaType.APPLICATION_XHTML_XML);
        supportedMediaTypes.add(MediaType.APPLICATION_XML);
        supportedMediaTypes.add(MediaType.IMAGE_GIF);
        supportedMediaTypes.add(MediaType.IMAGE_JPEG);
        supportedMediaTypes.add(MediaType.IMAGE_PNG);
        supportedMediaTypes.add(MediaType.TEXT_EVENT_STREAM);
        supportedMediaTypes.add(MediaType.TEXT_HTML);
        supportedMediaTypes.add(MediaType.TEXT_MARKDOWN);
        supportedMediaTypes.add(MediaType.TEXT_PLAIN);
        supportedMediaTypes.add(MediaType.TEXT_XML);
        this.setSupportedMediaTypes(supportedMediaTypes);
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return super.supports(clazz);
    }
}
