package com.yankuang.equipment.equipment.mapper;

import com.yankuang.equipment.equipment.model.ZNewReport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ZNewReportMapper{

    /**
     * 保存新度系数调节费清单
     * @param zNewReport
     * @return
     */
    int create(ZNewReport zNewReport);

    /**
     * 查询新度系数调节清单
     * @param zNewReport
     * @return
     */
    List<ZNewReport> list(ZNewReport zNewReport);

}
