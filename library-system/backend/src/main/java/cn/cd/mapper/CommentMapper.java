package cn.cd.mapper;

import cn.cd.domain.TComment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

public interface CommentMapper extends BaseMapper<TComment> {

    Page<TComment> selectPageByCondition(
            Page<TComment> page,
            @Param("isbn") String isbn,
            @Param("userId") Long userId);

    Double getAverageRatingByIsbn(@Param("isbn") String isbn);
    int deleteById(@Param("id") Long id);
    int saveComment(TComment comment);

}