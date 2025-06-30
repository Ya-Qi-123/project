package cn.cd.service.impl;

import cn.cd.domain.TComment;
import cn.cd.mapper.CommentMapper;
import cn.cd.service.CommentService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, TComment> implements CommentService {
    @Resource
    private CommentMapper commentMapper;

    @Override
    public Page<TComment> getCommentsByIsbn(String isbn, Integer page, Integer size) {
        QueryWrapper<TComment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("isbn", isbn);
        Page<TComment> pageInfo = new Page<>(page, size);
        return commentMapper.selectPage(pageInfo, queryWrapper);
//        Page<TComment> pageInfo = new Page<>(page, size);
//        return commentMapper.selectPageByIsbn(pageInfo, isbn);
    }

    @Override
    public Page<TComment> getCommentsByUserId(Long userId, Integer page, Integer size) {
        QueryWrapper<TComment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        Page<TComment> pageInfo = new Page<>(page, size);
        return commentMapper.selectPage(pageInfo, queryWrapper);

//        Page<TComment> pageInfo = new Page<>(page, size);
//        return commentMapper.selectPageByUserId(pageInfo, userId);
    }

    @Override
    public boolean addComment(String content, String isbn, Long userId) {
        TComment comment = new TComment();
        comment.setContent(content);
        comment.setIsbn(isbn);
        comment.setUserId(userId);
        return commentMapper.save(comment);
//        return commentMapper.save(comment);
    }

    @Override
    public boolean deleteComment(String isbn, Long userId) {
        QueryWrapper<TComment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("isbn", isbn).eq("user_id", userId);
        return commentMapper.delete(queryWrapper) > 0;

//        return commentMapper.deleteByIsbnAndUserId(isbn, userId) > 0;
    }

    @Override
    public Double getAverageRating(String isbn) {
        return commentMapper.getAverageRatingByIsbn(isbn);
    }

    @Override
    public Page<TComment> pageQuery(String isbn, Long userId, Integer page, Integer size) {
        Page<TComment> pageInfo = new Page<>(page, size);
        return commentMapper.selectPageByCondition(pageInfo, isbn, userId);

//        QueryWrapper<TComment> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq(StringUtils.isNotBlank(isbn), "isbn", isbn)
//                .eq(userId != null, "user_id", userId);
//        Page<TComment> pageInfo = new Page<>(page, size);
//        return commentMapper.selectPage(pageInfo, queryWrapper);
    }
}