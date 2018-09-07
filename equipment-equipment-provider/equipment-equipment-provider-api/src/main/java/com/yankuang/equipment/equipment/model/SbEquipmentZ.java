package com.yankuang.equipment.equipment.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class SbEquipmentZ implements Serializable {
    private Long id;

    @NotEmpty(message = "设备识别码不能为空!")
    @Length(min = 1,max = 200,message = "识别码长度超出范围!")
    private String code;

    @NotEmpty(message = "设备名称不能为空!")
    @Length(min = 1,max = 200,message = "设备名称长度超出范围!")
    private String name;

    private Byte isLease;

    private String sbtypeOne;
    private String sbtypeOneName;

    private String sbtypeTwo;
    private String sbtypeTwoName;

    private String sbtypeThree;
    private String sbtypeThreeName;

    private String sbmodelCode;
    private String sbmodelName;

    private String techCode;

    private String factory;

    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date prodDate;

    private String prodCode;

    private String maCode;

    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date maStartDate;

    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date maEndDate;

    private String proof;

    private String proofCode;

    private String license;

    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date arriveDate;

    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date useDate;

    private String comp;

    private String contranct;

    @Range(min = 0,max = 9999999,message = "购置价值必须在0~9999999之间!")
    private BigDecimal buyPrice;

    private Byte isNew;

    private String ware;

    private Byte isRfid;

    private String assetCode;

    private Byte status;

    private String createBy;

    private Date createAt;

    private String updateBy;

    private Date updateAt;

    private Long version;

    private String positionCode;

    private String stateCode;

    @Range(min = 0,max = 99,message = "财务费用折旧年限必须在0~99之间!")
    private Byte costYears;

    private Double dayLeaseFee;

    private String mainPara;
    private String para1;
    private String para2;
    private String para3;
    private String para4;
    private String para5;
    private String para6;
    private String para7;
    private String para8;

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

    public Byte getIsLease() {
        return isLease;
    }

    public void setIsLease(Byte isLease) {
        this.isLease = isLease;
    }

    public String getSbtypeOne() {
        return sbtypeOne;
    }

    public void setSbtypeOne(String sbtypeOne) {
        this.sbtypeOne = sbtypeOne == null ? null : sbtypeOne.trim();
    }

    public String getSbtypeTwo() {
        return sbtypeTwo;
    }

    public void setSbtypeTwo(String sbtypeTwo) {
        this.sbtypeTwo = sbtypeTwo == null ? null : sbtypeTwo.trim();
    }

    public String getSbtypeThree() {
        return sbtypeThree;
    }

    public void setSbtypeThree(String sbtypeThree) {
        this.sbtypeThree = sbtypeThree == null ? null : sbtypeThree.trim();
    }

    public String getSbmodelCode() {
        return sbmodelCode;
    }

    public void setSbmodelCode(String sbmodelCode) {
        this.sbmodelCode = sbmodelCode == null ? null : sbmodelCode.trim();
    }

    public String getTechCode() {
        return techCode;
    }

    public void setTechCode(String techCode) {
        this.techCode = techCode == null ? null : techCode.trim();
    }

    public String getFactory() {
        return factory;
    }

    public void setFactory(String factory) {
        this.factory = factory == null ? null : factory.trim();
    }

    public Date getProdDate() {
        return prodDate;
    }

    public void setProdDate(Date prodDate) {
        this.prodDate = prodDate;
    }

    public String getProdCode() {
        return prodCode;
    }

    public void setProdCode(String prodCode) {
        this.prodCode = prodCode == null ? null : prodCode.trim();
    }

    public String getMaCode() {
        return maCode;
    }

    public void setMaCode(String maCode) {
        this.maCode = maCode == null ? null : maCode.trim();
    }

    public Date getMaStartDate() {
        return maStartDate;
    }

    public void setMaStartDate(Date maStartDate) {
        this.maStartDate = maStartDate;
    }

    public Date getMaEndDate() {
        return maEndDate;
    }

    public void setMaEndDate(Date maEndDate) {
        this.maEndDate = maEndDate;
    }

    public String getProof() {
        return proof;
    }

    public void setProof(String proof) {
        this.proof = proof == null ? null : proof.trim();
    }

    public String getProofCode() {
        return proofCode;
    }

    public void setProofCode(String proofCode) {
        this.proofCode = proofCode == null ? null : proofCode.trim();
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license == null ? null : license.trim();
    }

    public Date getArriveDate() {
        return arriveDate;
    }

    public void setArriveDate(Date arriveDate) {
        this.arriveDate = arriveDate;
    }

    public Date getUseDate() {
        return useDate;
    }

    public void setUseDate(Date useDate) {
        this.useDate = useDate;
    }

    public String getComp() {
        return comp;
    }

    public void setComp(String comp) {
        this.comp = comp == null ? null : comp.trim();
    }

    public String getContranct() {
        return contranct;
    }

    public void setContranct(String contranct) {
        this.contranct = contranct == null ? null : contranct.trim();
    }

    public BigDecimal getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(BigDecimal buyPrice) {
        this.buyPrice = buyPrice;
    }

    public Byte getIsNew() {
        return isNew;
    }

    public void setIsNew(Byte isNew) {
        this.isNew = isNew;
    }

    public String getWare() {
        return ware;
    }

    public void setWare(String ware) {
        this.ware = ware == null ? null : ware.trim();
    }

    public Byte getIsRfid() {
        return isRfid;
    }

    public void setIsRfid(Byte isRfid) {
        this.isRfid = isRfid;
    }

    public String getAssetCode() {
        return assetCode;
    }

    public void setAssetCode(String assetCode) {
        this.assetCode = assetCode == null ? null : assetCode.trim();
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

    public String getPositionCode() {
        return positionCode;
    }

    public void setPositionCode(String positionCode) {
        this.positionCode = positionCode == null ? null : positionCode.trim();
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode == null ? null : stateCode.trim();
    }

    public Byte getCostYears() {
        return costYears;
    }

    public void setCostYears(Byte costYears) {
        this.costYears = costYears;
    }

    public Double getDayLeaseFee() {
        return dayLeaseFee;
    }

    public void setDayLeaseFee(Double dayLeaseFee) {
        this.dayLeaseFee = dayLeaseFee;
    }

    public String getSbtypeOneName() {
        return sbtypeOneName;
    }

    public void setSbtypeOneName(String sbtypeOneName) {
        this.sbtypeOneName = sbtypeOneName;
    }

    public String getSbtypeTwoName() {
        return sbtypeTwoName;
    }

    public void setSbtypeTwoName(String sbtypeTwoName) {
        this.sbtypeTwoName = sbtypeTwoName;
    }

    public String getSbtypeThreeName() {
        return sbtypeThreeName;
    }

    public void setSbtypeThreeName(String sbtypeThreeName) {
        this.sbtypeThreeName = sbtypeThreeName;
    }

    public String getSbmodelName() {
        return sbmodelName;
    }

    public void setSbmodelName(String sbmodelName) {
        this.sbmodelName = sbmodelName;
    }

    public String getMainPara() {
        return mainPara;
    }

    public void setMainPara(String mainPara) {
        this.mainPara = mainPara;
    }

    public String getPara1() {
        return para1;
    }

    public void setPara1(String para1) {
        this.para1 = para1;
    }

    public String getPara2() {
        return para2;
    }

    public void setPara2(String para2) {
        this.para2 = para2;
    }

    public String getPara3() {
        return para3;
    }

    public void setPara3(String para3) {
        this.para3 = para3;
    }

    public String getPara4() {
        return para4;
    }

    public void setPara4(String para4) {
        this.para4 = para4;
    }

    public String getPara5() {
        return para5;
    }

    public void setPara5(String para5) {
        this.para5 = para5;
    }

    public String getPara6() {
        return para6;
    }

    public void setPara6(String para6) {
        this.para6 = para6;
    }

    public String getPara7() {
        return para7;
    }

    public void setPara7(String para7) {
        this.para7 = para7;
    }

    public String getPara8() {
        return para8;
    }

    public void setPara8(String para8) {
        this.para8 = para8;
    }
}