package com.ustb.service;

import com.ustb.entity.PageResult;
import com.ustb.entity.QueryPageBean;
import com.ustb.entity.Result;
import com.ustb.pojo.CheckGroup;

public interface CheckGroupService {
    PageResult findPage(QueryPageBean queryPageBean);

    Result add(CheckGroup checkGroup, Integer[] itemids);

    Result findById(Integer id);

    Result edit(CheckGroup checkGroup, Integer[] checkitemIds);

    Result deleteById(Integer id);

    Result findAll();
}
