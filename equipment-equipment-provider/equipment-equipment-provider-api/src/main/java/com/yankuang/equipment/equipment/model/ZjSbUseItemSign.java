package com.yankuang.equipment.equipment.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 综机设备使用交接单sign
 *  xhh
 */
@Data
@Entity
@Table(name = "el_zjsb_use_item_sign")
public class ZjSbUseItemSign implements Serializable {

    private Long id;//交接单id

    private String equipmentManagement;//设备管理中心经办人

    private String buildingUser;//使用单位经办人

    private String handover;//交接日期

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateAt;//修改时间

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createAt;//创建时间

    private List<ZjSbUseItem> zjSbUseItems;

}
