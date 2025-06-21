package cn.cd.mapper;

import cn.cd.domain.TBook;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface BookMapper extends BaseMapper<TBook> {
    void add(TBook book);
    void update(TBook book);
    void delete(Long id);

    TBook getById(Long id);
    TBook getByName(String name);
    TBook getByAuthor(String author);
    TBook getByISBN(String isbn);

    List<TBook> getAll();


}
