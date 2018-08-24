package com.yankuang.equipment.equipment.model;

import lombok.Data;
import lombok.NonNull;

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

    private Long useBy;

    private Date useAt;

    private String usePosition;

    private String useEquipmentType;

    private Long approveBy;

    private Date approveAt;

    private String approveOpinion;

    private Long createBy;

    private Date createAt;

    private Long updateBy;

    private Date updateAt;

    private String version;

    private String remarks;

    private Byte isUse;

    private String backup2;

    private String backup3;

    private List<ElUseItem> elUseItems;

    private Long useId;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
