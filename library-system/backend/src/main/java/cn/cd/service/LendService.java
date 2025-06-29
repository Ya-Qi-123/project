package cn.cd.service;

import cn.cd.domain.TLendrecord;
import cn.cd.query.LendQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;


public interface LendService extends IService<TLendrecord> {
    Integer getStatus(Long user_id);


    // 按分类统计借阅次数
    List<Map<String, Object>> countByCategory();
    List<Map<String, Object>> countByBookName();
    List<Map<String, Object>> countByUserIdTop10();


    void addRecord(Long book_id, Long user_id, String category, String bookname);

    Page<TLendrecord> pageQuery(LendQuery lendquery);

    void deleteRecord(Long id);
    void updateRecordStatus(Long id,Integer status);
    TLendrecord getById(Long id);

    Long getBookidById(Long id);

    List<TLendrecord> getSoonRecord(Long userId);
    List<TLendrecord> getOverTimeRecord(Long userId);
}
