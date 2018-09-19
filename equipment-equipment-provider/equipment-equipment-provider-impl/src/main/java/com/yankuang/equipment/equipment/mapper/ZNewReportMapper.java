package com.yankuang.equipment.equipment.mapper;

import com.yankuang.equipment.equipment.model.ZNewReport;
import org.springframework.stereotype.Repository;

@Repository
public interface ZNewReportMapper{

    /**
     * 保存新度系数调节费清单
     * @param zNewReport
     * @return
     */
    int create(ZNewReport zNewReport);

}
