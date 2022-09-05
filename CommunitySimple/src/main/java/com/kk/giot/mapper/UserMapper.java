package com.kk.giot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kk.giot.pojo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
