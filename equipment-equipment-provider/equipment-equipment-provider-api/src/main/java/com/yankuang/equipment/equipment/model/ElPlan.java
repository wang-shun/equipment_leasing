package com.yankuang.equipment.equipment.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "el_plan")
public class ElPlan implements Serializable {
    private String planId;

    @NotNull(message = "需求年度不得为空")
    private Integer planYear;

    private Integer planMonth;

    private Integer planQuarter;

    @NotNull(message = "提出单位不得为空")
    private String planPosition;

    private String planRemarks;

    private String planOpinion;

    @NotNull(message = "提出人ID不得为空")
    private String planCreatorId;

    private String planStatus;

    private Long planApproveTime;

    private String planApproverId;

    private String planApproverName;

    private String planEquipmentType;

    private String planType;

    private Boolean planIsDel;

    private Long planCreateTime;

    private Long planUpdateTime;

    private String planCode;

    private String planUpdatorId;

    private String planUpdatorName;

    private String planSorting;

    private String planVersion;

    @NotNull(message = "提出人姓名不得为空")
    private String planCreatorName;

    private List<ElPlanItem> elPlanItemList;

    public String getPlanId() {
        return planId;
    }

    public List<ElPlanItem> getElPlanItemList() {
        return elPlanItemList;
    }

}