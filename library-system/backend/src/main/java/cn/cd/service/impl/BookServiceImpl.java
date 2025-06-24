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
    public void updateBookStatus(Long id, Integer status) {
        bookMapper.updateBookStatus(id, status);
    }

    @Override
    public TBook getById(Long id) {
        return bookMapper.getById(id);
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