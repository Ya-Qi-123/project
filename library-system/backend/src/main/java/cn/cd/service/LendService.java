package cn.cd.service;

import cn.cd.domain.TLendrecord;

import java.util.List;

public interface LendService {
    List<TLendrecord> countByCategory();
}
