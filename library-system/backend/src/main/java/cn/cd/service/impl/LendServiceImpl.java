package cn.cd.service.impl;

import cn.cd.domain.TLendrecord;
import cn.cd.mapper.LendrecordMapper;
import cn.cd.domain.TBook;
import cn.cd.query.LendQuery;
import cn.cd.service.LendService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class LendServiceImpl
        extends ServiceImpl<LendrecordMapper, TLendrecord>
        implements LendService {
    @Resource
    private LendrecordMapper lendrecordMapper;
    @Resource
    private BookServiceImpl bookService;


    // TODO : 获取用户借阅记录
    @Override
    public Integer getStatus(Long user_id) {
        return lendrecordMapper.getStatusSum(user_id);
    }

    @Override
    public List<Map<String, Object>> countByCategory() {
        return lendrecordMapper.countByCategory();
    }

    @Override
    public List<Map<String, Object>> countByBookName() {
        return this.baseMapper.countByBookName();
    }

    @Override
    public List<Map<String, Object>> countByUserIdTop10() {
        return this.baseMapper.countByUserIdTop10();
    }


    @Override
    public void addRecord(Long book_id, Long user_id, String category, String bookname) {
        // 添加借阅记录
        TLendrecord record = new TLendrecord();
        record.setBookId(book_id);
        record.setUserId(user_id);
        record.setCategory(category);
        record.setBookname(bookname);
        lendrecordMapper.insert(record);
        // 修改图书的库存数量
        UpdateWrapper<TBook> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", book_id)
                .setSql("available_quantity = available_quantity - 1");
        bookService.update(updateWrapper);
    }

    @Override
    public Page<TLendrecord> pageQuery(LendQuery lendquery) {
        QueryWrapper<TLendrecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(lendquery.getUser_id() != null,"user_id", lendquery.getUser_id())
                .like(StringUtils.isNotBlank(lendquery.getBookname()),"bookname", lendquery.getBookname())
                .like(StringUtils.isNotBlank(lendquery.getCategory()),"category", lendquery.getCategory())
                .eq(lendquery.getStatus() != null,"status", lendquery.getStatus());
        Page<TLendrecord> page = new Page<>(lendquery.getCurrentPage(), lendquery.getPageSize());
        lendrecordMapper.selectCount(queryWrapper);

        return lendrecordMapper.selectPage(page, queryWrapper);

    }


    @Override
    public void updateRecordStatus(Long id, Integer status) {
        UpdateWrapper<TLendrecord> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", id).set("status", status);
        lendrecordMapper.update(updateWrapper);

//        lendrecordMapper.updateRecordStatus(id, status);
    }
    

    @Override
    public List<TLendrecord> getSoonRecord(Long userId) {
        return lendrecordMapper.getSoonRecord(userId);
    }

    @Override
    public List<TLendrecord> getOverTimeRecord(Long userId) {
        return lendrecordMapper.getOverTimeRecord(userId);
    }


}
