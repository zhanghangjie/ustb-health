package com.ustb.controller;

import com.ustb.entity.PageResult;
import com.ustb.entity.QueryPageBean;
import com.ustb.entity.Result;
import com.ustb.pojo.CheckGroup;
import com.ustb.service.CheckGroupService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/checkgroup")
public class CheckGroupController {

    @Resource
    CheckGroupService checkGroupService;
    @RequestMapping("/findpage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        //调用service层，service层调用mapper层 mapper层查询数据库
        return  checkGroupService.findPage(queryPageBean);
    }

    @RequestMapping("/add")
    public Result add(@RequestBody CheckGroup checkGroup, Integer [] itemids){
        return checkGroupService.add(checkGroup,itemids);
    }

    @RequestMapping("findbyid")
    public Result findById(Integer id){
        return checkGroupService.findById(id);
    }

    @RequestMapping("edit")
    public Result edit(@RequestBody CheckGroup checkGroup,Integer [] checkitemIds){
        return checkGroupService.edit(checkGroup,checkitemIds);
    }
    @RequestMapping("deleteById")
    public Result deleteById(Integer id){
        return checkGroupService.deleteById(id);
    }

    @RequestMapping("findAll")
    public Result findAll(){
        return checkGroupService.findAll();
    }
}