package com.yankuang.equipment.equipment.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "el_use")
public class ElUse implements Serializable{
    private Long id;

    private String approveCode;

    private String code;

    private String sorting;

    private Byte isDel;

    private String status;

    private String useBy;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date useAt; // 计费开始时间
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date rentAt; // 领用时间

    private Long usePosition;

    private String useEquipmentType;

    private Long approveBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date approveAt;

    private String approveOpinion;

    private String createBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createAt;

    private String updateBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateAt;

    private String version;

    private String remarks;

    private Byte isUse;

    private String backup3;

    private List<ElUseItem> elUseItems;

    private Long useId;

    private String name;

    private String startTime;

    private String endTime;

    private String userName;

    private String dateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
