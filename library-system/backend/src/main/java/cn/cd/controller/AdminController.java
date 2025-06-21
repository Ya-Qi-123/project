package cn.cd.controller;

import cn.cd.domain.TAdmin;
import cn.cd.service.AdminService;
import cn.cd.util.AjaxResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/login/EmailAndPassword")
    public Object login(@RequestParam String email,
                        @RequestParam String password) {
        TAdmin admin = adminService.selectByEmailAndPassword(email, password);
        if(admin!=null){
            return admin;
        }else{
            return AjaxResult.fail("登录失败！");
        }
    }
}