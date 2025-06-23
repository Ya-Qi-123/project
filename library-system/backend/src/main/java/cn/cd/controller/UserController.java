package cn.cd.controller;

import cn.cd.domain.TUser;
import cn.cd.service.UserService;
import cn.cd.util.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService tUserService;

    // 用户的邮箱密码登录
    @PostMapping("/login/EmailAndPassword")
    public Object loginByEmailAndPassword(@RequestParam String email,
                                          @RequestParam String password){
        TUser tUser = tUserService.loginServiceByEmailAndPassword(email, password);
        if(tUser == null){
            return AjaxResult.fail("登录失败,邮箱或密码错误。");
        }else{
            if(tUser.getStatus() == 1){
                return tUser;
            }else if(tUser.getStatus() != 1){
                return AjaxResult.fail("登录失败,该账号无法使用");
            }
        }
        return null;
    }

    // 用户的手机号密码登录
    @PostMapping("/login/PhoneAndPassword")
    public Object loginByPhoneAndPassword(@RequestParam String phone,
                                          @RequestParam String password){
        TUser tUser = tUserService.loginServiceByPhoneAndPassword(phone, password);
        if(tUser == null){
            return AjaxResult.fail("登录失败，手机号或密码错误。");
        }
        if(tUser.getStatus() == 1){
            return tUser;
        }else if(tUser.getStatus() != 1){
            return AjaxResult.fail("登录失败,该账号无法使用");
        }
        return null;
    }

    // 用户的注册
    @PostMapping("/register")
    public Object register(@RequestParam String username,
                           @RequestParam String email,
                           @RequestParam String phone,
                           @RequestParam String password){
        TUser tUser1 = tUserService.loginServiceByEmailAndPassword(email, password);
        TUser tUser2 = tUserService.loginServiceByPhoneAndPassword(phone, password);
        if(tUser1 != null || tUser2 != null){
            return AjaxResult.fail("注册失败,邮箱或手机号已注册");
        }
        int temp = tUserService.registerService(username, email, phone, password);
        if(temp == 1){
            return AjaxResult.ok( "注册成功！");
        }else{
            return AjaxResult.fail("注册失败！");
        }
    }

    // 用户的状态修改
    @PostMapping("/updateStatus")
    public Object updateStatus(@RequestParam Long id,
                               @RequestParam Integer status){
        int temp = tUserService.updateStatus(id, status);
        if(temp == 1){
            return AjaxResult.ok( "状态修改成功！");
        }else{
            return AjaxResult.fail("状态修改失败！");
        }
    }

    // 用户个人信息修改
    @PostMapping("/updatePersonalInformation")
    public Object update(Long id, String username, String email, String phone, String gender){
        int temp = tUserService.updatePersonalInformation(id, username, email, phone, gender);
        if(temp == 1){
            return AjaxResult.ok( "修改成功！");
        }else{
            return AjaxResult.fail("修改失败！");
        }
    }

    // 删除用户
    @DeleteMapping("/deleteById")
    public Object delete(@RequestParam Long id){
        int temp = tUserService.delete(id);
        if(temp == 1){
            return AjaxResult.ok( "删除成功！");
        }else{
            return AjaxResult.fail("删除失败！");
        }
    }

    // 修改用户密码
    @PostMapping("/updatePassword")
    public Object updatePassword(@RequestParam Long id,
                                 @RequestParam String oldPassword,
                                 @RequestParam String newPassword){
        TUser tUser = tUserService.getById(id);
        if(tUser.getPassword().equals(oldPassword)){
            tUser.setPassword(newPassword);
            tUserService.updateById(tUser);
            return AjaxResult.ok("修改成功！");
        }else{
            return AjaxResult.fail("原密码错误！");
        }
    }
}
