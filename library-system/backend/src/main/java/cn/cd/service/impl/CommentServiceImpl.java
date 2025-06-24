package cn.cd.service.impl;

import cn.cd.domain.TComment;
import cn.cd.mapper.CommentMapper;
import cn.cd.service.CommentService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, TComment> implements CommentService {
    @Autowired
    private CommentMapper commentMapper;

    @Override
    public Page<TComment> getCommentsByIsbn(String isbn, Integer page, Integer size) {
        Page<TComment> pageInfo = new Page<>(page, size);
        return commentMapper.selectPageByIsbn(pageInfo, isbn);
    }

    @Override
    public Page<TComment> getCommentsByUserId(Long userId, Integer page, Integer size) {
        Page<TComment> pageInfo = new Page<>(page, size);
        return commentMapper.selectPageByUserId(pageInfo, userId);
    }

    @Override
    public boolean addComment(TComment comment) {
        return save(comment);
    }

    @Override
    public boolean deleteComment(String isbn, Long userId) {
        return commentMapper.deleteByIsbnAndUserId(isbn, userId) > 0;
    }

    @Override
    public Double getAverageRating(String isbn) {
        return commentMapper.getAverageRatingByIsbn(isbn);
    }
}