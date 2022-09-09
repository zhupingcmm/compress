package com.mf.mapper;

import com.mf.model.UserDo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    int addUser(UserDo user);

    UserDo getByName(String name);

    int deleteByName(String name);

    List<String> getUserNames();
}
