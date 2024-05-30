package com.ustb.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ustb.constant.MessageConstant;
import com.ustb.entity.PageResult;
import com.ustb.entity.QueryPageBean;
import com.ustb.entity.Result;
import com.ustb.mapper.CheckItemMapper;
import com.ustb.pojo.CheckItem;
import com.ustb.service.CheckItemService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CheckItemServiceImpl implements CheckItemService {
    @Resource
    CheckItemMapper checkItemMapper;
    @Override
    public Result add(CheckItem checkItem) {
        try {
            //调用mapper层 完成把页面数据的检查项信息插入到数据库中
            checkItemMapper.add(checkItem);
        }catch (Exception e){
            //24行的代码出错了
            return new Result(false,MessageConstant.ADD_CHECKITEM_FAIL ,null);
        }
        return new Result(true,MessageConstant.ADD_CHECKITEM_SUCCESS,null);
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        //使用分页插件
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        Page<CheckItem> page = checkItemMapper.findPage(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public Result update(CheckItem checkItem) {
        try {
            checkItemMapper.updateById(checkItem);
        }catch (Exception e){
            System.out.println(e);
            return new Result(false,MessageConstant.EDIT_CHECKITEM_FAIL ,null);
        }
        return new Result(true,MessageConstant.EDIT_CHECKITEM_SUCCESS,null);
    }

    @Override
    public Result delete(Integer id) {
        //业务说明：如果当前被删除的检查项存在被依赖的情况则提示用户无法删除   否则正常删除
        Long count = checkItemMapper.countCheckItemForCheckGroup(id);
        if(count>0){
            //当前插件项被检查组所依赖 无法删除
            return new Result(false,MessageConstant.DELETE_CHECKITEM_FAIL2 ,null);
        }else{
            //没有被依赖  可以删除
            try {
                checkItemMapper.deleteById(id);
            }catch (Exception e){
                return new Result(false,MessageConstant.DELETE_CHECKITEM_FAIL ,null);
            }
        }
        return new Result(true,MessageConstant.DELETE_CHECKITEM_SUCCESS ,null);
    }

    @Override
    public Result findAll() {
        List<CheckItem> list =  checkItemMapper.findAll();
        return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS ,list);
    }
}
