package cn.cd.service;

import cn.cd.domain.TLendrecord;
import cn.cd.query.LendQuery;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import java.util.List;


public interface LendService extends IService<TLendrecord> {
    List<TLendrecord> countByCategory();
    List<TLendrecord> countByName();
    List<TLendrecord> countByUserid();

    void addRecord(Long book_id, Long user_id, String category, String bookname);

    PageInfo<TLendrecord> getByUseridAndSome(LendQuery lendQuery);

    void deleteRecord(Long id);
    void updateRecordStatus(Long id,Integer status);
}
