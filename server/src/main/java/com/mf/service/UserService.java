package com.mf.service;

import com.mf.dto.UserDto;
import org.springframework.stereotype.Service;

public interface UserService {
    void addUser(UserDto userDto);

    UserDto findUserByName(String name);
}
