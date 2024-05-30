package com.ustb.mapper;

import com.github.pagehelper.Page;
import com.ustb.pojo.Setmeal;

import java.util.List;


public interface SetmealMapper {
    void add(Setmeal setmeal);

    Page<Setmeal> findByCondition(String queryString);

    Setmeal findById(Integer id);

    void update(Setmeal setmeal);

    void deleteById(Integer id);

    List<Setmeal> getSetmeal();

    Setmeal findSetmealAndGroupAndItemById(Integer id);

    Setmeal findSetmealById(Integer id);

    String getNameById(Integer setmealId);
}
