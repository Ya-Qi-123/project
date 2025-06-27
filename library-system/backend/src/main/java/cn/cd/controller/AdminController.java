package cn.cd.controller;

import cn.cd.domain.TAdmin;
import cn.cd.service.AdminService;
import cn.cd.service.UserService;
import cn.cd.util.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    // 管理员的邮箱密码登录
    @PostMapping("/login/EmailAndPassword")
    public Object login(@RequestParam String email,
                        @RequestParam String password,
                        HttpServletRequest request) {
        request.setAttribute("email", email);
        request.setAttribute("password", password);
        TAdmin admin = adminService.selectByEmailAndPassword(email, password);
        if(admin != null){
            return admin;
        }else{
            return AjaxResult.fail("登录失败！邮箱或密码错误");
        }

    }


}