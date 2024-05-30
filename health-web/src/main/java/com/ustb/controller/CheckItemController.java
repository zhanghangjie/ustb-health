package com.ustb.controller;

import com.ustb.entity.PageResult;
import com.ustb.entity.QueryPageBean;
import com.ustb.entity.Result;
import com.ustb.pojo.CheckItem;
import com.ustb.service.CheckItemService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

//由于本项目前后端传递的数据都是Json数据 所以使用@RestController
@RestController
@RequestMapping("/checkitem")   //表示当前类都是接收检查项操作的控制层
public class CheckItemController {

    @Resource
    CheckItemService checkItemService;

    @RequestMapping("/add")
    public Result add(@RequestBody CheckItem checkItem){
        return checkItemService.add(checkItem);
    }
    @RequestMapping("/findpage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        return checkItemService.findPage(queryPageBean);
    }

    @RequestMapping("/update")
    public Result update(@RequestBody CheckItem checkItem){
        return checkItemService.update(checkItem);
    }

    @RequestMapping("/delete")
    public Result delete(Integer id){
        return checkItemService.delete(id);
    }

    //查询所有的检查项信息
    @RequestMapping("/findAll")
    public Result findAll(){
        return checkItemService.findAll();
    }

}
