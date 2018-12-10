package com.qf.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qf.dao.SysUserMapper;
import com.qf.entity.SysMenu;
import com.qf.entity.SysMenuExample;
import com.qf.entity.SysUser;
import com.qf.entity.SysUserExample;
import com.qf.service.SysUserService;
import com.qf.util.R;
import com.qf.util.TableResult;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SysUserServiceImpl implements SysUserService {

    @Resource
    private SysUserMapper sum;

    @Override
    public int countByExample(SysUserExample example) {
        return sum.countByExample(example);
    }

    @Override
    public int deleteByExample(SysUserExample example) {
        return sum.deleteByExample(example);
    }

    @Override
    public int deleteByPrimaryKey(Long userId) {
        return sum.deleteByPrimaryKey(userId);
    }

    @Override
    public int insert(SysUser record) {
        return sum.insert(record);
    }

    @Override
    public int insertSelective(SysUser record) {
        return sum.insertSelective(record);
    }

    @Override
    public List<SysUser> selectByExample(SysUserExample example) {
        return null;
    }

    @Override
    public SysUser selectByPrimaryKey(Long userId) {
        return sum.selectByPrimaryKey(userId);
    }

    @Override
    public int updateByExampleSelective(SysUser record, SysUserExample example) {
        return sum.updateByExampleSelective(record,example);
    }

    @Override
    public int updateByExample(SysUser record, SysUserExample example) {
        return sum.updateByExample(record,example);
    }

    @Override
    public int updateByPrimaryKeySelective(SysUser record) {
        return sum.updateByPrimaryKeySelective(record);
    }

    @Override
    public R findById(long userId) {
        SysUser user = sum.selectByPrimaryKey(userId);
        if (user!=null){
            return R.ok().put("user",user);
        }
        return R.error();
    }

    @Override
    public R delete(List<Long> ids) {
        for (Long id : ids) {
            if (id==1){
                return R.error("管理员不能删除");
            }
        }

        SysUserExample example  = new SysUserExample();
        SysUserExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdIn(ids);
        int i = sum.deleteByExample(example);
        return i>0?R.ok():R.error();
    }

    @Override
    public R update(SysUser sysUser) {
        int i = sum.updateByPrimaryKeySelective(sysUser);
        return i>0?R.ok("修改成功"):R.error();
    }

    @Override
    public R save(SysUser sysUser) {
        sysUser.setCreateTime(new Date());
        sysUser.setStatus((byte)1);
        sysUser.setCreateUserId(1l);//创建人ID
        sysUser.setPassword(new Md5Hash(sysUser.getPassword(),sysUser.getUsername(),1024).toString());
        int i = sum.insertSelective(sysUser);

        return i>0?R.ok("新增成功"):R.error("新增失败");
    }

    @Override
    public TableResult findusers(int offset, int limit, String search) {

        PageHelper.offsetPage(offset,limit);
        SysUserExample example = null;
        if (search!=null&&!"".equals(search)){
            example = new SysUserExample();
            SysUserExample.Criteria  criteria=  example.createCriteria();
            criteria.andNameLike("%"+search+"%");
        }
        List<SysUser> list = sum.selectByExample(example);

        PageInfo<SysUser> pageInfo = new PageInfo<SysUser>(list);
        TableResult result = new TableResult();
        result.setTotal(pageInfo.getTotal());
        result.setRows(pageInfo.getList());

        return result;
    }

    @Override
    public SysUser login(SysUser user) {
        SysUser su = sum.login(user);
        if(su!=null){
            return su;
        }
        return null;
    }

    @Override
    public int updateByPrimaryKey(SysUser record) {
        return sum.updateByPrimaryKey(record);
    }
}
