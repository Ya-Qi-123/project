package cn.cd.mapper;

import cn.cd.domain.TUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;


public interface UserMapper extends BaseMapper<TUser> {
    void add(TUser tUser);

    // 修改用户信息
    int updatePersonalInformation(Long id, String username, String email, String phone, String gender);
    int updatePassword(Long id, String password);
    int updateStatus(Long id, Integer status);

    int delete(Long id);

    TUser getByEmailAndPassword(String email, String password);
    TUser getByPhoneAndPassword(String phone, String password);
    TUser getByEmail(String email);
    TUser getByPhone(String phone);
    String getPasswordById(Long id);
    Long getUseridById(Long id);

    int register(String username, String password, String email, String phone);

}




