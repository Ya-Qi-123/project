package cn.cd.service.impl;

import cn.cd.domain.TFavor;
import cn.cd.mapper.FavorMapper;
import cn.cd.service.FavorService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
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
        // 1. 验证收藏记录是否存在
        TFavor favor = getById(id);
        if (favor == null) {
            throw new RuntimeException("收藏记录不存在");
        }
        return removeById(id);
    }
    @Override
    public Page<TFavor> getFavoritesByUserId(Long userId, Integer page) {
        Page<TFavor> pageInfo = new Page<>(page, 10);
        return lambdaQuery()
                .eq(TFavor::getUserId, userId)
                .page(pageInfo);
    }
}