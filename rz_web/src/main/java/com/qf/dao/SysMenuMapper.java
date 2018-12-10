package com.qf.dao;


import com.qf.entity.SysMenu;
import com.qf.entity.SysMenuExample;
import com.qf.util.R;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SysMenuMapper {
    int countByExample(SysMenuExample example);

    int deleteByExample(SysMenuExample example);

    int deleteByPrimaryKey(Long menuId);

    int insert(SysMenu record);

    int insertSelective(SysMenu record);

    List<SysMenu> selectByExample(SysMenuExample example);

    SysMenu selectByPrimaryKey(Long menuId);

    int updateByExampleSelective(@Param("record") SysMenu record, @Param("example") SysMenuExample example);

    int updateByExample(@Param("record") SysMenu record, @Param("example") SysMenuExample example);

    int updateByPrimaryKeySelective(SysMenu record);

    int updateByPrimaryKey(SysMenu record);

    List<SysMenu> getupmenu();

    List<String> findPermsByUserId(Long userId);

    List<Map<String, Object>> findMenuByUserId(@Param("userId") Long userId, @Param("parentId") Long parentId);

    List<Map<String, Object>> findParentMenuByUserId(Long userId);
}