package cn.cd.controller;

import cn.cd.domain.TAdmin;
import cn.cd.service.AdminService;
import cn.cd.util.AjaxResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@Tag(name = "管理员管理")
public class AdminController {

    @Resource
    private AdminService adminService;

    // 管理员的邮箱密码登录
    @PostMapping("/login/EmailAndPassword")
    @Operation(summary = "管理员邮箱密码登录")
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

    @PostMapping("/addAdmin")
    public Object addAdmin(@RequestParam String adminname,
                          @RequestParam String password,
                          @RequestParam String email) {
        TAdmin admin = new TAdmin();
        admin.setAdminname(adminname);
        admin.setPassword(password);
        admin.setEmail(email);
        return admin;
    }

}