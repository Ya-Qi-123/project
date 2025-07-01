package cn.cd.service;

import cn.cd.domain.TUser;
import cn.cd.query.UserQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletRequest;


public interface UserService extends IService<TUser> {

    TUser loginServiceByEmailAndPassword(String email, String password);

    TUser loginServiceByPhoneAndPassword(String phone, String password);

    int registerService(String username, String password, String email, String phone);

    int updateStatus(Long id);


    int updatePersonalInformation(Long id, String username, String email, String phone, String gender);

    TUser getByEmail(String email);

    TUser getByPhone(String phone);

    TUser getCurrentUser(HttpServletRequest request);

    Page<TUser> pageQuery(UserQuery userQuery);
}
