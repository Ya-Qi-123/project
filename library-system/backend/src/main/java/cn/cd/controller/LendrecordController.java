package cn.cd.controller;

import cn.cd.domain.TLendrecord;
import cn.cd.query.LendQuery;
import cn.cd.service.BookService;
import cn.cd.service.LendService;
import cn.cd.service.UserService;
import cn.cd.util.AjaxResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/lend")
public class LendrecordController
{
    @Resource
    private LendService lendService;
    @Resource
    private BookService bookService;
    @Resource
    private UserService userService;


    // 分类别计数
    @GetMapping("/countByCategory")
    public Object countByCategory(){
        // 1. 查询所有分类的借阅次数
        return lendService.countByCategory();
    }

    // 分书计数
    @GetMapping("/countByBookName")
    public Object countByBookName(){
        return lendService.countByBookName();
    }

    // 统计每个用户的借阅次数
    @GetMapping("/countByUseridTop10")
    public Object countByUseridTop10(){
        return lendService.countByUserIdTop10();
    }

    // 增加借阅记录
    @PostMapping("/addRecord")
    public Object addRecord(@RequestParam String isbn,
                            @RequestParam String phone){
        Long book_id = bookService.getByISBN(isbn).getId();
        Long user_id = userService.getByPhone(phone).getId();
        String category = bookService.getById(book_id).getCategory();
        String bookname = bookService.getById(book_id).getName();
        lendService.addRecord(book_id, user_id, category, bookname);
        bookService.updateBookAvailableQuantity(book_id, -1);
        return AjaxResult.me().setMessage("添加成功");
    }

    //    // 可以对借阅记录表进行查询，分页查询+高级查询
//    @PostMapping("/pageQuery")
//    public Page<TLendrecord> pageQuery(@RequestBody LendQuery lendquery, HttpServletRequest request){
//        TUser currentUser = userService.getCurrentUser(request);
//        if (!Objects.equals(currentUser.getId(), lendquery.getUser_id())) {
//            throw new BusinessException("用户权限不足");
//        }
//        QueryWrapper<TLendrecord> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("user_id", lendquery.getUser_id());
//        Page<TLendrecord> page = new Page<>(lendquery.getCurrentPage(), lendquery.getPageSize());
//
////        Page<TLendrecord> list =lendService.pageQuery(lendquery);
//        return lendService.page(page, queryWrapper);
//    }

    // 可以对借阅记录表进行查询，分页查询+高级查询
    @PostMapping("/pageQuery")
    public Page<TLendrecord> pageQuery(@RequestBody LendQuery lendquery){
        if(lendquery == null){
            return Page.of(1, 5);
        }
        return lendService.pageQuery(lendquery);
    }

    // 删除借阅记录
    @DeleteMapping("/deleteRecord")
    public Object deleteRecord(@RequestParam Long id){
        lendService.deleteRecord(id);
        return AjaxResult.me().setMessage("删除成功");
    }

    // 归还图书时，更改借阅记录中的状态
    @PutMapping("/updateRecordStatus")
    public Object updateRecordStatus(@RequestParam Long id,@RequestParam Integer status){
        TLendrecord lendrecord = lendService.getById(id);
        if(lendrecord == null){
            return AjaxResult.fail("该借阅记录不存在！");
        }
        lendService.updateRecordStatus(id, status);
        Long book_id = lendService.getBookidById(id);
        bookService.updateBookAvailableQuantity(book_id, 1);
        return AjaxResult.me().setMessage("修改成功");
    }

    @GetMapping("/Reminder")
    public Object Reminder(@RequestParam Long user_id) {
        List<TLendrecord> OverRecords = lendService.getOverTimeRecord(user_id);
        List<TLendrecord> SoonRecords = lendService.getSoonRecord(user_id);

        if(OverRecords.size() > 0){
            return "你有图书超时了";
        }else if(SoonRecords.size() > 0){
            return "你有图书即将到期";
        }
        return "无到期或超时图书";
    }

}
