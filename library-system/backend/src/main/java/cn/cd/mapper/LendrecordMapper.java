package cn.cd.mapper;

import cn.cd.domain.TLendrecord;

import java.util.List;

public interface LendrecordMapper {
    void add(TLendrecord lendrecord);
    void update(TLendrecord lendrecord);
    void delete(Long id);

    TLendrecord getById(Long id);
    TLendrecord getByBookid(String bookid);
    TLendrecord getByUserid(String userid);

    // 给首页可视化使用，显示每个类的图书被借阅总次数
    List<TLendrecord> countByCategory();

}
