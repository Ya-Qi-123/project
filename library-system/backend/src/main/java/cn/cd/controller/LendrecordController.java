package cn.cd.controller;

import cn.cd.domain.TLendrecord;
import cn.cd.service.LendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class LendrecordController {
    @Autowired
    private LendService lendService;

    @GetMapping("/HomePage")
    public Object countByCategory(){
        return lendService.countByCategory();
    }
}
