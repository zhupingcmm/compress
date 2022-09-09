package com.mf.service.impl;

import com.common.base.Asset;
import com.common.base.Constants;
import com.mf.annotation.MyCacheable;
import com.mf.annotation.MyRateLimiter;
import com.mf.dto.UserDto;
import com.mf.mapper.UserMapper;
import com.mf.model.UserDo;
import com.mf.service.UserService;
import com.mf.utils.ObjectTransformer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    @Override
    @CachePut(cacheNames = Constants.USER_CACHE_KEY, key = "#userDto.getName()")
    public UserDto addUser(UserDto userDto) {
        int result = userMapper.addUser(ObjectTransformer.transform(userDto, UserDo.class));
        Asset.singleRowAffected(result);
        log.info("{} success to register", userDto.getName());
        return userDto;
    }

    @Override
    @MyCacheable(cacheNames = Constants.USER_CACHE_KEY, key = "#name")
    public UserDto findUserByName(String name) {
        UserDto userDto = ObjectTransformer.transform(userMapper.getByName(name), UserDto.class);
        log.info("Success get {} user info", userDto.getName());
        return userDto;
    }

    @Override
    @CacheEvict(cacheNames = Constants.USER_CACHE_KEY, key = "#name")
    public void deleteUser(String name) {
        int result = userMapper.deleteByName(name);
        Asset.singleRowAffected(result);
    }

    @Override
    public List<String> getUserNames() {
        return userMapper.getUserNames();
    }


}
