package com.ustb.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分页结果封装对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResult{
    private Long total;//总记录数
    private List rows;//当前页结果
}
