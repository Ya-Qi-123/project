package cn.cd.controller;

import cn.cd.domain.TBook;
import cn.cd.domain.TComment;
import cn.cd.service.CommentService;
import cn.cd.util.AjaxResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
public class CommentController {
    @Resource
    private CommentService commentService;
    //id和isbn一起查询
    @GetMapping("/getByCondition")
    public AjaxResult getByCondition(
            @RequestParam(required = false) String isbn,
            @RequestParam(required = false) Long userId,
            @RequestParam(defaultValue = "1") Integer page) {
        Page<TComment> result = commentService.getCommentsByCondition(isbn, userId, page);
        return AjaxResult.ok(result);
    }
    // 创建评论
    @PostMapping("/createComment")
    public AjaxResult createComment(@RequestBody TComment comment) {
        boolean success = commentService.addComment(comment);
        return success ? AjaxResult.ok("评论添加成功") : AjaxResult.fail("评论添加失败");
    }

    // 删除评论
    @DeleteMapping("/removeComment")
    public AjaxResult removeComment(@RequestParam Long id) {
        boolean success = commentService.deleteComment(id);
        return success ? AjaxResult.ok("评论删除成功") : AjaxResult.fail("评论删除失败");
    }

    // 获取图书平均评分
    @GetMapping("/getBookRating")
    public AjaxResult getBookRating(@RequestParam String isbn) {
        Double rating = commentService.getAverageRating(isbn);
        return AjaxResult.ok(rating != null ? rating : 0);
    }
}