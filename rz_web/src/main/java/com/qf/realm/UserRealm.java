package com.qf.realm;

import com.qf.entity.SysUser;
import com.qf.service.MenuService;
import com.qf.service.RoleService;
import com.qf.service.SysUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class UserRealm extends AuthorizingRealm {

    @Resource
    private SysUserService sysUserService;
    @Resource
    private RoleService roleService;
    @Resource
    private MenuService menuService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("授权----》");
        //sys_user   sys_role   sys_user_role
        //sys_menu  sys_role_menu
        // System.out.println("principals----->"+principals);

        SysUser user = (SysUser) principalCollection.getPrimaryPrincipal();
        //SecurityUtils.getSubject().getPrincipal(); 使用该方法也可以得到认证后存储的对象
        List<String> roles = roleService.findRoleByUserId(user.getUserId());

        List<String> perms = menuService.findPermsByUserId(user.getUserId());

        //授权
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addRoles(roles);
        info.addStringPermissions(perms);


        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("认证----》");
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        String username = usernamePasswordToken.getUsername();
        String password = new String(usernamePasswordToken.getPassword());

        //跟真实数据比较
        SysUser user = new SysUser();
        user.setUsername(username);
        System.out.println(password);
        SysUser sysUser = sysUserService.login(user);
        if(sysUser==null){
            throw new UnknownAccountException("账号不存在");
        }
        if(!sysUser.getPassword().equals(password)){
            throw new IncorrectCredentialsException("密码错误");
        }
        if(sysUser.getStatus()!=1){
            throw new LockedAccountException("账号被锁定");
        }

        //封装AuthenticationInfo 对象
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(sysUser,password,this.getName());
        return info;
    }
}
