package cn.cd.mapper;

import cn.cd.domain.TAdmin;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminMapper {
    void add(TAdmin admin);
    void update(TAdmin admin);
    void delete(Long id);

    TAdmin selectByEmailAndPassword(String email, String password);

}