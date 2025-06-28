package cn.cd.controller;

import cn.cd.domain.TComment;
import cn.cd.service.CommentService;
import cn.cd.util.AjaxResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Resource
    private CommentService commentService;

    @GetMapping("/isbn/{isbn}")
    public AjaxResult getCommentsByIsbn(
            @PathVariable String isbn,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        Page<TComment> comments = commentService.getCommentsByIsbn(isbn, page, size);
        return AjaxResult.ok(comments);
    }

    @GetMapping("/user/{userId}")
    public AjaxResult getCommentsByUserId(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        Page<TComment> comments = commentService.getCommentsByUserId(userId, page, size);
        return AjaxResult.ok(comments);
    }

    @PostMapping
    public AjaxResult addComment(@RequestBody TComment comment) {

        boolean success = commentService.addComment(comment);
        return success ? AjaxResult.ok("评论添加成功") : AjaxResult.fail("评论添加失败");
    }

    @DeleteMapping
    public AjaxResult deleteComment(
            @RequestParam String isbn,
            @RequestParam Long userId) {
        boolean success = commentService.deleteComment(isbn, userId);
        return success ? AjaxResult.ok("评论删除成功") : AjaxResult.fail("评论删除失败");
    }

    // 获取图书平均评分
    @GetMapping("/rating/{isbn}")
    public AjaxResult getAverageRating(@PathVariable String isbn) {
        Double rating = commentService.getAverageRating(isbn);
        return AjaxResult.ok(rating != null ? rating : 0);
    }
}