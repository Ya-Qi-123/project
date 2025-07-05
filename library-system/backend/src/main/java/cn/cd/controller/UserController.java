package cn.cd.controller;

import cn.cd.domain.TUser;
import cn.cd.query.UserQuery;
import cn.cd.service.LendService;
import cn.cd.service.UserService;
import cn.cd.util.AjaxResult;
import cn.cd.util.RedisUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
@Tag(name = "用户管理")
public class UserController {
    @Resource
    private UserService tUserService;
    @Resource
    private LendService lendService;
    @Resource
    private RedisUtil redisUtils;


    // 用户的邮箱密码登录
    @PostMapping("/login/EmailAndPassword")
    @Parameters({
            @Parameter(name = "email", description = "用户邮箱", required = true),
            @Parameter(name = "password", description = "用户密码", required = true)
    })
    public Object loginByEmailAndPassword(@RequestParam String email,
                                          @RequestParam String password,
                                          HttpServletRequest request) {
        TUser tUser = tUserService.loginServiceByEmailAndPassword(email, password);
        if (tUser == null) {
            return AjaxResult.fail("登录失败,邮箱或密码错误。");
        } else {
            if (tUser.getStatus() == 1) {
                // TODO:登录成功，将用户信息存入Session
                String sessionId = request.getSession().getId(); // 使用 session ID 作为 Redis 的 key
                redisUtils.set(sessionId, JSON.toJSONString(tUser), 6000, TimeUnit.SECONDS);
                return tUser;
            } else {
                return AjaxResult.fail("登录失败,该账号无法使用");
            }
        }
    }

    // 用户的手机号密码登录
    @PostMapping("/login/PhoneAndPassword")
    @Parameters({
            @Parameter(name = "phone", description = "用户手机号", required = true),
            @Parameter(name = "password", description = "用户密码", required = true)
    })
    public Object loginByPhoneAndPassword(@RequestParam String phone,
                                          @RequestParam String password,
                                          HttpServletRequest request) {
        TUser tUser = tUserService.loginServiceByPhoneAndPassword(phone, password);
        if (tUser == null) {
            return AjaxResult.fail("登录失败，手机号或密码错误。");
        } else {
            if (tUser.getStatus() == 1) {
                // TODO:登录成功，将用户信息存入Session
                String sessionId = request.getSession().getId();
                redisUtils.set(sessionId, JSON.toJSONString(tUser), 6000, TimeUnit.SECONDS);
                return tUser;
            } else {
                return AjaxResult.fail("登录失败,该账号无法使用");
            }
        }
    }

    // 用户的注册
    @PostMapping("/register")
    public Object register(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String email,
                           @RequestParam String phone) {
        TUser tUser1 = tUserService.getByEmail(email);
        TUser tUser2 = tUserService.getByPhone(phone);
        if (tUser1 != null || tUser2 != null) {
            return AjaxResult.fail("注册失败,邮箱或手机号已注册");
        }
        int temp = tUserService.registerService(username, password, email, phone);
        if (temp == 1) {
            return AjaxResult.ok("注册成功！");
        } else {
            return AjaxResult.fail("注册失败！");
        }
    }

    // 管理员修改用户状态
    @PostMapping("/updateStatus")
    public Object updateStatus(@RequestParam Long id) {
        if (tUserService.getById(id) == null) {
            return AjaxResult.fail("ID错误，用户不存在！");
        }
        if (tUserService.updateStatus(id) == 1) {
            return AjaxResult.ok("状态修改成功！");
        } else {
            return AjaxResult.fail("状态修改失败！");
        }
    }

    // 用户个人信息修改
    @PostMapping("/updatePersonalInformation")
    public Object update(@RequestParam Long id, @RequestParam String username,
                         @RequestParam String email, @RequestParam String phone,
                         @RequestParam(required = false) String gender,
                         HttpServletRequest request) {
        TUser currentUser = tUserService.getCurrentUser(request);
        if (!Objects.equals(currentUser.getId(), id)) {
            return AjaxResult.fail("用户权限不足无法修改");
        }
        if (tUserService.updatePersonalInformation(id, username, email, phone, gender) == 1) {
            return AjaxResult.ok("修改成功！");
        } else {
            return AjaxResult.fail("修改失败！");
        }
    }

    // 删除用户
    @DeleteMapping("/deleteById")
    public Object delete(@RequestParam Long id) {
        Integer statusSum = lendService.getStatus(id);
        if (id == -1) {
            return AjaxResult.fail("该用户无法删除！");
        }
        if (statusSum != null && statusSum > 0) {
            return AjaxResult.fail("该用户有未还的图书，请先还图书！");
        }
        if (tUserService.removeById(id)) {
            return AjaxResult.ok("删除成功！");
        } else {
            return AjaxResult.fail("删除失败！");
        }
    }

    // 修改用户密码
    @PostMapping("/updatePassword")
    public Object updatePassword(@RequestParam Long id,
                                 @RequestParam String oldPassword,
                                 @RequestParam String newPassword,
                                 HttpServletRequest request) {
        TUser currentUser = tUserService.getCurrentUser(request);
        if (!Objects.equals(currentUser.getId(), id)) {
            return AjaxResult.fail("用户权限不足");
        }
        if (newPassword.equals(null) || newPassword.equals(oldPassword)) {
            return AjaxResult.fail("修改密码失败，新密码为空或与原密码相同。");
        } else {
            TUser tUser = tUserService.getById(id);
            if (tUser.getPassword().equals(oldPassword)) {
                UpdateWrapper<TUser> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("id", id);
                updateWrapper.set("password", newPassword);
                tUserService.update(updateWrapper);
                return AjaxResult.ok("修改密码成功！");
            } else {
                return AjaxResult.fail("修改失败，原密码错误！");
            }
        }
    }

    // 管理员重置用户密码
    @PostMapping("/resetPassword")
    public Object resetPassword(@RequestParam Long id) {
        TUser tUser = tUserService.getById(id);
        if (tUser == null) {
            return AjaxResult.fail("用户不存在！");
        } else {
            tUser.setPassword("123456");
            tUserService.updateById(tUser);
            return AjaxResult.ok("密码重置成功！");
        }
    }

    // 用户退出登录
    @GetMapping("/logout")
    public Object logout(HttpServletRequest request) {
        // TODO:清除Session
        redisUtils.delete(request.getSession().getId());
        return AjaxResult.ok("退出登录成功！");
    }

    @PostMapping("/pageQueryUser")
    public Page<TUser> pageQuery(@RequestBody UserQuery userQuery) {
        return tUserService.pageQuery(userQuery);
    }

}
