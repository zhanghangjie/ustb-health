package com.ustb.mapper;
import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.Page;
import com.ustb.pojo.CheckItem;

import java.util.List;


public interface CheckItemMapper {

    void add(CheckItem checkItem);

    Page<CheckItem> findPage(String queryString);

    void updateById(CheckItem checkItem);

    Long countCheckItemForCheckGroup(Integer id);

    void deleteById(Integer id);

    List<CheckItem> findAll();

    List<CheckItem> findByGroupId(Integer checkGroupId);
}