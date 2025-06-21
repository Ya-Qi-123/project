package cn.cd.controller;

import cn.cd.domain.TUser;
import cn.cd.service.UserService;
import cn.cd.util.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService tUserService;

    @PostMapping("/login/EmailAndPassword")
    public Object loginByEmailAndPassword(@RequestParam String email,
                                              @RequestParam String password){
        TUser tUser = tUserService.loginServiceByEmailAndPassword(email, password);
        if(tUser!=null){
            return tUser;
        }else{
            return AjaxResult.fail("登录失败！");
        }
    }

    @PostMapping("/login/PhoneAndPassword")
    public Object loginByPhoneAndPassword(@RequestParam String phone,
                                              @RequestParam String password){
        TUser tUser = tUserService.loginServiceByPhoneAndPassword(phone, password);
        if(tUser!=null){
            return tUser;
        }else{
            return AjaxResult.fail("登录失败！");
        }
    }

    @PostMapping("/register")
    public Object register(@RequestParam String username,
                               @RequestParam String email,
                               @RequestParam String phone,
                               @RequestParam String password){
        int temp = tUserService.registerService(username, email, phone, password);
        if(temp == 1){
            return AjaxResult.ok( "注册成功！");
        }else{
            return AjaxResult.fail("注册失败！");
        }
    }

}
