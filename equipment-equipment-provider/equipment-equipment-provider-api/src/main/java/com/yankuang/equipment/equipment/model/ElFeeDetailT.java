package com.yankuang.equipment.equipment.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "el_fee_detail_t")
public class ElFeeDetailT implements Serializable {
    private Long id;

    private String reportYear; // 报表年度

    private String reportMonth; // 报表月度

    private String exportAtStr; // 导出时间 年度-月度

    private Long status; // 状态 1:正常  99:删除

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date exportAt; //导出时间

    private Long equipmentId; //设备ID

    private String positionName; // 矿处单位名称

    private String positionCode; // 矿处单位code

    private String middleTypeName; // 中类名称

    private String middleTypeCode; // 中类code

    private String smallTypeName; // 小类名称

    private String smallTypeCode; // 小类code

    private String equipmentCode; // 设备识别码

    private String techCode; // 技术标识号

    private String modelCode; // 规格型号code

    private String modelName; // 规格型号名称

    private String effectName; // 功能位置名称

    private String effectCode; // 功能位置code

    private Long elDays; // 租赁天数

    private Double costA1; // 租赁价格

    private Double costA3; // 调整金额

    private Double totalFee; // 合计

    private String remarks; // 备注

    private Long updateBy; // 更新人ID

    private Long version; // 版本

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateAt; // 更新时间

    private Long createBy; // 创建人ID

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createAt; // 创建时间

    private String code;

    private Long sorting;

    private Integer pageNum;

    private Integer pageSize;

}