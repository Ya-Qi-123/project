package cn.cd.mapper;

import cn.cd.domain.TFavor;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FavorMapper extends BaseMapper<TFavor> {
    Page<TFavor> selectByUserId(Page<TFavor> page, Long userId); // 修改为分页查询
}