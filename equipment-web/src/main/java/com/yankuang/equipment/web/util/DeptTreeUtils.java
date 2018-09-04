package com.yankuang.equipment.web.util;


import com.yankuang.equipment.web.dto.DeptTreeDTO;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DeptTreeUtils {

    public static Map<String,Object> mapArray = new LinkedHashMap<String, Object>();
    public List<DeptTreeDTO> menuCommon;
    public List<DeptTreeDTO> list = new ArrayList<>();

    public List<DeptTreeDTO> menuList(List<DeptTreeDTO> menu){
        this.menuCommon = menu;
        for (DeptTreeDTO x : menu) {
            DeptTreeDTO mapArr = new DeptTreeDTO();
            if("0".equals(x.getPcode())){
                mapArr.setCode(x.getCode());
                mapArr.setName(x.getName());
                mapArr.setPcode(x.getPcode());
                mapArr.setType(x.getType());
                mapArr.setSorting(x.getSorting());
                mapArr.setCreateAt(x.getCreateAt());
                mapArr.setCreateBy(x.getCreateBy());
                mapArr.setAddress(x.getAddress());
                mapArr.setRemark(x.getRemark());
                mapArr.setChildList(menuChild(x.getCode()));
                list.add(mapArr);
            }
        }
        return list;
    }

    public List<DeptTreeDTO> menuChild(String code){
        List<DeptTreeDTO> lists = new ArrayList<>();
        for(DeptTreeDTO a:menuCommon){
            DeptTreeDTO childArray = new DeptTreeDTO();
            if(code.equals(a.getPcode())){
                childArray.setCode(a.getCode());
                childArray.setName(a.getName());
                childArray.setPcode(a.getPcode());
                childArray.setType(a.getType());
                childArray.setSorting(a.getSorting());
                childArray.setCreateAt(a.getCreateAt());
                childArray.setCreateBy(a.getCreateBy());
                childArray.setAddress(a.getAddress());
                childArray.setRemark(a.getRemark());
                childArray.setChildList(menuChild(a.getCode()));
                lists.add(childArray);
            }
        }
        return lists;
    }
    
}
