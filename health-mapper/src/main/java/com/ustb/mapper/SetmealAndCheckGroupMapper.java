package com.ustb.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface SetmealAndCheckGroupMapper {
    Integer countByCheckGroupId(Integer id);

    void add(@Param("setmealId") Integer setmealId, @Param("checkgroupId")Integer checkgroupId);

    List<Integer> findBySetmealId(Integer setmealId);

    void deleteBySetmealId(Integer id);

    Integer countBySetmealId(Integer id);
}
