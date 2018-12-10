package com.qf.controller;

import com.google.code.kaptcha.Producer;
import com.qf.dto.SysUserDTO;
import com.qf.entity.SysUser;
import com.qf.service.SysUserService;
import com.qf.util.R;
import com.qf.util.ShiroUtils;
import com.qf.util.SysConstant;
import com.qf.util.TableResult;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private Producer producer;

    @Resource
    private SysUserService sus;

    @RequestMapping("sys/user/info")
    public R userInfo(){
        SysUser sysUser  = ShiroUtils.getCurrentUser();
        return R.ok().put("user",sysUser);
    }

    @RequiresPermissions("sys:user:list")
    @RequestMapping("/sys/user/list")
    public TableResult getusers(int offset , int limit, String search){
        return  sus.findusers(offset,limit,search);
    }

    @RequestMapping("/captcha.jpg")
    public void captcha(HttpServletResponse response) throws IOException {
        String text = producer.createText();
        System.out.println("验证码---"+text);
        //存储到域对象中
        ShiroUtils.setAttribute(SysConstant.CODE_KEY,text);
        //存储验证码
        BufferedImage image = producer.createImage(text);
        ImageIO.write(image,"jpg",response.getOutputStream());
    }

    @RequiresPermissions("sys:user:save")
    @RequestMapping("sys/user/save")
    public R save(@RequestBody SysUser sysUser){
        return sus.save(sysUser);
    }

    //修改开始
    @RequiresPermissions("sys:user:info")
    @RequestMapping("/sys/user/info/{userId}")
    public R selectUser(@PathVariable long userId){

        return sus.findById(userId);
    }

    //修改结束
    @RequiresPermissions("sys:user:update")
    @RequestMapping("sys/user/update")
    public R update(@RequestBody SysUser sysUser) {
        return  sus.update(sysUser);
    }

    //删除
    @RequiresPermissions("sys:user:delete")
    @RequestMapping("/sys/user/del")
    public R delete(@RequestBody List<Long> ids){

        return sus.delete(ids);
    }

    //退出
    @RequestMapping("/logout")
    public  void logout(){

        ShiroUtils.logout();

        // return "/login.html";
    }

    @RequestMapping("/sys/login")
    public R login(@RequestBody SysUserDTO user){
        //得到系统生成的验证码
        String text = (String) ShiroUtils.getAttribute(SysConstant.CODE_KEY);
        if(!text.equalsIgnoreCase(user.getCaptcha())){
            return R.error("验证码不正确");
        }
        Md5Hash md5Hash = new Md5Hash(user.getPassword(),user.getUsername(),1024);
        user.setPassword(md5Hash.toString());

        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(),user.getPassword());
        //得到subject对象
        Subject subject = SecurityUtils.getSubject();

        //登录
        try {
            //记住我功能
            if (user.isRememberMe()){
                token.setRememberMe(true);
            }
            subject.login(token);//调用自定义Realm进行认证和授权
            System.out.println(subject.isPermitted("sys:user:select"));
            System.out.println(subject.isPermitted("sys:role:select"));
            return R.ok();
        } catch (Exception e) {
            return R.error(e.getMessage());
        }


        /*SysUser su = sus.login(user);
        if (su!=null){
            if(user.getPassword().equals(su.getPassword())){
                return R.ok();
            }else {
                return R.error("密码不正确");
            }

        }
        return R.error("用户名不存在");*/
    }

}
