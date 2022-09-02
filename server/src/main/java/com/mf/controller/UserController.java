package com.mf.controller;

import com.common.base.BaseResponse;
import com.common.base.Constants;
import com.common.base.ResponseEnum;
import com.common.utils.JwtUtil;
import com.mf.dto.UserDto;
import com.mf.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

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

    @GetMapping("/me")
    public BaseResponse<UserDto> getUser(HttpServletRequest request) {
        val token = request.getHeader("Authorization").replace("Bearer", "").trim();
        val claims = JwtUtil.parseToken(token);
        String username = (String) claims.get(Constants.USERNAME);
        String password = (String) claims.get(Constants.PASSWORD);
        UserDto userDto = userService.findUserByName(username);
        if (!Objects.equals(password, userDto.getPassword())) return BaseResponse.error(ResponseEnum.INVALID_TOKEN);
        userDto.setToken(token);
        return BaseResponse.success(userDto);
    }

    @PostMapping
    public BaseResponse register(@RequestBody UserDto userDto) {
        userService.addUser(userDto);
        return BaseResponse.success();
    }
}
