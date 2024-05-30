package com.ustb.service;

import com.ustb.entity.PageResult;
import com.ustb.entity.QueryPageBean;
import com.ustb.entity.Result;
import com.ustb.pojo.CheckItem;

public interface CheckItemService {
    Result add(CheckItem checkItem);

    PageResult findPage(QueryPageBean queryPageBean);

    Result update(CheckItem checkItem);

    Result delete(Integer id);

    Result findAll();
}
