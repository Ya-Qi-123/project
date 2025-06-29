package cn.cd.mapper;

import cn.cd.domain.TBook;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.List;

public interface BookMapper extends BaseMapper<TBook> {
    int add(String isbn, String name, BigDecimal price,
            String author, String publisher,String category,
            String language, String introduction, int total_quantity);

    int update(TBook book);

    int batchDeleteBooks(List<Long> ids);

    TBook getById(Long id);
    TBook getByName(String name);
    TBook getByAuthor(String author);
    TBook getByISBN(String isbn);
    List<TBook> getAll();

    // 管理员用的分页查询
    Page<TBook> selectPageForAdmin(
            Page<TBook> page,
            @Param("name") String name,
            @Param("author") String author,
            @Param("isbn") String isbn,
            @Param("category") String category
    );

    // 用户用的分页查询
    Page<TBook> selectPageForUser(
            Page<TBook> page,
            @Param("name") String name,
            @Param("author") String author,
            @Param("isbn") String isbn,
            @Param("category") String category

    );

    void updateBookAvailableQuantity(Long id, int quantity);
    void updateTotalAndAvailable(Long id, int changeNum);

    int gatAvailableQuantityById(Long id);

    int gatTotalQuantityById(Long id);
}