package cn.cd.service;

import cn.cd.domain.TFavor;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

public interface FavorService extends IService<TFavor> {
    boolean addFavorite(String isbn, Long userId);

    boolean removeFavorite(Long id);

    Page<TFavor> getFavoritesByUserId(Long userId, Integer page, Integer size);

    Page<TFavor> pageQuery(String isbn, Long userId, Integer page, Integer size);
}