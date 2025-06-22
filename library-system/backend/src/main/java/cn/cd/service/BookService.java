package cn.cd.service;

import cn.cd.domain.TBook;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

public interface BookService {
    List<TBook> HomePageService();
    int addBook(TBook book);
    int updateBook(TBook book);
    int batchDeleteBooks(List<Long> ids);
    TBook getBookById(Long id);
    // 增加分页查询接口方法
    Page<TBook> getBooksByPage(
            int current, int size,
            String name, String author, String isbn);
}
