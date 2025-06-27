package cn.cd.controller;

import cn.cd.domain.TFavor;
import cn.cd.service.FavorService;
import cn.cd.util.AjaxResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/favorite")
public class FavorController {

    @Autowired
    private FavorService favorService;

    @PostMapping("/add")
    public AjaxResult addFavorite(@RequestBody TFavor favor) {
        boolean success = favorService.addFavorite(favor);
        return success ? AjaxResult.ok("收藏成功") : AjaxResult.fail("收藏失败或已收藏");
    }

    @DeleteMapping("/remove/{id}")
    public AjaxResult removeFavorite(@PathVariable Long id) {
        boolean success = favorService.removeFavorite(id);
        return success ? AjaxResult.ok("取消收藏成功") : AjaxResult.fail("取消收藏失败");
    }

    @GetMapping("/user/{userId}")
    public AjaxResult getFavoritesByUserId(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "1") Integer page) {
        Page<TFavor> favorites = favorService.getFavoritesByUserId(userId, page);
        return AjaxResult.ok(favorites);
    }
}