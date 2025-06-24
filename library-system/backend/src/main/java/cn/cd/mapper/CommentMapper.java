package cn.cd.mapper;

import cn.cd.domain.TComment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

public interface CommentMapper extends BaseMapper<TComment> {
    // 根据ISBN分页查询评论
    Page<TComment> selectPageByIsbn(Page<TComment> page, @Param("isbn") String isbn);

    // 根据用户ID分页查询评论
    Page<TComment> selectPageByUserId(Page<TComment> page, @Param("userId") Long userId);

    // 计算某ISBN图书的平均评分
    Double getAverageRatingByIsbn(@Param("isbn") String isbn);

    // 根据ISBN和用户ID删除评论
    int deleteByIsbnAndUserId(@Param("isbn") String isbn, @Param("userId") Long userId);
}