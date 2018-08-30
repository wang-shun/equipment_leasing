package com.yankuang.equipment.equipment.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SbType implements Serializable {
    private Long id;

    @NotEmpty(message = "设备编码不能为空!")
    @Length(min = 1,max = 200,message = "设备分类编码长度超出范围!")
    private String code;

    @NotEmpty(message = "设备名称不能为空!")
    @Length(min = 1,max = 200,message = "设备名称长度超出范围!")
    private String name;

    private String pcode;

    private Long sorting;

    private Long level;

    private Byte status;

    private String remark;

    private String createBy;

    private Date createAt;

    private String updateBy;

    private Date updateAt;

    private Long version;


    private String mainPara;

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

    private List<SbType> children = new ArrayList<SbType>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPcode() {
        return pcode;
    }

    public void setPcode(String pcode) {
        this.pcode = pcode == null ? null : pcode.trim();
    }

    public Long getSorting() {
        return sorting;
    }

    public void setSorting(Long sorting) {
        this.sorting = sorting;
    }

    public Long getLevel() {
        return level;
    }

    public void setLevel(Long level) {
        this.level = level;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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

    public List<SbType> getChildren() {
        return children;
    }

    public void setChildren(List<SbType> children) {
        this.children = children;
    }

    public String getMainPara() {
        return mainPara;
    }

    public void setMainPara(String mainPara) {
        this.mainPara = mainPara;
    }

    public String getMainParaType() {
        return mainParaType;
    }

    public void setMainParaType(String mainParaType) {
        this.mainParaType = mainParaType;
    }

    public String getDefPara1() {
        return defPara1;
    }

    public void setDefPara1(String defPara1) {
        this.defPara1 = defPara1;
    }

    public String getDefPara1Type() {
        return defPara1Type;
    }

    public void setDefPara1Type(String defPara1Type) {
        this.defPara1Type = defPara1Type;
    }

    public String getDefPara2() {
        return defPara2;
    }

    public void setDefPara2(String defPara2) {
        this.defPara2 = defPara2;
    }

    public String getDefPara2Type() {
        return defPara2Type;
    }

    public void setDefPara2Type(String defPara2Type) {
        this.defPara2Type = defPara2Type;
    }

    public String getDefPara3() {
        return defPara3;
    }

    public void setDefPara3(String defPara3) {
        this.defPara3 = defPara3;
    }

    public String getDefPara3Type() {
        return defPara3Type;
    }

    public void setDefPara3Type(String defPara3Type) {
        this.defPara3Type = defPara3Type;
    }

    public String getDefPara4() {
        return defPara4;
    }

    public void setDefPara4(String defPara4) {
        this.defPara4 = defPara4;
    }

    public String getDefPara4Type() {
        return defPara4Type;
    }

    public void setDefPara4Type(String defPara4Type) {
        this.defPara4Type = defPara4Type;
    }

    public String getDefPara5() {
        return defPara5;
    }

    public void setDefPara5(String defPara5) {
        this.defPara5 = defPara5;
    }

    public String getDefPara5Type() {
        return defPara5Type;
    }

    public void setDefPara5Type(String defPara5Type) {
        this.defPara5Type = defPara5Type;
    }

    public String getDefPara6() {
        return defPara6;
    }

    public void setDefPara6(String defPara6) {
        this.defPara6 = defPara6;
    }

    public String getDefPara6Type() {
        return defPara6Type;
    }

    public void setDefPara6Type(String defPara6Type) {
        this.defPara6Type = defPara6Type;
    }

    public String getDefPara7() {
        return defPara7;
    }

    public void setDefPara7(String defPara7) {
        this.defPara7 = defPara7;
    }

    public String getDefPara7Type() {
        return defPara7Type;
    }

    public void setDefPara7Type(String defPara7Type) {
        this.defPara7Type = defPara7Type;
    }

    public String getDefPara8() {
        return defPara8;
    }

    public void setDefPara8(String defPara8) {
        this.defPara8 = defPara8;
    }

    public String getDefPara8Type() {
        return defPara8Type;
    }

    public void setDefPara8Type(String defPara8Type) {
        this.defPara8Type = defPara8Type;
    }
}