package com.yankuang.equipment.web.dto;

import com.yankuang.equipment.equipment.model.ZjxlReport;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ZjxlZReportDTO implements Serializable {

   private List<ZjxlReport> zjxlReports;
}
