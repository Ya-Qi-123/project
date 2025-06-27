package cn.cd.service;

import cn.cd.domain.TFavor;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;

public interface FavorService {
    boolean addFavorite(TFavor favor);
    boolean removeFavorite(Long id);
    Page<TFavor> getFavoritesByUserId(Long userId, Integer page);
}