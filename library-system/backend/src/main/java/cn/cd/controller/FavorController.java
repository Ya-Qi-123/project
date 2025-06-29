package cn.cd.controller;

import cn.cd.domain.TFavor;
import cn.cd.domain.TUser;
import cn.cd.service.FavorService;
import cn.cd.service.UserService;
import cn.cd.util.AjaxResult;
import cn.cd.util.RedisUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/favorite")
public class FavorController {
    @Resource
    private UserService userService;
    @Autowired
    private FavorService favorService;
    @Resource
    private RedisUtil redisUtil;

    @PostMapping("/add")
    public AjaxResult addFavorite(@RequestParam String isbn,
                                  @RequestParam Long userId,
                                  HttpServletRequest request) {
        TUser currentUser = userService.getCurrentUser(request);
        if(!currentUser.getId().equals(userId)){
            throw new RuntimeException("用户权限不足");
        }
        boolean success = favorService.addFavorite(isbn, userId);
        return success ? AjaxResult.ok("收藏成功") : AjaxResult.fail("收藏失败或已收藏");
    }

    @DeleteMapping("/removeById")
    public AjaxResult removeFavorite(@RequestParam Long id, HttpServletRequest request) {
        TUser currentUser = userService.getCurrentUser(request);
        if(!currentUser.getId().equals( favorService.getById(id).getUserId())){
            throw new RuntimeException("用户权限不足");
        }
        boolean success = favorService.removeById(id);
        return success ? AjaxResult.ok("取消收藏成功") : AjaxResult.fail("取消收藏失败");
    }

    @GetMapping("/getByUserId")
    public AjaxResult getFavoritesByUserId(
            @RequestParam Long userId,
            @RequestParam(defaultValue = "1") Integer page) {
        Page<TFavor> favorites = favorService.getFavoritesByUserId(userId, page);
        return AjaxResult.ok(favorites);
    }
}