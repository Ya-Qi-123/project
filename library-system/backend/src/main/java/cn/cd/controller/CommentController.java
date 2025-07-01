package cn.cd.controller;

import cn.cd.domain.TComment;
import cn.cd.domain.TUser;
import cn.cd.service.CommentService;
import cn.cd.service.UserService;
import cn.cd.util.AjaxResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Resource
    private CommentService commentService;
    @Resource
    private UserService tUserService;

//    @GetMapping("/getCommentsByIsbn")
//    public AjaxResult getCommentsByIsbn(
//            @RequestParam String isbn,
//            @RequestParam(defaultValue = "1") Integer page,
//            @RequestParam(defaultValue = "10") Integer size) {
//        Page<TComment> comments = commentService.getCommentsByIsbn(isbn, page, size);
//        return AjaxResult.ok(comments);
//    }
//
//    @GetMapping("/getCommentsByUserId")
//    public AjaxResult getCommentsByUserId(
//            @RequestParam Long userId,
//            @RequestParam(defaultValue = "1") Integer page,
//            @RequestParam(defaultValue = "10") Integer size) {
//        Page<TComment> comments = commentService.getCommentsByUserId(userId, page, size);
//        return AjaxResult.ok(comments);
//    }

    @GetMapping("/pageQuery")
    public Page<TComment> pageQuery(
            @RequestParam(required = false) String isbn,
            @RequestParam(required = false) Long userId,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "5") Integer size) {
        return commentService.pageQuery(isbn, userId, page, size);
    }


    @PostMapping("/addComment")
    public AjaxResult addComment(@RequestParam String content,
                                 @RequestParam String isbn,
                                 @RequestParam Long userId,
                                 @RequestParam int rating,
                                 HttpServletRequest request) {
        TUser currentUser = tUserService.getCurrentUser(request);
        if (!currentUser.getId().equals(userId)) {
            throw new RuntimeException("用户权限不足");
        }
        boolean success = commentService.addComment(content, isbn, userId, rating);
        return success ? AjaxResult.ok("评论添加成功") : AjaxResult.fail("评论添加失败");
    }

    @DeleteMapping("/deleteCommentById")
    public AjaxResult deleteComment(@RequestParam Long id) {
        boolean success = commentService.removeById(id);
        return success ? AjaxResult.ok("评论删除成功") : AjaxResult.fail("评论删除失败");
    }

    // 获取图书平均评分
    @GetMapping("/getAverageRating")
    public AjaxResult getAverageRating(@RequestParam String isbn) {
        Double rating = commentService.getAverageRating(isbn);
        return AjaxResult.ok(rating != null ? rating : 0);
    }
}