package com.yankuang.equipment.equipment.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "el_fee_middle_t")
public class ElFeeMiddleT implements Serializable {
    private Long id;

    private String code;

    private Byte status; // 状态 1：正常 2：删除

    private String positionName; // 矿处单位名称

    private String positionCode; // 矿处单位code

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date exportAt; // 导出日期

    private String reportYear; // 报表年度

    private String reportMonth; // 报表月度

    private String middleCode; // 中类code

    private String middleName; // 中类名称

    private Long equipmentNum; // 在藉数量

    private Long useNum; // 使用数量

    private Double costA1; // 租赁费

    private Double costA2; // 非正常损坏赔偿费

    private Double costA3; // 费用调整

    private Double totalFee; // 合计

    private String remarks; // 备注

    private String exportAtStr; // 导出月度 年度-月度

    private Long approveBy; // 审核人ID

    private Long positionBy; // 矿方人ID

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateAt; // 更新时间

    private Long updateBy; // 更新人ID

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createAt; // 创建时间

    private Long createBy; // 创建人ID

    private Long sorting; // 排序

    private Long version; // 版本

    private Integer pageNum; // 页码

    private Integer pageSize; // 每页记录数

}