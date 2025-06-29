package cn.cd.service.impl;

import cn.cd.exception.BusinessException;
import cn.cd.util.RedisUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.cd.domain.TUser;
import cn.cd.service.UserService;
import cn.cd.mapper.UserMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;


@Service
public class UserServiceImpl
        extends ServiceImpl<UserMapper, TUser>
        implements UserService {
    private static final long EXPIRE_TIME = 10;
    @Resource
    private UserMapper tUserMapper;
    @Resource
    private RedisUtil redisUtil;


    // TUserServiceImpl 中新增的邮箱登录方法
    @Override
    public TUser loginServiceByEmailAndPassword(String email, String password) {
        TUser tUser = tUserMapper.getByEmailAndPassword(email, password); // Mapper 层查询邮箱+密码匹配的用户
        return tUser;
    }

    @Override
    public TUser loginServiceByPhoneAndPassword(String phone, String password) {
        TUser tUser = tUserMapper.getByPhoneAndPassword(phone, password); // Mapper 层查询邮箱+密码匹配的用户
        return tUser;
    }

    @Override
    public int registerService(String username, String password,
                                 String email, String phone) {
        return tUserMapper.register(username, password, email, phone);
    }

    @Override
    public int updateStatus(Long id, Integer status) {
        return tUserMapper.updateStatus(id, status);
    }

    @Override
    public int delete(Long id) {
        return tUserMapper.deleteById(id);
    }

    @Override
    public int updatePersonalInformation(Long id, String username, String email,
                                         String phone, String gender) {
        return tUserMapper.updatePersonalInformation(id, username, email, phone, gender);
    }

    @Override
    public TUser getByEmail(String email) {
        return tUserMapper.getByEmail(email);
    }

    @Override
    public TUser getByPhone(String phone) {
        return tUserMapper.getByPhone(phone);
    }

    @Override
    public TUser getByUsername(String username) {
        return tUserMapper.getByUsername(username);
    }

    /**
     * @param request 请求
     * @return 当前用户
     */
    @Override
    public TUser getCurrentUser(HttpServletRequest request) {
        String sessionId = request.getSession().getId();
        String userJson = redisUtil.get(sessionId);
        if (userJson == null) {
            throw new BusinessException("账号未登录");
        }
        TUser currentUser = JSON.parseObject(userJson, TUser.class);
        if (currentUser == null) {
            throw new BusinessException("账号未登录");
        }
        // 续期
        redisUtil.expire(sessionId, EXPIRE_TIME, TimeUnit.SECONDS);
        return currentUser;
    }

}




