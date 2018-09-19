package com.yankuang.equipment.equipment.mapper;

import com.yankuang.equipment.equipment.model.ZjDepreciationCostReportItem;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ZjDepreciationCostReportItemMapper {

    Integer create(ZjDepreciationCostReportItem zjDepreciationCostReportItem);

    List<ZjDepreciationCostReportItem> list(Map zjCostRepairMap);

    List<ZjDepreciationCostReportItem> listHomeOffice(@Param("reportId") Long reportId,@Param("deptType") String deptType);

}
