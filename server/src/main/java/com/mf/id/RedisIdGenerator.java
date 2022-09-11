package com.mf.id;


import com.common.base.IdTypeEnum;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class RedisIdGenerator {

    private static final DateTimeFormatter  dateTimeFormatter = DateTimeFormatter.ofPattern("yyMMddHHmmssSSS");
    private final RedisTemplate<String, Integer> redisTemplate;


    public long getNext(IdTypeEnum idTypeEnum) {
        LocalDateTime now = LocalDateTime.now();
        String dateTime = dateTimeFormatter.format(now);
        Long value = redisTemplate.opsForValue().increment(idTypeEnum.getServiceName(), 1);

        if (value >= 1000) {
            value = value % 1000;
        }

        String seq = StringUtils.leftPad(Long.toString(value), 3, "0");

        return Long.parseLong(idTypeEnum.getCode() + dateTime + seq);
    }

}
