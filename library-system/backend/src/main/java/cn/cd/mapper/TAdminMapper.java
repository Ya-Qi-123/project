package cn.cd.mapper;

import cn.cd.domain.TAdmin;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TAdminMapper {
    int add(TAdmin admin);
    int update(TAdmin admin);
    int delete(Long id);
    TAdmin getById(Long id);
    TAdmin getByAdminname(String adminname);
    TAdmin getByEmail(String email);
    TAdmin getByEmailAndPassword(String email, String password);
    TAdmin selectByAdminnameAndPassword(String adminname, String password);

}