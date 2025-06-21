package cn.cd.service;

import cn.cd.domain.TUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Lenovo
* @description 针对表【t_user】的数据库操作Service
* @createDate 2025-06-21 09:03:06
*/

public interface TUserService extends IService<TUser> {
    TUser loginServiceByEmailAndPassword(String email, String password);
    TUser loginServiceByPhoneAndPassword(String phone, String password);

    TUser registerServiceByEmailAndPassword(TUser tUser);
}
