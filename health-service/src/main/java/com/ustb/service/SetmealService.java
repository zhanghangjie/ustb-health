package com.ustb.service;

import com.ustb.entity.PageResult;
import com.ustb.entity.QueryPageBean;
import com.ustb.entity.Result;
import com.ustb.pojo.Setmeal;

public interface SetmealService {
    Result add(Setmeal setmeal, Integer[] checkgroupIds);

    PageResult findPage(QueryPageBean queryPageBean);

    Result findById(Integer id);

    Result update(Setmeal setmeal, Integer[] checkgroupIds);

    Result deleteById(Integer id);

    Result getSetmeal();

    Result findSetmealAndGroupAndItemById(Integer id);

    Result findSetmealById(Integer id);
}
