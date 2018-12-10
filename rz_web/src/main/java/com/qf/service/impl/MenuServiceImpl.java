package com.qf.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.qf.dao.SysMenuMapper;
import com.qf.entity.SysMenu;
import com.qf.entity.SysMenuExample;
import com.qf.service.MenuService;
import com.qf.util.R;
import com.qf.util.TableResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.xml.bind.SchemaOutputResolver;
import java.util.*;

@Service
public class MenuServiceImpl implements MenuService {

    //注入mapper
    @Autowired
    private SysMenuMapper sysMenuMapper;

    /**
     *
     * @param offset  起始记录
     * @param limit  每页显示多少条
     * @return
     */
    @Override
    public TableResult findMenu(int offset, int limit, String search) {

        //分页   * @param pageNum  页码
        //     * @param pageSize 每页显示数量
       // PageHelper.startPage()
        /**
         * offset:起始记录
         * limit 每页显示多少条
         */
        PageHelper.offsetPage(offset,limit);

      // select * from sys_menu where name like ?

       // List<SysMenu> list = sysMenuMapper.selectByExample(null);//全部
        //封装查询条件（where后的条件）
        SysMenuExample example = null;
        if (search!=null&&!"".equals(search)){
           example = new SysMenuExample();
            SysMenuExample.Criteria  criteria=  example.createCriteria();
            criteria.andNameLike("%"+search+"%");
        }
        List<SysMenu> list = sysMenuMapper.selectByExample(example);

        PageInfo<SysMenu> pageInfo = new PageInfo<>(list);

        TableResult result = new TableResult();
        result.setTotal(pageInfo.getTotal());
        result.setRows(pageInfo.getList());

        return result;
    }

    @Override
    public R getmenu(Long userId) {
        //系统菜单
        //任务菜单
        List<Map<String,Object>> parentMenu = sysMenuMapper.findParentMenuByUserId(userId);
        for (Map<String, Object> map : parentMenu) {
            Long menuId = (Long)map.get("menu_id");
            //查询菜单下的子菜单
            List<Map<String,Object>> menuMap = sysMenuMapper.findMenuByUserId(userId,menuId);
            map.put("list",menuMap);
        }
        List<String> perms = this.findPermsByUserId(userId);
        return R.ok().put("menuList",parentMenu).put("permissions",perms);
    }

    public List<String> findPermsByUserId(Long userId) {
        List<String> list = sysMenuMapper.findPermsByUserId(userId);
        Set<String> set = new HashSet<String>();
        for (String s : list) {
            if (s != null && !s.equals("")) {
                String ss[] = s.split(",");
                for (String s1 : ss) {
                    set.add(s1);
                }
            }
        }
        //set->list
        List<String> perms = new ArrayList<>();
        perms.addAll(set);

        return perms;
    }

    @Override
    public R updatemenu(SysMenu menu) {
        int i = sysMenuMapper.updateByPrimaryKeySelective(menu);
        return i>0?R.ok("修改成功"):R.error("修改失败");
    }

    @Override
    public R findById(long menuId) {
        SysMenu menu = sysMenuMapper.selectByPrimaryKey(menuId);
        if(menu!=null){
            return R.ok().put("menu",menu);
        }
        return R.error("查询菜单错误");
    }

    @Override
    public R insertSelective(SysMenu menu) {
        int i = sysMenuMapper.insertSelective(menu);
        return i>0?R.ok("新增成功"):R.error("新增失败");
    }

    @Override
    public R getupmenu() {
        List<SysMenu> list = sysMenuMapper.getupmenu();
        //在后台添加一个一级目录
        //为了可以添加和系统菜单同级的目录
        SysMenu menu = new SysMenu();
        menu.setMenuId(0l);
        menu.setParentId(-1l);
        menu.setName("一级菜单");
        menu.setType(0);
        list.add(menu);
        return R.ok("").put("menuList",list);
    }

    @Override
    public R del(List<Long> ids) {

        //系统菜单不能删除
        for (Long id : ids) {
            if (id<30){
                return  R.error("系统菜单不能删除！");
            }
        }

        //sysMenuMapper.deleteByPrimaryKey()//
        //where menu_id in (1,2);
        SysMenuExample example = new SysMenuExample();
        SysMenuExample.Criteria  c = example.createCriteria();

        c.andMenuIdIn(ids);

        int i = sysMenuMapper.deleteByExample(example);

        return i>0?R.ok("删除成功"):R.error("删除失败");
    }
}
