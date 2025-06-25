package cn.cd.service;

import cn.cd.domain.TLendrecord;
import cn.cd.query.LendQuery;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import java.util.List;


public interface LendService extends IService<TLendrecord> {
    Integer getStatus(Long user_id);

    List<TLendrecord> countByCategory();
    List<TLendrecord> countByName();
    List<TLendrecord> countByUserid();

    void addRecord(Long book_id, Long user_id, String category, String bookname);

    PageInfo<TLendrecord> pageQuery(LendQuery lendquery);

    void deleteRecord(Long id);
    void updateRecordStatus(Long id,Integer status);
    TLendrecord getById(Long id);

    Long getBookidById(Long id);

    List<TLendrecord> getSoonRecord(Long userId);
    List<TLendrecord> getOverTimeRecord(Long userId);
}
