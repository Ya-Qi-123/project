package cn.cd.mapper;

import cn.cd.domain.TUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;


public interface UserMapper extends BaseMapper<TUser> {
    void add(TUser tUser);
    int update(TUser tUser);
    void delete(Long id);

    TUser getByEmailAndPassword(String email, String password);
    TUser getByPhoneAndPassword(String phone, String password);
    TUser getByEmail(String email);
    TUser getByPhone(String phone);

    int register(String username, String password, String email, String phone);
    int updateStatus(Long id, Integer status);
}




