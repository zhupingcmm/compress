package com.mf.controller;

import com.common.base.BaseResponse;
import com.common.base.Constants;
import com.common.base.ResponseEnum;
import com.common.exception.CompressException;
import com.common.utils.JwtUtil;
import com.mf.config.AppConfig;
import com.mf.dto.UserDto;
import com.mf.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AppConfig appConfig;

    private final RedisTemplate<String, UserDto> redisTemplate;

    @GetMapping("/me")
    public BaseResponse<UserDto> getUser(HttpServletRequest request) {
        val token = request.getHeader("Authorization").replace("Bearer", "").trim();
        val claims = JwtUtil.parseToken(token);
        String username = (String) claims.get(Constants.USERNAME);
        String password = (String) claims.get(Constants.PASSWORD);
        val userInfoFromCache= redisTemplate.opsForValue().get(username);

        if (userInfoFromCache != null) {
            if (!Objects.equals(password, userInfoFromCache.getPassword())) throw new CompressException(ResponseEnum.INVALID_TOKEN);
            userInfoFromCache.setPassword(null);
        } else {
            UserDto userDto = userService.findUserByName(username);
            userDto.setToken(token);
            return BaseResponse.success(userDto);
        }

        return BaseResponse.success(userInfoFromCache);
    }

    @PostMapping("/register")
    public BaseResponse<UserDto> register(@RequestBody UserDto userDto) {
        userService.addUser(userDto);
        val token = JwtUtil.createToken(appConfig.getJwt().getJwtId(), userDto.getName(), userDto.getPassword(), appConfig.getJwt().getTokenExpireTime());
        userDto.setToken(token);
        userDto.setPassword(null);
        redisTemplate.opsForValue().set(userDto.getName(), userDto, appConfig.getJwt().getTokenExpireTime(), TimeUnit.MILLISECONDS);
        return BaseResponse.success(userDto);
    }

    @PostMapping("/login")
    public BaseResponse<UserDto> login(@RequestBody UserDto userDto) {
        UserDto user = userService.findUserByName(userDto.getName());
        if (Objects.equals(user.getPassword(), userDto.getPassword())) {
            val token = JwtUtil.createToken(appConfig.getJwt().getJwtId(), userDto.getName(), userDto.getPassword(), appConfig.getJwt().getTokenExpireTime());

            user.setToken(token);
            redisTemplate.opsForValue().set(user.getName(), user, appConfig.getJwt().getTokenExpireTime(), TimeUnit.MILLISECONDS);
            user.setPassword(null);
            return BaseResponse.success(user);
        }
        throw new CompressException(ResponseEnum.AUTHENTICATION_FAILED);
    }


}
