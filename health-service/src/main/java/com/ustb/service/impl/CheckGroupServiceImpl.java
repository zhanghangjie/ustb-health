package com.ustb.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ustb.constant.MessageConstant;
import com.ustb.entity.PageResult;
import com.ustb.entity.QueryPageBean;
import com.ustb.entity.Result;
import com.ustb.mapper.CheckGroupAndItemMapper;
import com.ustb.mapper.CheckGroupMapper;
import com.ustb.mapper.SetmealAndCheckGroupMapper;
import com.ustb.pojo.CheckGroup;
import com.ustb.pojo.CheckItem;
import com.ustb.service.CheckGroupService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CheckGroupServiceImpl implements CheckGroupService {

    @Resource
    CheckGroupMapper checkGroupMapper;
    @Resource
    CheckGroupAndItemMapper checkGroupAndItemMapper;
    @Resource
    SetmealAndCheckGroupMapper setmealAndCheckGroupMapper;
    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        Page<CheckGroup> page = checkGroupMapper.findPage(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public Result add(CheckGroup checkGroup, Integer[] itemids) {
        //1.把检查组的基本信息存储到数据库中
        checkGroupMapper.add(checkGroup);
        Integer checkGroupId = checkGroup.getId(); //如果逐渐回填策略生效那么此处可以获取到新增的id
        //2.把检查项和检查组的对应关系存储到数据库中
        for (Integer itemid : itemids) {
            checkGroupAndItemMapper.add(checkGroupId,itemid);
        }
        return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS,null);
    }

    @Override
    public Result findById(Integer id) {
        CheckGroup checkGroup = checkGroupMapper.findById(id);
        return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkGroup);
    }

    @Override
    public Result edit(CheckGroup checkGroup, Integer[] checkitemIds) {
        //1.修改检查组本身的数据
        checkGroupMapper.edit(checkGroup);
        //2.清理和检查项关联关系
        checkGroupAndItemMapper.deleteByGroupId(checkGroup.getId());
        //3.建立和检查项新的关联关系
        for (Integer checkitemId : checkitemIds) {
            //遍历添加新的关联关系
            checkGroupAndItemMapper.add(checkGroup.getId(),checkitemId);
        }
        return new Result(true,MessageConstant.EDIT_CHECKGROUP_SUCCESS,null);
    }

    @Override
    public Result deleteById(Integer id) {
        //判断是否和套餐关联 判断是否和检查项关联
        //1.查询是否和套餐存在关联关系
        Integer count1 = setmealAndCheckGroupMapper.countByCheckGroupId(id);
        //2.查询是否和检查项存在关联关系
        Integer count2 = checkGroupAndItemMapper.countByCheckGroupId(id);
        //3.如果两者都不存在关联关系就可以删除
        if(count1<=0 && count2<=0){
            //可以删除
            checkGroupMapper.deleteById(id);
            return new Result(true,MessageConstant.DELETE_CHECKGROUP_SUCCESS,null);
        }else{
            //不可删除
            return new Result(false,MessageConstant.DELETE_CHECKGROUP_FAIL,null);
        }
    }

    @Override
    public Result findAll() {
        List<CheckGroup> list = null;
        try {
            list = checkGroupMapper.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_CHECKGROUP_FAIL,null);
        }
        return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,list);
    }
}
