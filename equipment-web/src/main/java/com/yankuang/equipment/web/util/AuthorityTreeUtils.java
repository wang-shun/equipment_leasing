package com.yankuang.equipment.web.util;

import com.yankuang.equipment.web.dto.AuthorityTreeDTO;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AuthorityTreeUtils {


    public static Map<String,Object> mapArray = new LinkedHashMap<String, Object>();
    public List<AuthorityTreeDTO> menuCommon;
    public List<AuthorityTreeDTO> list = new ArrayList<>();

    public List<AuthorityTreeDTO> menuList(List<AuthorityTreeDTO> menu){
        this.menuCommon = menu;
        for (AuthorityTreeDTO x : menu) {
            AuthorityTreeDTO mapArr = new AuthorityTreeDTO();
            if("0".equals(x.getPcode())){
                mapArr.setCode(x.getCode());
                mapArr.setName(x.getName());
                mapArr.setPcode(x.getPcode());
                mapArr.setType(x.getType());
                mapArr.setLevel(x.getLevel());
                mapArr.setUrl(x.getUrl());
                mapArr.setSorting(x.getSorting());
                mapArr.setIcon(x.getIcon());
                mapArr.setChildList(menuChild(x.getCode()));
                list.add(mapArr);
            }
        }
        return list;
    }

    public List<AuthorityTreeDTO> menuChild(String code){
        List<AuthorityTreeDTO> lists = new ArrayList<>();
        for(AuthorityTreeDTO a:menuCommon){
            AuthorityTreeDTO childArray = new AuthorityTreeDTO();
            if(code.equals(a.getCode())){
                childArray.setCode(a.getCode());
                childArray.setName(a.getName());
                childArray.setPcode(a.getPcode());
                childArray.setType(a.getType());
                childArray.setLevel(a.getLevel());
                childArray.setUrl(a.getUrl());
                childArray.setSorting(a.getSorting());
                childArray.setIcon(a.getIcon());
                childArray.setChildList(menuChild(a.getCode()));
                lists.add(childArray);
            }
        }
        return lists;
    }

}
