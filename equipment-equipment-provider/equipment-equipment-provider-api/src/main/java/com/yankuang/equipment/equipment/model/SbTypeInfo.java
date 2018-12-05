package com.yankuang.equipment.equipment.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

public class SbTypeInfo implements Serializable {
    private Long id;

    @NotNull
    private Long typeId;

    @Length(min = 1,max = 200,message = "主要技术参数长度超出范围!")
    private String mainPara;

    @Length(min = 1,max = 200,message = "参数类型长度超出范围!")
    private String mainParaType;

    private String defPara1;

    private String defPara1Type;

    private String defPara2;

    private String defPara2Type;

    private String defPara3;

    private String defPara3Type;

    private String defPara4;

    private String defPara4Type;

    private String defPara5;

    private String defPara5Type;

    private String defPara6;

    private String defPara6Type;

    private String defPara7;

    private String defPara7Type;

    private String defPara8;

    private String defPara8Type;

    private Byte status;

    private String createBy;

    private Date createAt;

    private String updateBy;

    private Date updateAt;

    private Long version;


    private String typeCode;//设备类型编码
    private String typeName;//设备类型名称

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public String getMainPara() {
        return mainPara;
    }

    public void setMainPara(String mainPara) {
        this.mainPara = mainPara == null ? null : mainPara.trim();
    }

    public String getMainParaType() {
        return mainParaType;
    }

    public void setMainParaType(String mainParaType) {
        this.mainParaType = mainParaType == null ? null : mainParaType.trim();
    }

    public String getDefPara1() {
        return defPara1;
    }

    public void setDefPara1(String defPara1) {
        this.defPara1 = defPara1 == null ? null : defPara1.trim();
    }

    public String getDefPara1Type() {
        return defPara1Type;
    }

    public void setDefPara1Type(String defPara1Type) {
        this.defPara1Type = defPara1Type == null ? null : defPara1Type.trim();
    }

    public String getDefPara2() {
        return defPara2;
    }

    public void setDefPara2(String defPara2) {
        this.defPara2 = defPara2 == null ? null : defPara2.trim();
    }

    public String getDefPara2Type() {
        return defPara2Type;
    }

    public void setDefPara2Type(String defPara2Type) {
        this.defPara2Type = defPara2Type == null ? null : defPara2Type.trim();
    }

    public String getDefPara3() {
        return defPara3;
    }

    public void setDefPara3(String defPara3) {
        this.defPara3 = defPara3 == null ? null : defPara3.trim();
    }

    public String getDefPara3Type() {
        return defPara3Type;
    }

    public void setDefPara3Type(String defPara3Type) {
        this.defPara3Type = defPara3Type == null ? null : defPara3Type.trim();
    }

    public String getDefPara4() {
        return defPara4;
    }

    public void setDefPara4(String defPara4) {
        this.defPara4 = defPara4 == null ? null : defPara4.trim();
    }

    public String getDefPara4Type() {
        return defPara4Type;
    }

    public void setDefPara4Type(String defPara4Type) {
        this.defPara4Type = defPara4Type == null ? null : defPara4Type.trim();
    }

    public String getDefPara5() {
        return defPara5;
    }

    public void setDefPara5(String defPara5) {
        this.defPara5 = defPara5 == null ? null : defPara5.trim();
    }

    public String getDefPara5Type() {
        return defPara5Type;
    }

    public void setDefPara5Type(String defPara5Type) {
        this.defPara5Type = defPara5Type == null ? null : defPara5Type.trim();
    }

    public String getDefPara6() {
        return defPara6;
    }

    public void setDefPara6(String defPara6) {
        this.defPara6 = defPara6 == null ? null : defPara6.trim();
    }

    public String getDefPara6Type() {
        return defPara6Type;
    }

    public void setDefPara6Type(String defPara6Type) {
        this.defPara6Type = defPara6Type == null ? null : defPara6Type.trim();
    }

    public String getDefPara7() {
        return defPara7;
    }

    public void setDefPara7(String defPara7) {
        this.defPara7 = defPara7 == null ? null : defPara7.trim();
    }

    public String getDefPara7Type() {
        return defPara7Type;
    }

    public void setDefPara7Type(String defPara7Type) {
        this.defPara7Type = defPara7Type == null ? null : defPara7Type.trim();
    }

    public String getDefPara8() {
        return defPara8;
    }

    public void setDefPara8(String defPara8) {
        this.defPara8 = defPara8 == null ? null : defPara8.trim();
    }

    public String getDefPara8Type() {
        return defPara8Type;
    }

    public void setDefPara8Type(String defPara8Type) {
        this.defPara8Type = defPara8Type == null ? null : defPara8Type.trim();
    }
    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public String getTypeName() {
        return typeName;
    }
}