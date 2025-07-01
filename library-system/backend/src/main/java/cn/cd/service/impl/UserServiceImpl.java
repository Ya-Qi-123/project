package cn.cd.service.impl;

import cn.cd.domain.TUser;
import cn.cd.exception.BusinessException;
import cn.cd.mapper.UserMapper;
import cn.cd.query.UserQuery;
import cn.cd.service.UserService;
import cn.cd.util.RedisUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
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
        QueryWrapper<TUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", email).eq("password", password);
        return tUserMapper.selectOne(queryWrapper);

//        TUser tUser = tUserMapper.getByEmailAndPassword(email, password);
//        return tUser;
    }

    @Override
    public TUser loginServiceByPhoneAndPassword(String phone, String password) {
        QueryWrapper<TUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone", phone).eq("password", password);
        return tUserMapper.selectOne(queryWrapper);

//        TUser tUser = tUserMapper.getByPhoneAndPassword(phone, password);
//        return tUser;
    }

    @Override
    public int registerService(String username, String password,
                               String email, String phone) {
        TUser tUser = new TUser();
        tUser.setUsername(username);
        tUser.setPassword(password);
        tUser.setEmail(email);
        tUser.setPhone(phone);
        tUser.setStatus(1);
        return tUserMapper.insert(tUser);
//        return tUserMapper.register(username, password, email, phone);
    }

    @Override
    public int updateStatus(Long id) {
        UpdateWrapper<TUser> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", id);
        if (tUserMapper.getStatus(id) == 1) {
            updateWrapper.set("status", 0);
        } else if (tUserMapper.getStatus(id) == 0) {
            updateWrapper.set("status", 1);
        } else {
            return -1;
        }
        return tUserMapper.update(updateWrapper);
//        return tUserMapper.updateStatus(id, status);
    }

    @Override
    public int updatePersonalInformation(Long id, String username, String email,
                                         String phone, String gender) {
        UpdateWrapper<TUser> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", id);
        updateWrapper.set("username", username);
        updateWrapper.set("email", email);
        updateWrapper.set("phone", phone);
        updateWrapper.set("gender", gender);
        return tUserMapper.update(updateWrapper);

//        return tUserMapper.updatePersonalInformation(id, username, email, phone, gender);
    }

    @Override
    public TUser getByEmail(String email) {
        QueryWrapper<TUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", email);
        return tUserMapper.selectOne(queryWrapper);
//        return tUserMapper.getByEmail(email);
    }

    @Override
    public TUser getByPhone(String phone) {
        QueryWrapper<TUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone", phone);
        return tUserMapper.selectOne(queryWrapper);
//        return tUserMapper.getByPhone(phone);
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

    @Override
    public Page<TUser> pageQuery(UserQuery userQuery) {
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(userQuery.getEmail()), "email", userQuery.getEmail())
                .like(StringUtils.isNotBlank(userQuery.getPhone()), "phone", userQuery.getPhone());
        Page<TUser> page = new Page<>(userQuery.getCurrentPage(), userQuery.getPageSize());
        return this.page(page, queryWrapper);
    }

}




