package com.ustb.controller;

import com.ustb.constant.MessageConstant;
import com.ustb.entity.PageResult;
import com.ustb.entity.QueryPageBean;
import com.ustb.entity.Result;
import com.ustb.pojo.Setmeal;
import com.ustb.service.SetmealService;
import com.ustb.util.QiNiuUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/setmeal/")
public class SetmealController {

    @Resource
    private SetmealService setmealService;

    @RequestMapping("upload")
    public Result upload(@RequestParam("imgFile") MultipartFile imgFile){
        String originalFilename = imgFile.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileName =  UUID.randomUUID().toString()+suffix;
        try {
            QiNiuUtils.upload2QiNiu(imgFile.getBytes(),fileName);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL,null);
        }
        return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS,fileName);
    }

    @RequestMapping("add")
    public Result add(@RequestBody Setmeal setmeal,Integer[] checkgroupIds){
        return setmealService.add(setmeal,checkgroupIds);
    }

    @RequestMapping("findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        return setmealService.findPage(queryPageBean);
    }

    @RequestMapping("findById")
    public Result findById(Integer id){
        return setmealService.findById(id);
    }

    @RequestMapping("update")
    public Result update(@RequestBody Setmeal setmeal,Integer[] checkgroupIds){
        return setmealService.update(setmeal,checkgroupIds);
    }
    @RequestMapping("deleteById")
    public Result deleteById(Integer id){
        return setmealService.deleteById(id);
    }
}
