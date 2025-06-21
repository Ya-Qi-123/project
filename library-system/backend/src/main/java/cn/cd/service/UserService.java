package cn.cd.service;

import cn.cd.domain.TUser;

/**
* @author Lenovo
* @description 针对表【t_user】的数据库操作Service
* @createDate 2025-06-21 09:03:06
*/

public interface UserService {
    TUser loginServiceByEmailAndPassword(String email, String password);
    TUser loginServiceByPhoneAndPassword(String phone, String password);

    int registerService(String username, String password, String email, String phone);

}
