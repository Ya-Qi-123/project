package cn.cd.controller;

import cn.cd.domain.TLendrecord;
import cn.cd.query.LendQuery;
import cn.cd.service.LendService;
import cn.cd.util.AjaxResult;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lend")
public class LendrecordController
{
    @Autowired
    private LendService lendService;

    // 分类别计数
    @GetMapping("/countByCategory")
    public Object countByCategory(){
        List<TLendrecord> countByCategory = lendService.countByCategory();
        return countByCategory;
    }

    // 分书计数
    @GetMapping("/countByName")
    public Object countByName(){
        List<TLendrecord> countByName = lendService.countByName();
        return countByName;
    }

    // 统计每个用户的借阅次数
    @GetMapping("/countByUserid")
    public Object countByUserid(){
        List<TLendrecord> countByUserid = lendService.countByUserid();
        return countByUserid;
    }

    // 增加借阅记录
    @PostMapping("/addRecord")
    public Object addRecord(@RequestParam Long book_id, @RequestParam Long user_id,
                            @RequestParam String category, @RequestParam String bookname){
        lendService.addRecord(book_id, user_id, category, bookname);
        return AjaxResult.me().setMessage("添加成功");
    }

    // 可以对借阅记录表进行查询，分页查询+高级查询
    @PostMapping("/pageQuery")
    public PageInfo<TLendrecord> pageQuery(@RequestBody LendQuery lendquery){
        PageInfo<TLendrecord> list =lendService.pageQuery(lendquery);
        return list;
    }

    // 删除借阅记录
    @DeleteMapping("/deleteRecord")
    public Object deleteRecord(@RequestParam Long id){
        lendService.deleteRecord(id);
        return AjaxResult.me().setMessage("删除成功");
    }

    // 更改借阅记录中的状态
    @PutMapping("/updateRecordStatus")
    public Object updateRecordStatus(@RequestParam Long id,@RequestParam Integer status){
        lendService.updateRecordStatus(id, status);
        return AjaxResult.me().setMessage("修改成功");
    }

}
