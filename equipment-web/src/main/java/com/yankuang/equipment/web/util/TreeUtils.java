package com.yankuang.equipment.web.util;


import com.yankuang.equipment.web.dto.TreeDTO;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TreeUtils {

    public static Map<String,Object> mapArray = new LinkedHashMap<String, Object>();
    public List<TreeDTO> menuCommon;
    public List<Object> list = new ArrayList<Object>();

    public List<Object> menuList(List<TreeDTO> menu){
        this.menuCommon = menu;
        for (TreeDTO x : menu) {
            Map<String,Object> mapArr = new LinkedHashMap<String, Object>();
            if(x.getpId()==0){
                mapArr.put("id", x.getId());
                mapArr.put("name", x.getName());
                mapArr.put("pid", x.getpId());
                mapArr.put("childList", menuChild(x.getId()));
                list.add(mapArr);
            }
        }
        return list;
    }

    public List<?> menuChild(Long id){
        List<Object> lists = new ArrayList<Object>();
        for(TreeDTO a:menuCommon){
            Map<String,Object> childArray = new LinkedHashMap<String, Object>();
            if(a.getpId() == id){
                childArray.put("id", a.getId());
                childArray.put("name", a.getName());
                childArray.put("pid", a.getpId());
                childArray.put("childList", menuChild(a.getId()));
                lists.add(childArray);
            }
        }
        return lists;
    }

}
