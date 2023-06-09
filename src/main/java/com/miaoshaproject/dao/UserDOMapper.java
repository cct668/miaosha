package com.miaoshaproject.dao;

import com.miaoshaproject.dataobject.UserDO;
import org.apache.catalina.User;

public interface UserDOMapper {
    int deleteByPrimaryKey(Integer id);
    int insert(UserDO record);
    int insertSelective(UserDO record);
    UserDO selectByPrimaryKey(Integer id);
    UserDO selectByTelphone(String telphone);
    int updateByPrimaryKeySelective(UserDO record);
    int updateByPrimaryKey(UserDO record);

}