package cn.cd.service.impl;

import cn.cd.domain.TBook;
import cn.cd.mapper.BookMapper;
import cn.cd.service.BookService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, TBook> implements BookService {

    @Resource
    private BookMapper bookMapper;

    @Override
    public Page<TBook> getBooksByPageForAdmin(
            int current, int size,
            String name, String author, String isbn) {
        Page<TBook> page = new Page<>(current, size);
        return bookMapper.selectPageForAdmin(page, name, author, isbn);
    }

    @Override
    public Page<TBook> getBooksByPageForUser(int current, int size, String name,
                                             String author, String isbn, String category) {
        Page<TBook> page = new Page<>(current, size);
        return bookMapper.selectPageForUser(page, name, author, isbn, category);
    }


    @Override
    public TBook getById(Long id) {
        return bookMapper.getById(id);
    }

    @Override
    public void updateBookAvailableQuantity(Long id, int quantity) {
        bookMapper.updateBookAvailableQuantity(id, quantity);
    }

    @Override
    public int gatAvailableQuantityById(Long id) {
        return bookMapper.gatAvailableQuantityById(id);
    }

    @Override
    public int gatTotalQuantityById(Long id) {
        return bookMapper.gatTotalQuantityById(id);
    }


    @Override
    public List<TBook> HomePageService() {
        return bookMapper.getAll();
    }

    @Override
    public int addBook(String isbn, String name, BigDecimal price,
                       String author, String publisher,String category,
                       String language, String introduction, int total_quantity) {
        return bookMapper.add(isbn, name, price, author, publisher, category,
                language, introduction, total_quantity);
    }

    @Override
    public int updateBook(TBook book) {
        return bookMapper.update(book);
    }

    @Override
    public TBook getBookById(Long id) {
        return bookMapper.getById(id);
    }

    @Override
    public TBook getByISBN(String isbn) {
        return bookMapper.getByISBN(isbn);
    }

    @Override
    public int batchDeleteBooks(List<Long> ids ) {
        return bookMapper.batchDeleteBooks(ids);
    }

    @Override
    public void updateTotalAndAvailable(Long id, int changeNum) {
        bookMapper.updateTotalAndAvailable(id, changeNum);
    }

}