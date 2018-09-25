package com.yankuang.equipment.equipment.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@Entity
@Table(name = "el_fee_position_t")
public class ElFeePositionT implements Serializable {
    private Long id;

    private Byte status; // 状态

    private String reportYear; // 报表年度

    private String reportMonth; // 报表月度

    private String middleName; // 类型名称

    private String middleCode; // 类型code

    private String positionMap; // 各矿数据String

    private Double totalFee; // 小计

    private Long version; // 版本

    private String remarks; // 备注

    //@JsonIgnore
    private Map<String, Double> depositMap; // 各矿数据

    private List depositList; // 各矿数据List

    private Long createBy; // 创建人ID

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createAt; // 创建时间

    private Long updateBy; // 更新人ID

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateAt; // 更新时间

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date exportAt; // 导出时间

    private Byte poStatus; // 矿状态 1:本部矿  2:外部矿

    @JsonIgnore
    private String code;

    @JsonIgnore
    private Long sorting;

    @JsonIgnore
    private String exportAtStr;

    @JsonIgnore
    private Integer pageNum; // 页码

    @JsonIgnore
    private Integer pageSize; // 每页记录数
}