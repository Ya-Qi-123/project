package cn.cd.service;

import cn.cd.domain.TBook;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;
import java.util.List;

public interface BookService extends IService<TBook> {
    List<TBook> HomePageService();

    void updateBook(TBook book);
    int batchDeleteBooks(List<Long> ids);
    void updateTotalAndAvailable(Long id, int changeNum);
    TBook getByISBN(String isbn);

    // 增加分页查询接口方法
    Page<TBook> getBooksByPage(
            int current, int size, String name,
            String author, String isbn, String category);


    void updateBookAvailableQuantity(Long id, int changeNum);

    int gatAvailableQuantityById(Long id);

    int gatTotalQuantityById(Long id);
}
