package cn.cd.service;

import cn.cd.domain.TBook;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;
import java.util.List;

public interface BookService extends IService<TBook> {
    List<TBook> HomePageService();

    int addBook(String isbn, String name, BigDecimal price,
                String author, String publisher,String category,
                String language, String introduction, int total_quantity);

    int updateBook(TBook book);
    int batchDeleteBooks(List<Long> ids);
    void updateTotalAndAvailable(Long id, int changeNum);
    TBook getBookById(Long id);
    TBook getByISBN(String isbn);
    // 增加分页查询接口方法
    Page<TBook> getBooksByPageForAdmin(
            int current, int size,
            String name, String author, String isbn);

    Page<TBook> getBooksByPageForUser(
            int current, int size, String name,
            String author, String isbn, String category);


    TBook getById(Long id);
    void updateBookAvailableQuantity(Long id, int changeNum);

    int gatAvailableQuantityById(Long id);

    int gatTotalQuantityById(Long id);
}
