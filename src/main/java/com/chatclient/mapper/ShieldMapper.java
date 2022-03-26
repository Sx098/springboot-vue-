package com.chatclient.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chatclient.entity.Shield;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShieldMapper extends BaseMapper<Shield> {
    @Select("SELECT word FROM shield")
    List<String> huo();
}
