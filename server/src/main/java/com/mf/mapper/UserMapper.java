package com.mf.mapper;

import com.mf.model.UserDo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    int addUser(UserDo user);

    UserDo getByName(String name);

    int deleteByName(String name);
}
