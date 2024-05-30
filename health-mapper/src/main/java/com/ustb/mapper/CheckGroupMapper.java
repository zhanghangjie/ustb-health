package com.ustb.mapper;

import com.github.pagehelper.Page;
import com.ustb.pojo.CheckGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface CheckGroupMapper {
    Page<CheckGroup> findPage(String queryString);

    void add(CheckGroup checkGroup);

    CheckGroup findById(@Param("checkGroupId") Integer id);

    void edit(CheckGroup checkGroup);

    void deleteById(Integer id);

    List<CheckGroup> findAll();

    List<CheckGroup> findBySetmealId(Integer setmealId);
}
