package cn.cd.service.impl;
import cn.cd.domain.TBook;
import cn.cd.domain.TComment;
import cn.cd.mapper.BookMapper;
import cn.cd.service.BookService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.ISelect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import static com.baomidou.mybatisplus.core.toolkit.Wrappers.lambdaQuery;
import static com.github.pagehelper.page.PageMethod.count;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookMapper bookMapper;
    @Override
    public Page<TBook> getBooksByPageForAdmin(
            int current, int size, String name,
            String author, String isbn, String category) {
        Page<TBook> page = new Page<>(current, size);
        return bookMapper.selectPageForAdmin(page, name, author, isbn,category);
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