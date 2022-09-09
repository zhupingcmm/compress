package com.mf.filter;

import com.common.base.Constants;
import com.mf.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class BloomFilter {

    private final UserService userService;

    private final RedisTemplate<String, Object> redisTemplate;


    @PostConstruct
    private void init() {
        List<String> names = userService.getUserNames();
        names.forEach(name -> redisTemplate.opsForValue().setBit(Constants.BLOOM_FILTER, this.getOffset(String.format(Constants.USER_CACHE_KEY, name)), true));
        log.info("Success init bloom filter data");
    }

    public boolean isContain (String name) {
        return Boolean.TRUE.equals(redisTemplate.opsForValue().getBit(Constants.BLOOM_FILTER, this.getOffset(name)));
    }

    private long getOffset(String name) {
        long hashCode = Math.abs((name + Constants.BLOOM_FILTER).hashCode());
        return  (long) (hashCode % Math.pow(2, 32));
    }


}
