package cn.cd.service.impl;

import cn.cd.domain.TBook;
import cn.cd.domain.TFavor;
import cn.cd.exception.BusinessException;
import cn.cd.mapper.BookMapper;
import cn.cd.mapper.FavorMapper;
import cn.cd.service.FavorService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class FavorServiceImpl extends ServiceImpl<FavorMapper, TFavor> implements FavorService {

    @Resource
    private FavorMapper favorMapper;
    @Resource
    private BookMapper bookMapper;

    @Override
    public boolean addFavorite(String isbn, Long userId) {
        // 查询是否已收藏
        QueryWrapper<TFavor> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.eq("isbn", isbn);
        if (favorMapper.exists(queryWrapper)) {
            throw new BusinessException("已收藏");
        }

        // 插入数据
        TBook book = bookMapper.getByISBN(isbn);
        TFavor favor = new TFavor();
        favor.setIsbn(isbn);
        favor.setUserId(userId);
        favor.setBookName(book.getName());
        favor.setAuthor(book.getAuthor());
        return favorMapper.insert(favor) > 0;

//        // 检查是否已收藏
//        if (lambdaQuery()
//                .eq(TFavor::getUserId, favor.getUserId())
//                .eq(TFavor::getIsbn, favor.getIsbn())
//                .count() > 0) {
//            return false;
//        }
//        return save(favor);
    }

    @Override
    public boolean removeFavorite(Long id) {
        // 1. 验证收藏记录是否存在
        TFavor favor = getById(id);
        if (favor == null) {
            throw new RuntimeException("收藏记录不存在");
        }
        return removeById(id);
    }

    @Override
    public Page<TFavor> getFavoritesByUserId(Long userId, Integer page, Integer size) {
        QueryWrapper<TFavor> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        Page<TFavor> pageInfo = new Page<>(page, size);
        return favorMapper.selectPage(pageInfo, queryWrapper);

//        Page<TFavor> pageInfo = new Page<>(page, 10);
//        return lambdaQuery()
//                .eq(TFavor::getUserId, userId)
//                .page(pageInfo);
    }

    @Override
    public Page<TFavor> pageQuery(String isbn, Long userId, Integer page, Integer size) {
        Page<TFavor> pageInfo = new Page<>(page, size);
        QueryWrapper<TFavor> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(isbn), "isbn", isbn)
                .eq(userId != null, "user_id", userId);
        return favorMapper.selectPage(pageInfo, queryWrapper);
    }
}