package cn.cd.service.impl;

import cn.cd.domain.TComment;
import cn.cd.mapper.CommentMapper;
import cn.cd.service.CommentService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;


@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, TComment>
        implements CommentService {

    @Resource
    private CommentMapper commentMapper;

    @Override
    public Page<TComment> getCommentsByCondition(String isbn, Long userId, Integer page) {
        Page<TComment> pageInfo = new Page<>(page, 10); // 固定分页大小
        return commentMapper.selectPageByCondition(pageInfo, isbn, userId);
    }
    @Override
    public boolean addComment(TComment comment) {
        return commentMapper.saveComment(comment) > 0;
    }

    @Override
    public boolean deleteComment(Long id) {  // [修改点] 参数改为id
        return commentMapper.deleteById(id) > 0;
    }
    @Override
    public Double getAverageRating(String isbn) {
        return commentMapper.getAverageRatingByIsbn(isbn);
    }

}