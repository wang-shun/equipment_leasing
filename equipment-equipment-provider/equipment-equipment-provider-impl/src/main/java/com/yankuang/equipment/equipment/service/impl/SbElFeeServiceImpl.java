package com.yankuang.equipment.equipment.service.impl;

import com.yankuang.equipment.common.util.DateUtils;
import com.yankuang.equipment.equipment.mapper.SbElFeeConfigMapper;
import com.yankuang.equipment.equipment.mapper.SbEquipmentTMapper;
import com.yankuang.equipment.equipment.mapper.SbEquipmentZMapper;
import com.yankuang.equipment.equipment.model.SbElFeeConfig;
import com.yankuang.equipment.equipment.model.SbEquipmentT;
import com.yankuang.equipment.equipment.model.SbEquipmentZ;
import com.yankuang.equipment.equipment.service.SbElFeeService;
import io.terminus.boot.rpc.common.annotation.RpcProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RpcProvider
public class SbElFeeServiceImpl implements SbElFeeService {

    @Autowired
    SbElFeeConfigMapper sbElFeeConfigMapper;

    @Autowired
    SbEquipmentTMapper sbEquipmentTMapper;

    @Autowired
    SbEquipmentZMapper sbEquipmentZMapper;

    public Double CalDayElFeeA1ByEquipmentTId(Long equipmentId) {
        double dayElFeeA1 = 0;
        SbEquipmentT sbEquipmentT = sbEquipmentTMapper.findById(equipmentId);
        SbElFeeConfig sbElFeeConfig = sbElFeeConfigMapper.findByYear(DateUtils.getCurrentYear());

        //如果设备信息不存在或当年的费用配置信息不存在则返回null
        if(sbEquipmentT==null || sbElFeeConfig==null){
            return null;
        }

        //先判断设备属于一类设备or二类设备
        if(sbEquipmentT.getBuyPrice() != null && sbEquipmentT.getBuyPrice().compareTo(BigDecimal.valueOf(300000)) > 0){
            double ElFee = (sbEquipmentT.getBuyPrice().doubleValue()/sbEquipmentT.getCostYears().doubleValue()
                        + sbEquipmentT.getBuyPrice().doubleValue()*sbElFeeConfig.getOneIncreRate()*sbElFeeConfig.getOneElRate())*sbElFeeConfig.getOneUseRate()
                        + sbEquipmentT.getBuyPrice().doubleValue()*sbElFeeConfig.getOneRepairRate()
                        + sbEquipmentT.getBuyPrice().doubleValue()*sbElFeeConfig.getOneManageRate();
            dayElFeeA1 = new BigDecimal(ElFee/365).setScale(2, RoundingMode.UP).doubleValue();
        }else{
            double ElFee = sbEquipmentT.getBuyPrice().doubleValue()/sbEquipmentT.getCostYears().doubleValue()
                        + sbEquipmentT.getBuyPrice().doubleValue()*sbElFeeConfig.getOneRepairRate()
                        + sbEquipmentT.getBuyPrice().doubleValue()*sbElFeeConfig.getOneManageRate();
            dayElFeeA1 = new BigDecimal(ElFee/365).setScale(2, RoundingMode.UP).doubleValue();
        }

        return dayElFeeA1;
    }

    public Double CalDayElFeeA1ByEquipmentZId(Long equipmentId) {
        double dayElFeeA1 = 0;
        SbEquipmentZ sbEquipmentZ = sbEquipmentZMapper.findById(equipmentId);
        SbElFeeConfig sbElFeeConfig = sbElFeeConfigMapper.findByYear(DateUtils.getCurrentYear());

        //如果设备信息不存在或当年的费用配置信息不存在则返回null
        if(sbEquipmentZ==null || sbElFeeConfig==null){
            return null;
        }

        double ElFee = (sbEquipmentZ.getBuyPrice().doubleValue()/sbEquipmentZ.getCostYears().doubleValue()
                + sbEquipmentZ.getBuyPrice().doubleValue()*sbElFeeConfig.getOneIncreRate()*sbElFeeConfig.getOneElRate())*sbElFeeConfig.getOneUseRate()
                + sbEquipmentZ.getBuyPrice().doubleValue()*sbElFeeConfig.getOneRepairRate()
                + sbEquipmentZ.getBuyPrice().doubleValue()*sbElFeeConfig.getOneManageRate();
        dayElFeeA1 = new BigDecimal(ElFee/365).setScale(2, RoundingMode.UP).doubleValue();

        return dayElFeeA1;
    }

}
