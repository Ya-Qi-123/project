package cn.cd.mapper;

import cn.cd.domain.TBook;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface BookMapper extends BaseMapper<TBook> {
    int add(TBook book);
    int update(TBook book);
    int batchDeleteBooks(List<Long> ids);

    TBook getById(Long id);
    TBook getByName(String name);
    TBook getByAuthor(String author);
    TBook getByISBN(String isbn);

    List<TBook> getAll();

    // 分页查询
    Page<TBook> selectPage(
            Page<TBook> page,
            @Param("name") String name,
            @Param("author") String author,
            @Param("isbn") String isbn);


}