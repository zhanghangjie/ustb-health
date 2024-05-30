package com.ustb.controller;

import com.ustb.entity.Result;
import com.ustb.service.SetmealAndGroupService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/setmealAndGroup/")
public class SetmealAndGroupController {

    @Resource
    private SetmealAndGroupService setmealAndGroupService;

    @RequestMapping("findBySetmealId")
    public Result findBySetmealId(Integer setmealId){
        return setmealAndGroupService.findBySetmealId(setmealId);
    }
}
