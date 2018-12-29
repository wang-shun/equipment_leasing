package com.yankuang.equipment.web.restful;

import com.yankuang.equipment.common.util.Constants;
import com.yankuang.equipment.common.util.DateUtils;
import com.yankuang.equipment.common.util.ResponseMeta;
import com.yankuang.equipment.equipment.model.SbElFeeConfig;
import com.yankuang.equipment.equipment.model.SbEquipmentT;
import com.yankuang.equipment.equipment.service.SbElFeeConfigService;
import com.yankuang.equipment.equipment.service.SbElFeeService;
import com.yankuang.equipment.equipment.service.SbEquipmentTService;
import io.terminus.boot.rpc.common.annotation.RpcConsumer;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping(value = "/v1/sbelfee")
public class SbElFeeShowTestController {

    @RpcConsumer
    SbElFeeService sbElFeeService;

    @RpcConsumer
    SbEquipmentTService sbEquipmentTService;

    @RpcConsumer
    SbElFeeConfigService sbElFeeConfigService;

    @RequestMapping(value = "/show",method = RequestMethod.GET)
    public ResponseMeta sbElFeeShow(Long equipmentId, Long useId,Long backId, Date startDate, Date endDate){
        ResponseMeta responseMeta = new ResponseMeta();
        try{
            Map map = new HashMap();

            SbEquipmentT sbEquipmentT = sbEquipmentTService.findById(equipmentId);
            double A1 = sbElFeeService.CalDayElFeeA1ByEquipmentTId(equipmentId);
            long days = sbElFeeService.CalEquipmentElDays(useId,backId,equipmentId,startDate,endDate);
            double A3_rate = sbElFeeService.CalDayElFeeA3T_rate(useId,equipmentId);

            SbElFeeConfig sbElFeeConfig = sbElFeeConfigService.findByYear("2018");

            map.put("统计开始时间~结束时间:",DateUtils.formatDate(startDate)+"~"+DateUtils.formatDate(endDate));
            map.put("设备名称:",sbEquipmentT.getName());
            map.put("购买价格:",sbEquipmentT.getBuyPrice());
            map.put("设备折旧年限:",sbEquipmentT.getCostYears());
            map.put("设备基本使用费(A1):",BigDecimal.valueOf(A1*days).setScale(2, RoundingMode.UP).doubleValue());
            map.put("设备租赁天数:",days);
            map.put("设备新度系数调节费(A3):",BigDecimal.valueOf(A1*A3_rate*days).setScale(2, RoundingMode.UP).doubleValue());
            map.put("2018年：","一类设备增值税率:"+sbElFeeConfig.getOneIncreRate()
                    +",一类设备融资租赁利率:"+sbElFeeConfig.getOneElRate()
                    +",一类设备修理费率:"+sbElFeeConfig.getOneRepairRate()
                    +",一类设备管理费率:"+sbElFeeConfig.getOneManageRate()
                    +",一类设备使用系数:"+sbElFeeConfig.getOneUseRate()
                    +",二类设备修理费率:"+sbElFeeConfig.getTwoRepairRate()
                    +",二类设备管理费率:"+sbElFeeConfig.getTwoManageRate());

            responseMeta.setMeta(Constants.RESPONSE_SUCCESS,"返回设备租赁费信息成功");
            responseMeta.setData(map);
        }catch (Exception e){
            responseMeta.setMeta(Constants.RESPONSE_EXCEPTION,"返回设备租赁费信息失败");
            responseMeta.setData(ExceptionUtils.getStackTrace(e));
        }
        return responseMeta;

    }

}
