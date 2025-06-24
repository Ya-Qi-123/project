package cn.cd.service;

import cn.cd.domain.TBook;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.math.BigDecimal;
import java.util.List;

public interface BookService {
    List<TBook> HomePageService();
    int addBook(String isbn, String name, BigDecimal price,
                String author, String publisher,String category);
    int updateBook(TBook book);
    int batchDeleteBooks(List<Long> ids);
    TBook getBookById(Long id);
    // 增加分页查询接口方法
    Page<TBook> getBooksByPageForAdmin(
            int current, int size,
            String name, String author, String isbn);

    Page<TBook> getBooksByPageForUser(
            int current, int size, String name,
            String author, String isbn, String category);

    void updateBookStatus(Long id, Integer status);

    TBook getById(Long id);
}
