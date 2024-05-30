package com.ustb.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface CheckGroupAndItemMapper {
    void add(@Param(value = "checkGroupId") Integer checkGroupId,@Param(value = "checkItemId") Integer checkItemId);

    List<Integer> findByCheckGroupId(@Param(value = "checkGroupId") Integer id);

    void deleteByGroupId(Integer id);

    Integer countByCheckGroupId(Integer id);
}
