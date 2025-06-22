package cn.cd.service;

import cn.cd.domain.TUser;

public interface UserService {
    TUser loginServiceByEmailAndPassword(String email, String password);
    TUser loginServiceByPhoneAndPassword(String phone, String password);

    int registerService(String username, String password, String email, String phone);
    int updateStatus(Long id, Integer status);

    int update(TUser tUser);
    int delete(Long id);
}
