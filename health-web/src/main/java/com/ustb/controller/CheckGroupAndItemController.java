package com.ustb.controller;

import com.ustb.entity.Result;
import com.ustb.service.CheckGroupAndItemService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/checkgroupandechekitem/")
public class CheckGroupAndItemController {

    @Resource
    private CheckGroupAndItemService checkGroupAndItemService;

    @RequestMapping("findbycheckgroupid")
    public Result findByCheckGroupId(Integer id){
        return checkGroupAndItemService.findByCheckGroupId(id);
    }

}
