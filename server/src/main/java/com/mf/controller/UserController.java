package com.mf.controller;

import com.common.base.BaseResponse;
import com.mf.dto.UserDto;
import com.mf.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{name}")
    public BaseResponse<UserDto> getUser(@PathVariable String name){
        return BaseResponse.success(userService.findUserByName(name));
    }

    @PostMapping
    public BaseResponse register(@RequestBody UserDto userDto) {
        userService.addUser(userDto);
        return BaseResponse.success();
    }
}
