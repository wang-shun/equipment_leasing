package com.yankuang.equipment.web.util;

import com.yankuang.equipment.web.dto.AuthorityDTO;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AuthorityTreeUtils {


    public static Map<String,Object> mapArray = new LinkedHashMap<String, Object>();
    public List<AuthorityDTO> menuCommon;
    public List<AuthorityDTO> list = new ArrayList<>();

    public List<AuthorityDTO> menuList(List<AuthorityDTO> menu){
        this.menuCommon = menu;
        for (AuthorityDTO x : menu) {
            AuthorityDTO mapArr = new AuthorityDTO();
            if(x.getpId()==0){
                mapArr.setId(x.getId());
                mapArr.setName(x.getName());
                mapArr.setpId(x.getpId());
                mapArr.setType(x.getType());
                mapArr.setLevel(x.getLevel());
                mapArr.setUrl(x.getUrl());
                mapArr.setChildList(menuChild(x.getId()));
                list.add(mapArr);
            }
        }
        return list;
    }

    public List<AuthorityDTO> menuChild(Long id){
        List<AuthorityDTO> lists = new ArrayList<>();
        for(AuthorityDTO a:menuCommon){
            AuthorityDTO childArray = new AuthorityDTO();
            if(a.getpId() == id){
                childArray.setId(a.getId());
                childArray.setName(a.getName());
                childArray.setpId(a.getpId());
                childArray.setType(a.getType());
                childArray.setLevel(a.getLevel());
                childArray.setUrl(a.getUrl());
                childArray.setChildList(menuChild(a.getId()));
                lists.add(childArray);
            }
        }
        return lists;
    }

}
