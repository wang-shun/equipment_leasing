package com.yankuang.equipment.equipment.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yankuang.equipment.common.util.UuidUtils;
import com.yankuang.equipment.equipment.mapper.ElUseItemMapper;
import com.yankuang.equipment.equipment.mapper.ElUseMapper;
import com.yankuang.equipment.equipment.model.ElUse;
import com.yankuang.equipment.equipment.model.ElUseItem;
import com.yankuang.equipment.equipment.service.ElUseService;
import io.terminus.boot.rpc.common.annotation.RpcProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.management.snmp.jvminstr.JvmThreadInstanceEntryImpl;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@RpcProvider
public class ElUseServiceImpl implements ElUseService{
    @Autowired
    ElUseMapper elUseMapper;

    @Autowired
    ElUseItemMapper elUseItemMapper;

    public Boolean create(ElUse elUse){
        if (elUse.getUseBy() == null){
            return false;
        }
        if (elUse.getUsePosition() == null || " ".equals(elUse.getUsePosition())){
            return false;
        }
        elUse.setApproveCode("111111");//TODO 这个暂时写死，等后续有相应的申请编码生成规则在改写
        String code = (elUse.getCode() == null || " ".equals(elUse.getCode()))? UuidUtils.newUuid():elUse.getCode();
        elUse.setCode(code);
        String sorting = (elUse.getSorting() == null || " ".equals(elUse.getSorting()))?"0":elUse.getSorting();
        elUse.setSorting(sorting);
        String status = (elUse.getStatus() == null || " ".equals(elUse.getStatus()))?"1":elUse.getStatus();
        elUse.setStatus(status);
        elUse.setUseAt(new Date());
        elUse.setCreateAt(new Date());
        elUse.setUpdateAt(new Date());
        String useEquipmentType = (elUse.getUseEquipmentType() == null || " ".equals(elUse.getUseEquipmentType()))?"1":elUse.getUseEquipmentType();
        elUse.setUseEquipmentType(useEquipmentType);
        elUse.setCreateBy(1L);//TODO 这个从redis中获取，暂未完成先写死
        String version = (elUse.getVersion() == null || " ".equals(elUse.getVersion()))?"1":elUse.getVersion();
        elUse.setVersion(version);
        elUse.setUpdateBy(1L);
        elUse.setIsUse((byte)1);
        int num = Integer.parseInt(elUseMapper.create(elUse)+"");
        List<ElUseItem> elUseItems = elUse.getElUseItems();
        if (elUseItems == null){
            return false;
        }
        for (ElUseItem elUseItem:elUseItems){
            if (elUseItems == null){
                return false;
            }
            elUseItem.setUseId(elUse.getId());
            if (elUseItem.getEquipmentId() == null){
                return false;
            }
            if (elUseItem.getEquipmentEffect() == null || " ".equals(elUseItem.getEquipmentEffect())){
                return false;
            }
            if (elUseItem.getPlanUseId() == null){
                return false;
            }
            Integer equipmentNum = elUseItem.getEquipmentNum() == null?1:elUseItem.getEquipmentNum();
            String equipmentStatus = (elUseItem.getStatus() == null || " ".equals(elUseItem.getStatus()))?"1":elUseItem.getStatus();
            elUseItem.setStatus(equipmentStatus);
            String equipmentCode = (elUseItem.getCode() == null || " ".equals(elUseItem.getStatus()))?"1": elUseItem.getCode();
            elUseItem.setStatus(equipmentCode);
            elUseItem.setEquipmentPosition(elUse.getUsePosition());
            elUseItem.setEquipmentNum(equipmentNum);
            elUseItem.setUseId(elUse.getId());
            elUseItem.setUseAt(elUse.getUseAt());
            elUseItem.setIsUse((byte)1);
            elUseItemMapper.create(elUseItem);
        }
        return num > 0;
    }

    public Boolean update(ElUse elUse){
        return elUseMapper.update(elUse) >= 0;
    }

    public ElUse select(Long id){
        return elUseMapper.select(id);
    }

    public Boolean delete(Long id){
        return elUseMapper.delete(id) > 0;
    }

    public PageInfo<ElUse> list(Integer page, Integer size, Map elUseMap){
        PageHelper.startPage(page, size);
        elUseMap.put("isUse",(byte)1);
        List<ElUse> elUses = elUseMapper.list(elUseMap);
        PageInfo<ElUse> pageInfo = new PageInfo<ElUse>(elUses);
        return pageInfo;
    }

    public Boolean open(Long id){
        return elUseMapper.open(id) >= 0;
    }
}
