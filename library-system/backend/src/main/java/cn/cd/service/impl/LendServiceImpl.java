package cn.cd.service.impl;

import cn.cd.domain.TLendrecord;
import cn.cd.mapper.LendrecordMapper;
import cn.cd.query.LendQuery;
import cn.cd.service.LendService;
import cn.cd.util.AjaxResult;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LendServiceImpl
        extends ServiceImpl<LendrecordMapper, TLendrecord>
        implements LendService {
    @Autowired
    private LendrecordMapper lendrecordMapper;


    @Override
    public Integer getStatus(Long user_id) {
        return lendrecordMapper.getStatus(user_id);
    }

    @Override
    public List<TLendrecord> countByCategory() {
        return lendrecordMapper.countByCategory();
    }
    @Override
    public List<TLendrecord> countByName() {
        return lendrecordMapper.countByName();
    }
    @Override
    public List<TLendrecord> countByUserid() {
        return lendrecordMapper.countByUserid();
    }

    @Override
    public void addRecord(Long book_id, Long user_id, String category, String bookname) {
        lendrecordMapper.addRecord(book_id, user_id, category, bookname);
    }

    @Override
    public PageInfo<TLendrecord> getByUseridAndSome(LendQuery lendquery) {
        PageHelper.startPage(lendquery.getCurrentPage(),lendquery.getPageSize());
        List<TLendrecord> list =  lendrecordMapper.getByUseridAndSome(lendquery);
        return new PageInfo<>(list);
    }

    @Override
    public void deleteRecord(Long id) {
        lendrecordMapper.deleteRecord(id);
    }
    @Override
    public void updateRecordStatus(Long id, Integer status) {
        lendrecordMapper.updateRecordStatus(id, status);
    }

}
