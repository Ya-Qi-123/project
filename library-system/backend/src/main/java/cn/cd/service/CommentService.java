package cn.cd.service;

import cn.cd.domain.TComment;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

public interface CommentService extends IService<TComment> {

    Page<TComment> getCommentsByCondition(String isbn, Long userId, Integer page);

    boolean addComment(TComment comment);
    boolean deleteComment(Long id);

    Double getAverageRating(String isbn);

}