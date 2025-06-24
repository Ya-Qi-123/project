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

    PageInfo<TLendrecord> pageQuery(LendQuery lendQuery);

    void deleteRecord(Long id);
    void updateRecordStatus(Long id,Integer status);

}
