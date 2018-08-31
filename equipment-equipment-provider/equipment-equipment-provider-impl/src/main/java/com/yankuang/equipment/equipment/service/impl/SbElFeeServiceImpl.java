package com.yankuang.equipment.equipment.service.impl;

import com.yankuang.equipment.common.util.DateUtils;
import com.yankuang.equipment.equipment.mapper.ElUseItemMapper;
import com.yankuang.equipment.equipment.mapper.SbElFeeConfigMapper;
import com.yankuang.equipment.equipment.mapper.SbEquipmentTMapper;
import com.yankuang.equipment.equipment.mapper.SbEquipmentZMapper;
import com.yankuang.equipment.equipment.model.ElUseItem;
import com.yankuang.equipment.equipment.model.SbElFeeConfig;
import com.yankuang.equipment.equipment.model.SbEquipmentT;
import com.yankuang.equipment.equipment.model.SbEquipmentZ;
import com.yankuang.equipment.equipment.service.SbElFeeService;
import io.terminus.boot.rpc.common.annotation.RpcProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RpcProvider
public class SbElFeeServiceImpl implements SbElFeeService {

    @Autowired
    SbElFeeConfigMapper sbElFeeConfigMapper;

    @Autowired
    SbEquipmentTMapper sbEquipmentTMapper;

    @Autowired
    SbEquipmentZMapper sbEquipmentZMapper;

    @Autowired
    ElUseItemMapper elUseItemMapper;

    public Double CalDayElFeeA1ByEquipmentTId(Long equipmentId) {
        double dayElFeeA1 = 0;
        SbEquipmentT sbEquipmentT = sbEquipmentTMapper.findById(equipmentId);
        SbElFeeConfig sbElFeeConfig = sbElFeeConfigMapper.findByYear(DateUtils.getCurrentYear());

        //如果设备信息不存在或当年的费用配置信息不存在则返回null
        if(sbEquipmentT==null || sbElFeeConfig==null){
            return null;
        }

        //如果设备存在单价则基本租赁费按单价计算
        if(sbEquipmentT!=null && sbEquipmentT.getDayLeaseFee()!=null){
            return sbEquipmentT.getDayLeaseFee();
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
                        + sbEquipmentT.getBuyPrice().doubleValue()*sbElFeeConfig.getTwoRepairRate()
                        + sbEquipmentT.getBuyPrice().doubleValue()*sbElFeeConfig.getTwoManageRate();
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

        //如果设备存在单价则基本租赁费按单价计算
        if(sbEquipmentZ!=null && sbEquipmentZ.getDayLeaseFee()!=null){
            return sbEquipmentZ.getDayLeaseFee();
        }

        double ElFee = (sbEquipmentZ.getBuyPrice().doubleValue()/sbEquipmentZ.getCostYears().doubleValue()
                + sbEquipmentZ.getBuyPrice().doubleValue()*sbElFeeConfig.getOneIncreRate()*sbElFeeConfig.getOneElRate())*sbElFeeConfig.getOneUseRate()
                + sbEquipmentZ.getBuyPrice().doubleValue()*sbElFeeConfig.getOneRepairRate()
                + sbEquipmentZ.getBuyPrice().doubleValue()*sbElFeeConfig.getOneManageRate();
        dayElFeeA1 = new BigDecimal(ElFee/365).setScale(2, RoundingMode.UP).doubleValue();

        return dayElFeeA1;
    }

    public Long CalEquipmentElDays(Long useId,Long backId, Long equipmentId, Date startDate, Date endDate) {
        long days = 0;Date sDate = startDate;Date eDate = endDate;

        Map map = new HashMap();
        map.put("useId",useId);
        map.put("equipmentId",equipmentId);
        map.put("isUse",1);
        ElUseItem elUseItem = elUseItemMapper.findByCondition(map);
        //如果领用单没有此设备领用信息返回天数0
        if(elUseItem==null){
            return days;
        }
        if(elUseItem!=null && elUseItem.getUseAt().after(startDate)){
            sDate = elUseItem.getUseAt();
        }

        if(backId!=null){
            Map map2 = new HashMap();
            map2.put("useId",backId);
            map2.put("equipmentId",equipmentId);
            map2.put("isUse",2);
            ElUseItem elUseItem2 = elUseItemMapper.findByCondition(map2);
            if(elUseItem2!=null && elUseItem2.getUseAt().before(endDate)){
                eDate = elUseItem2.getUseAt();
            }
        }

        days = DateUtils.getDaysInTwoDate(sDate,eDate) < 0 ? 0:DateUtils.getDaysInTwoDate(sDate,eDate);

        return days;
    }

    public Double CalDayElFeeA3T_rate(Long useId, Long equipmentId) {
        double dayElFeeA3 = 1;
        SbEquipmentT sbEquipmentT = sbEquipmentTMapper.findById(equipmentId);
        Map map = new HashMap();
        map.put("useId",useId);
        map.put("equipmentId",equipmentId);
        map.put("isUse",1);
        ElUseItem elUseItem = elUseItemMapper.findByCondition(map);
        //如果领用单没有此设备领用信息返回新度系数调节费0.00
        if(elUseItem==null){
            return 0.00;
        }

        if(sbEquipmentT==null || elUseItem==null || sbEquipmentT.getUseDate()==null){
            return dayElFeeA3;
        }

        if(DateUtils.getDaysInTwoDate(sbEquipmentT.getUseDate(),elUseItem.getUseAt()) < 12*30){
            //新购设备
            if("0001".equals(sbEquipmentT.getStateCode())){
                dayElFeeA3 = 1*(1+0.3);
            }
            //再制造设备
            if("0002".equals(sbEquipmentT.getStateCode())){
                dayElFeeA3 = 1*(1+0.15);
            }
        }
        if(DateUtils.getDaysInTwoDate(sbEquipmentT.getUseDate(),elUseItem.getUseAt()) > (sbEquipmentT.getCostYears()-1)*365){
            //新购设备
            if("0001".equals(sbEquipmentT.getStateCode())){
                dayElFeeA3 = 1*(1-0.3);
            }
            //再制造设备
            if("0002".equals(sbEquipmentT.getStateCode())){
                dayElFeeA3 = 1*(1-0.15);
            }
        }

        return dayElFeeA3;
    }

    public Double CalDayElFeeA3Z_rate(Long useId, Long equipmentId) {
        double dayElFeeA3 = 1;
        SbEquipmentZ sbEquipmentZ = sbEquipmentZMapper.findById(equipmentId);
        Map map = new HashMap();
        map.put("useId",useId);
        map.put("equipmentId",equipmentId);
        map.put("isUse",1);
        ElUseItem elUseItem = elUseItemMapper.findByCondition(map);

        if(sbEquipmentZ==null || elUseItem==null || sbEquipmentZ.getUseDate()==null){
            return dayElFeeA3;
        }

        if(DateUtils.getDaysInTwoDate(sbEquipmentZ.getUseDate(),elUseItem.getUseAt()) < 12*30){
            //新购设备
            if("0001".equals(sbEquipmentZ.getStateCode())){
                dayElFeeA3 = 1*(1+0.3);
            }
            //再制造设备
            if("0002".equals(sbEquipmentZ.getStateCode())){
                dayElFeeA3 = 1*(1+0.15);
            }
        }
        if(DateUtils.getDaysInTwoDate(sbEquipmentZ.getUseDate(),elUseItem.getUseAt()) > (sbEquipmentZ.getCostYears()-1)*365){
            //新购设备
            if("0001".equals(sbEquipmentZ.getStateCode())){
                dayElFeeA3 = 1*(1-0.3);
            }
            //再制造设备
            if("0002".equals(sbEquipmentZ.getStateCode())){
                dayElFeeA3 = 1*(1-0.15);
            }
        }

        return dayElFeeA3;
    }

}
