package cn.cd.mapper;

import cn.cd.domain.TAdmin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminMapper extends BaseMapper<TAdmin> {

    TAdmin selectByEmailAndPassword(String email, String password);

}