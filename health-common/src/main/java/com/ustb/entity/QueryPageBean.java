package com.ustb.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 封装查询条件
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryPageBean{
    private Integer currentPage;//页码
    private Integer pageSize;//每页记录数
    private String queryString;//查询条件

}