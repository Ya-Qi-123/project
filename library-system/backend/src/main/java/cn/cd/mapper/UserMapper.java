package cn.cd.mapper;

import cn.cd.domain.TUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author Lenovo
* @description 针对表【t_user】的数据库操作Mapper
* @createDate 2025-06-21 09:03:06
* @Entity generator.domain.TUser
*/

public interface UserMapper extends BaseMapper<TUser> {
    void add(TUser tUser);
    void update(TUser tUser);
    void delete(Long id);

    TUser getByEmailAndPassword(String email, String password);
    TUser getByPhoneAndPassword(String phone, String password);
    TUser getByEmail(String email);
    TUser getByPhone(String phone);

    int register(String username, String password, String email, String phone);

}




