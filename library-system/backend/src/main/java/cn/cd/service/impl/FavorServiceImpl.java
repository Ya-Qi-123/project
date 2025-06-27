package cn.cd.service.impl;

import cn.cd.domain.TFavor;
import cn.cd.mapper.FavorMapper;
import cn.cd.service.FavorService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class FavorServiceImpl extends ServiceImpl<FavorMapper, TFavor> implements FavorService {

    @Override
    public boolean addFavorite(TFavor favor) {
        // 检查是否已收藏
        if (lambdaQuery()
                .eq(TFavor::getUserId, favor.getUserId())
                .eq(TFavor::getIsbn, favor.getIsbn())
                .count() > 0) {
            return false;
        }
        return save(favor);
    }

    @Override
    public boolean removeFavorite(Long id) {
        return false;
    }


    @Override
    public Page<TFavor> getFavoritesByUserId(Long userId, Integer page) {
        Page<TFavor> pageInfo = new Page<>(page, 10);
        return lambdaQuery()
                .eq(TFavor::getUserId, userId)
                .page(pageInfo);
    }
}