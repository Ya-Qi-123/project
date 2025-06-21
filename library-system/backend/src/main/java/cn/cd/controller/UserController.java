package cn.cd.controller;

import cn.cd.domain.TUser;
import cn.cd.service.TUserService;
import cn.cd.util.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private TUserService tUserService;

    @PostMapping("/login/EmailAndPassword")
    public AjaxResult loginControllerByEmailAndPassword(@RequestParam String email, @RequestParam String password){
        TUser tUser = tUserService.loginServiceByEmailAndPassword(email, password);
        if(tUser!=null){
            return AjaxResult.ok( "登录成功！");
        }else{
            return AjaxResult.fail("登录失败！");
        }
    }

    @PostMapping("/login/PhoneAndPassword")
    public AjaxResult loginControllerByPhoneAndPassword(@RequestParam String phone, @RequestParam String password){
        TUser tUser = tUserService.loginServiceByPhoneAndPassword(phone, password);
        if(tUser!=null){
            return AjaxResult.ok( "登录成功！");
        }else{
            return AjaxResult.fail("登录失败！");
        }
    }

}
