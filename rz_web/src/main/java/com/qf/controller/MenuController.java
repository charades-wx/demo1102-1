package com.qf.controller;


import com.qf.entity.SysMenu;
import com.qf.entity.SysUser;
import com.qf.service.MenuService;
import com.qf.util.R;
import com.qf.util.ShiroUtils;
import com.qf.util.TableResult;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class MenuController {

    //依赖service
    @Resource
    private MenuService menuService;

    @RequiresPermissions("sys:menu:list")
    @RequestMapping("/sys/menu/list")
    public TableResult findMenyuList(int offset , int limit, String search){//{total:800,rows:[{},{}]}

       return  menuService.findMenu(offset,limit,search);
    }

    @RequiresPermissions("sys:menu:save")
    @RequestMapping("/sys/menu/save")
    public R addmenu(@RequestBody SysMenu menu){
        return menuService.insertSelective(menu);
    }

    @RequiresPermissions("sys:menu:info")
    @RequestMapping("/sys/menu/info/{menuId}")
    public R findById(@PathVariable("menuId") long menuId){
        return menuService.findById(menuId);
    }

    @RequiresRoles("admin")
    @RequestMapping("/sys/menu/update")
    public R updatemenu(@RequestBody SysMenu menu){
        return menuService.updatemenu(menu);
    }

    @RequiresPermissions("sys:menu:select")
    @RequestMapping("/sys/menu/select")
    public R getupmenu(){
        return menuService.getupmenu();
    }

    @RequiresPermissions("sys:menu:delete")
    @RequestMapping("/sys/menu/del")
    public R del(@RequestBody List<Long> ids){// [1,2];

        return  menuService.del(ids);
    }

    //获取左侧菜单栏
    @RequestMapping("sys/menu/user")
    public R getMenuList(){
        //得到当前登录人的信息
        SysUser sysUser = ShiroUtils.getCurrentUser();

        return menuService.getmenu(sysUser.getUserId());
    }

}
