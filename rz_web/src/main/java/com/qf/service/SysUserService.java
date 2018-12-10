package com.qf.service;

import com.qf.entity.SysUser;
import com.qf.entity.SysUserExample;
import com.qf.util.R;
import com.qf.util.TableResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserService {

    int countByExample(SysUserExample example);

    int deleteByExample(SysUserExample example);

    int deleteByPrimaryKey(Long userId);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    List<SysUser> selectByExample(SysUserExample example);

    SysUser selectByPrimaryKey(Long userId);

    int updateByExampleSelective(@Param("record") SysUser record, @Param("example") SysUserExample example);

    int updateByExample(@Param("record") SysUser record, @Param("example") SysUserExample example);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    SysUser login(SysUser user);

    TableResult findusers(int offset, int limit, String search);

    R save(SysUser sysUser);

    R findById(long userId);

    R update(SysUser sysUser);

    R delete(List<Long> ids);
}
