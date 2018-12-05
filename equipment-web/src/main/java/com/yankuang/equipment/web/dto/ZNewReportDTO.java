package com.yankuang.equipment.web.dto;

import com.yankuang.equipment.equipment.model.ZNewReport;
import lombok.Data;

import java.io.Serializable;
import java.util.List;


public class ZNewReportDTO implements Serializable {

    private List<ZNewReport> zNewReports;

    public List<ZNewReport> getzNewReports() {
        return zNewReports;
    }

    public void setzNewReports(List<ZNewReport> zNewReports) {
        this.zNewReports = zNewReports;
    }
}
