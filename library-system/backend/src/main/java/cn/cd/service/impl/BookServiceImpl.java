package cn.cd.service.impl;

import cn.cd.domain.TBook;
import cn.cd.mapper.BookMapper;
import cn.cd.service.BookService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, TBook> implements BookService {

    @Resource
    private BookMapper bookMapper;

//    @Override
//    public Page<TBook> getBooksByPageForAdmin(
//            int current, int size,
//            String name, String author, String isbn) {
//        Page<TBook> page = new Page<>(current, size);
//        return bookMapper.selectPageForAdmin(page, name, author, isbn);
//    }

    // 获取所有图书，实现分页查询、高级查询
    @Override
    public Page<TBook> getBooksByPage(int current, int size, String name,
                                      String author, String isbn, String category) {
        QueryWrapper<TBook> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(!StringUtils.isBlank(name), "name", name);
        queryWrapper.like(!StringUtils.isBlank(author), "author", author);
        queryWrapper.eq(!StringUtils.isBlank(isbn), "isbn", isbn);
        queryWrapper.like(!StringUtils.isBlank(category), "category", category);
        Page<TBook> page = new Page<>(current, size);
        return bookMapper.selectPage(page, queryWrapper);
    }

//    @Override
//    public Page<TBook> getBooksByPageForUser(int current, int size, String name,
//                                             String author, String isbn, String category) {
//        Page<TBook> page = new Page<>(current, size);
//        return bookMapper.selectPageForUser(page, name, author, isbn, category);
//    }


    @Override
    public void updateBookAvailableQuantity(Long id, int quantity) {
        UpdateWrapper<TBook> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", id)
                .setSql("available_quantity = available_quantity + " + quantity);
        bookMapper.update(updateWrapper);
    }

    @Override
    public int gatAvailableQuantityById(Long id) {
        return bookMapper.selectById(id).getAvailableQuantity();

//        return bookMapper.gatAvailableQuantityById(id);
    }

    @Override
    public int gatTotalQuantityById(Long id) {
        return bookMapper.selectById(id).getTotalQuantity();
    }


    @Override
    public List<TBook> HomePageService() {
        return bookMapper.selectList(null);
    }


    @Override
    public TBook getByISBN(String isbn) {
        QueryWrapper<TBook> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("isbn", isbn);
        return bookMapper.selectOne(queryWrapper);

//        return bookMapper.getByISBN(isbn);
    }

    @Override
    public int batchDeleteBooks(List<Long> ids) {
        return bookMapper.deleteByIds(ids);
//        return bookMapper.batchDeleteBooks(ids);
    }


}