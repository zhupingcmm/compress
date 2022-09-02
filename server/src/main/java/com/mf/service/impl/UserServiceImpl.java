package com.mf.service.impl;

import com.common.base.Asset;
import com.mf.dto.UserDto;
import com.mf.mapper.UserMapper;
import com.mf.model.UserDo;
import com.mf.service.UserService;
import com.mf.utils.ObjectTransformer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    @Override
    public void addUser(UserDto userDto) {
        int result = userMapper.addUser(ObjectTransformer.transform(userDto, UserDo.class));
        Asset.singleRowAffected(result);
        log.info("{} success to register", userDto.getName());
    }

    @Override
    public UserDto findUserByName(String name) {
        UserDto userDto = ObjectTransformer.transform(userMapper.getByName(name), UserDto.class);
        log.info("Success get {} user info", userDto.getName());
        return userDto;
    }
}
