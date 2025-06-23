package cn.cd.service.impl;
import cn.cd.domain.TBook;
import cn.cd.mapper.BookMapper;
import cn.cd.service.BookService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
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
    public List<TBook> HomePageService() {
        return bookMapper.getAll();
    }

    @Override
    public int addBook(String isbn, String name, BigDecimal price,
                       String author, String publisher, String category) {
        return bookMapper.add(isbn,name,price, author,publisher,category);
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
    public int batchDeleteBooks(List<Long> ids) {
        return bookMapper.batchDeleteBooks(ids);
    }

}