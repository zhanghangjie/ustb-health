package com.ustb.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 体检套餐
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Setmeal{
    private Integer id;
    private String name;
    private String code;
    private String helpCode;
    private String sex;//套餐适用性别：0不限 1男 2女
    private String age;//套餐适用年龄
    private Float price;//套餐价格
    private String remark;
    private String attention;
    private String img;//套餐对应图片存储路径
    private List<CheckGroup> checkGroups;//体检套餐对应的检查组，多对多关系
}
