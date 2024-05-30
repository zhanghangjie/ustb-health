package com.ustb.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ustb.constant.MessageConstant;
import com.ustb.entity.PageResult;
import com.ustb.entity.QueryPageBean;
import com.ustb.entity.Result;
import com.ustb.mapper.SetmealAndCheckGroupMapper;
import com.ustb.mapper.SetmealMapper;
import com.ustb.pojo.Setmeal;
import com.ustb.service.SetmealService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service
public class SetmealServiceImpl implements SetmealService {
    @Resource
    private SetmealMapper setmealMapper;
    @Resource
    private SetmealAndCheckGroupMapper setmealAndCheckGroupMapper;

    @Transactional   //事务  数据库
    @Override
    public Result add(Setmeal setmeal, Integer[] checkgroupIds) {
        //1.添加套餐数据并且进行主键回填
        setmealMapper.add(setmeal);
        Integer setmealId = setmeal.getId();
        //2.添加套餐ID和检查组ID的关联关系
        for (Integer checkgroupId : checkgroupIds) {
            //3.调用mapper层添加数据
            setmealAndCheckGroupMapper.add(setmealId,checkgroupId);
        }
        return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS,null);
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        //分页设置
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        Page<Setmeal> page =  setmealMapper.findByCondition(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public Result findById(Integer id) {
        Setmeal setmeal = setmealMapper.findById(id);
        return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,setmeal);
    }

    @Override
    public Result update(Setmeal setmeal, Integer[] checkgroupIds) {
        //修改套餐本身的信息
        setmealMapper.update(setmeal);
        //清理套餐和检查组之间的关系
        setmealAndCheckGroupMapper.deleteBySetmealId(setmeal.getId());
        //建立和检查组之间新的关系
        for (Integer checkgroupId : checkgroupIds) {
            setmealAndCheckGroupMapper.add(setmeal.getId(),checkgroupId);
        }
        return new Result(true,MessageConstant.EDIT_SETMEAL_SUCCESS,null);
    }


    @Override
    public Result deleteById(Integer id) {
        //没有关联关系就可以删除  有则不能删除
        Integer count = setmealAndCheckGroupMapper.countBySetmealId(id);
        if(count>0){
            return new Result(false,MessageConstant.DELETE_SETMEAL_FAIL,null);
        }else{
            setmealMapper.deleteById(id);
            return new Result(true,MessageConstant.DELETE_SETMEAL_SUCCESS,null);
        }
    }

    @Override
    public Result getSetmeal() {
        //返回查询到的所有套餐
        List<Setmeal> list =  setmealMapper.getSetmeal();
        return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,list);
    }

    @Override
    public Result findSetmealAndGroupAndItemById(Integer id) {
        //返回一个具体的setmeal
        Setmeal setmeal = setmealMapper.findSetmealAndGroupAndItemById(id);
        return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,setmeal);
    }

    @Override
    public Result findSetmealById(Integer id) {
        Setmeal setmeal = setmealMapper.findSetmealById(id);
        return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,setmeal);
    }
}
