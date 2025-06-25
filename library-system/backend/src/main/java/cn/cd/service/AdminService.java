package cn.cd.service;

import cn.cd.domain.TAdmin;

public interface AdminService {
    TAdmin selectByEmailAndPassword(String email, String password);
}