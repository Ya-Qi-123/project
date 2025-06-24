package cn.cd.mapper;

import cn.cd.domain.TLendrecord;
import cn.cd.query.LendQuery;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.pagehelper.PageInfo;


import java.util.List;

public interface LendrecordMapper
    extends BaseMapper<TLendrecord> {
    void deleteRecord(Long id);
    void updateRecordStatus(Long id, Integer status);

    // 给首页可视化使用，显示每个类的图书被借阅总次数
    List<TLendrecord> countByCategory();
    List<TLendrecord> countByName();
    List<TLendrecord> countByUserid();

    void addRecord(Long book_id, Long user_id, String category, String bookname);

    // 查看当前用户的所有记录
    List<TLendrecord> pageQuery(LendQuery lendquery);

    TLendrecord getById( Long id);

    Integer getStatusSum(Long user_id);

}
