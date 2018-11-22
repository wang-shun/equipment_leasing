package com.yankuang.equipment.equipment.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 综机设备使用(停用)交接单
 *
 */
@Data
@Entity
@Table(name = "el_zjsb_use_item")
public class ZjSbUseItem implements Serializable {

    private Long id;

    private Long receiptId;//交接单id

    private Long useId;//领用申请Id

    private Long equipmentId;//设备Id

    private String equipmentEffect;//使用地点，功能位置

    private String departmentName;//领用单位

    private String equName;//设备名称

    private String equipmentModel;//设备型号

    private Integer equipmentNum;//数量

    private String techCode;//技术标识号

    private String comp;//归属公司

    private Byte isNew;//是否是新的

    private String stubUnit;//存根单位

    private String remark;//备注

    private Byte isUse;//领用、退租 1.领用 2.退租

//    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private String useAt;//领用时间

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateAt;//更新修改时间

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createAt;//创建时间

    private Byte status;


}
