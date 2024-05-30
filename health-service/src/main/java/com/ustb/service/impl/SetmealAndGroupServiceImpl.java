package com.ustb.service.impl;

import com.ustb.constant.MessageConstant;
import com.ustb.entity.Result;
import com.ustb.mapper.SetmealAndCheckGroupMapper;
import com.ustb.service.SetmealAndGroupService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SetmealAndGroupServiceImpl implements SetmealAndGroupService {
    @Resource
    private SetmealAndCheckGroupMapper setmealAndCheckGroupMapper;

    @Override
    public Result findBySetmealId(Integer setmealId) {
        List<Integer> list = null;
        try {
            list = setmealAndCheckGroupMapper.findBySetmealId(setmealId);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKGROUP_FAIL,null);
        }
        return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS,list);
    }
}
