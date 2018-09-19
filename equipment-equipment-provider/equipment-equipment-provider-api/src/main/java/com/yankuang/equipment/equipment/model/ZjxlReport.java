package com.yankuang.equipment.equipment.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name="el_zjxl_zReport")
public class ZjxlReport implements Serializable {

    private Long id;

    private String usePosition;//使用地点

    private String usePositionNum;//申请单编号

    private Integer appendixPage;//附清单（张）

    private Double zjxlFee;//综机折旧大修费

    private String remark;//备注

    private Double sum;//合计

    private Integer appendixPageSum;//附清单（张）合计

    private String useDepartment;//使用单位

    private String number;//编号

    private String satrap;//主管人

    private String shPerson;//审核人

    private String centerManager;//中心经办人

    private String sectionChief;//科长签字

    private String capital;//大写

    private String zjxlYear;//折旧修理年份

    private String zjxlMonth;//折旧修理月份

    private String zjxlDay;//折旧修理日期
}
