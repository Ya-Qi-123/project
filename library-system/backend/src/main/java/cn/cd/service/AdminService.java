package cn.cd.service;

import cn.cd.domain.TAdmin;
import com.baomidou.mybatisplus.extension.service.IService;

public interface AdminService extends IService<TAdmin> {
    TAdmin selectByEmailAndPassword(String email, String password);
}