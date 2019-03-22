package com.dbsun.service;

import com.dbsun.entity.system.PageData;
import com.dbsun.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysUserService {
    @Autowired
    private SysUserMapper sysuserMapper;

    /**
     * 验证用户账号及密码是否存在
     */
    public PageData getLoginValidation(PageData pd) {
        return sysuserMapper.getLoginValidation(pd);
    }

    public int registerUser(PageData pd){
        // 该用户是否存在
        int ret = -1;
        try {
            PageData retPd = sysuserMapper.getUserByUserName(pd);
            if((Long)retPd.get("ret") == 0L)
                sysuserMapper.registerUser(pd);

            ret = 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    public List<PageData> getAllUser(PageData pd){
        return sysuserMapper.getAllUser(pd);
    }

    public int updateUserById(PageData pd){
        return sysuserMapper.updateUserById(pd);
    }



}
