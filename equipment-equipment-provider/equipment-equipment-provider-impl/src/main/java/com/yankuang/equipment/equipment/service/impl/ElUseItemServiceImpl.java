package com.yankuang.equipment.equipment.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yankuang.equipment.equipment.mapper.ElUseItemMapper;
import com.yankuang.equipment.equipment.model.ElUseItem;
import com.yankuang.equipment.equipment.service.ElUseItemService;
import io.terminus.boot.rpc.common.annotation.RpcProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RpcProvider
public class ElUseItemServiceImpl implements ElUseItemService{
    @Autowired
    ElUseItemMapper elUseItemMapper;

    public Boolean create(ElUseItem elUseItem){
        return elUseItemMapper.create(elUseItem) > 0 ;
    }

    public Boolean update(ElUseItem elUseItem){
        return elUseItemMapper.update(elUseItem) >= 0;
    }

    public PageInfo<ElUseItem> list(Integer page, Integer size,Map elUseItemMap){
        PageHelper.startPage(page,size);
        List<ElUseItem> elUseItems = elUseItemMapper.list(elUseItemMap);
        PageInfo<ElUseItem> pageInfo = new PageInfo<ElUseItem>(elUseItems);
        return pageInfo;
    }

    public ElUseItem findById(Long itemId){
        return elUseItemMapper.findById(itemId);
    }

    public Boolean delete(Long useId){
        return elUseItemMapper.delete(useId) >= 0;
    }

    public Boolean deleteById(Long itemId){
        return elUseItemMapper.deleteById(itemId) >= 0;
    }
}
