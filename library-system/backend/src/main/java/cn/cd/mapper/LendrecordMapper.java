package cn.cd.mapper;

import cn.cd.domain.TLendrecord;
import cn.cd.query.LendQuery;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.lettuce.core.dynamic.annotation.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

import java.util.List;
import java.util.Map;

public interface LendrecordMapper extends BaseMapper<TLendrecord> {
    void deleteRecord(Long id);
    void updateRecordStatus(Long id, Integer status);

    // 按分类统计借阅次数（使用 MyBatis-Plus 内置方法）
    default List<Map<String, Object>> countByCategory() {
        QueryWrapper<TLendrecord> wrapper = new QueryWrapper<>();
        wrapper.select("category", "COUNT(*) as count")  // 选择统计字段
                .groupBy("category");  // 按分类分组
        return this.selectMaps(wrapper);  // 返回Map列表
    }

    // 按书名统计借阅次数
    default List<Map<String, Object>> countByBookName() {
        QueryWrapper<TLendrecord> wrapper = new QueryWrapper<>();
        wrapper.select("bookname", "COUNT(*) as count")  // 统计每个书名的借阅次数
                .groupBy("bookname")                      // 按书名分组
                .orderByDesc("count");                    // 按次数降序排列
        return this.selectMaps(wrapper);
    }

    // 按用户ID统计借阅次数（返回前10名）
    default List<Map<String, Object>> countByUserIdTop10() {
        QueryWrapper<TLendrecord> wrapper = new QueryWrapper<>();
        wrapper.select("user_id", "COUNT(*) as borrow_count")  // 统计每个用户的借阅次数
                .groupBy("user_id")                             // 按用户ID分组
                .orderByDesc("borrow_count")                    // 按次数降序排列
                .last("LIMIT 10");                              // 取前10条
        return this.selectMaps(wrapper);
    }

    void addRecord(Long book_id, Long user_id, String category, String bookname);

    // 查看当前用户的所有记录
    List<TLendrecord> pageQuery(LendQuery lendquery);

    TLendrecord getById( Long id);

    Integer getStatusSum(Long user_id);

    Long getBookidById(Long id);

    List<TLendrecord> getOverTimeRecord(Long userId);
    List<TLendrecord> getSoonRecord(Long userId);


}
