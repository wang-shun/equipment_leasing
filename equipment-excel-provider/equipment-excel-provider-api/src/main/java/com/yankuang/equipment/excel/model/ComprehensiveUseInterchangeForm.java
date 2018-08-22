package com.yankuang.equipment.excel.model;

import lombok.Data;

/**
 * @author xhh
 * @date 2018/8/21 11:20
 * 综机设备使用交接单
 */
@Data
public class ComprehensiveUseInterchangeForm {

    private String item_position;//使用单位

    private String use_place;// 使用地点

    private String equipment_name;//设备名称

    private String equipment_specification;// 设备型号

    private int equipment_num;//数量

    private String tech_code;//技术标识号

    private String company_affiliation;//归属公司

    private String whether_new_equ;//是否新设备

    private String remark;//备注

    private String handover_date;//交接日期


}
