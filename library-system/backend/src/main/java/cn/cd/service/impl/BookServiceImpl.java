package cn.cd.service.impl;

import cn.cd.domain.TBook;
import cn.cd.mapper.BookMapper;
import cn.cd.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookMapper bookMapper;


    @Override
    public List<TBook> HomePageService() {
        return bookMapper.getAll();
    }
}
