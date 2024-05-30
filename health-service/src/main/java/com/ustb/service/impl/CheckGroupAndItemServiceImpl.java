package com.ustb.service.impl;

import com.ustb.constant.MessageConstant;
import com.ustb.entity.Result;
import com.ustb.mapper.CheckGroupAndItemMapper;
import com.ustb.service.CheckGroupAndItemService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CheckGroupAndItemServiceImpl implements CheckGroupAndItemService {
    @Resource
    private CheckGroupAndItemMapper checkGroupAndItemMapper;

    @Override
    public Result findByCheckGroupId(Integer id) {
        //根据检查组的id查询对用检查项的ids  在service层里调用mapper层的方法
        List<Integer> list = checkGroupAndItemMapper.findByCheckGroupId(id);
        return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS,list);
    }
}
