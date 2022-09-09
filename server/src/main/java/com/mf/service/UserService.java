package com.mf.service;

import com.mf.dto.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {
    UserDto addUser(UserDto userDto);

    UserDto findUserByName(String name);

    void deleteUser(String name);

    List<String> getUserNames();
}
