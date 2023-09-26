package com.ianmu.mall.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

/**
 * Jackson Jackson json
 *
 * @author darre
 * @version 2023/09/22 21:22
 **/
@Slf4j
public class Jackson {

    // Object转Json
    public static String ObjectToJson(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException ex) {
            log.error("ObjectToJson转换异常", ex);
        }
        return null;
    }
}
