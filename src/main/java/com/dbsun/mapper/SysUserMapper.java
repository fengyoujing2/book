package com.dbsun.mapper;

import com.dbsun.entity.system.PageData;

import java.util.List;

public interface SysUserMapper {

    /**
     * 验证用户账号及密码是否存在
     */
    public PageData getLoginValidation(PageData pd);
    public PageData getUserByUserName(PageData pd);
    public int registerUser(PageData pd);
    public List<PageData> getAllUser(PageData pd);
    public int updateUserById(PageData pd);


}
