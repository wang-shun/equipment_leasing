package com.yankuang.equipment.equipment.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
@Data
@Entity
@Table(name = "el_fee_quarter_t")
public class ElFeeQuarterT implements Serializable {
    private Long id;

    private Byte status; // 状态 1:正常 2:历史 99:删除

    private String positionName; // 矿处单位名称

    private String positionCode; // 矿处单位code

    private String reportYear; // 报表年度

    private String reportQuarter; // 报表季度

    private String reportMonth; // 报表月度

    private Long useNum; // 在用数量

    private Double totalCostA1; // 月度费用

    private Double totalCostA3; // 费用调整

    private Double totalFee; // 月度合计

    private Long totalDay; // 使用天数

    private String remarks; // 备注

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date exportAt; // 导出日期

    private Double totalCostA2; // A2

    private Long version; // 版本

    @JsonIgnore
    private String exportAtStr; // 导出日期 年度-月度

    private Long createBy; // 制表人ID

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createAt; // 制表时间

    private Long updateBy; // 更新人ID

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateAt; // 更新时间

    private Long centerLeaderBy; // 中心主管ID

    private Long centerOfficeBy; // 中心科室负责人ID

    private Long centerHandleBy; // 中心经办人ID

    private Long positionBy; // 设备使用方机电矿长ID

    @JsonIgnore
    private String code;

    @JsonIgnore
    private Long sorting;

    @JsonIgnore
    private Integer pageNum; // 页码

    @JsonIgnore
    private Integer pageSize; // 每页记录数
}