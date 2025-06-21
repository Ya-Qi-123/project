package cn.cd.service;

import cn.cd.domain.TAdmin;

public interface TAdminService {
    TAdmin getByAdminnameAndPassword(String adminname, String password);
}