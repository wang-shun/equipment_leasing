package com.yankuang.equipment.equipment.mapper;

import com.yankuang.equipment.equipment.model.ZjxlReport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ZjxlReportMapper {

    int create(ZjxlReport zjxlReport);

    List<ZjxlReport> list(ZjxlReport zjxlReport);

    List<ZjxlReport> listHome(String kb);

    int update(ZjxlReport zjxlReport);

}
