package cn.cd.controller;

import cn.cd.domain.TAdmin;
import cn.cd.service.TAdminService;
import cn.cd.util.AjaxResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class TAdminController {

    private TAdminService adminService;

    public TAdminController(TAdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/login/AdminnameAndPassword")
    public Object login(@RequestParam String adminname,
                        @RequestParam String password) {
        TAdmin admin = adminService.getByAdminnameAndPassword(adminname, password);
        if(admin!=null){
            return AjaxResult.ok( "登录成功！");
        }else{
            return AjaxResult.fail("登录失败！");
        }
    }
}