package cn.cd.controller;

import cn.cd.domain.TLendrecord;
import cn.cd.service.LendService;
import cn.cd.util.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/book")
public class LendrecordController {
    @Autowired
    private LendService lendService;

    @GetMapping("/HomePage")
    public Object countByCategory(){
        List<TLendrecord> log = lendService.countByCategory();
        if(log != null)
            return log;
        else
            return AjaxResult.me().setMessage("查询失败");
    }
}
