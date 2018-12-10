package com.qf.service.impl;


import com.qf.dao.SysRoleMapper;
import com.qf.service.RoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class RoleServiceImpl  implements RoleService {

    //dao
    @Resource
    private SysRoleMapper sysRoleMapper;

    @Override
    public List<String> findRoleByUserId(long userId) {

        return sysRoleMapper.findRoleNameByUserId(userId);
    }
}
