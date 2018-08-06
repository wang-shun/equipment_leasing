package com.yankuang.equipment.authority.service.impl;

import com.yankuang.equipment.authority.mapper.DeptMapper;
import com.yankuang.equipment.authority.model.Dept;
import com.yankuang.equipment.authority.service.DeptService;
import com.yankuang.equipment.common.util.UuidUtils;
import io.terminus.boot.rpc.common.annotation.RpcProvider;
import io.terminus.common.model.Paging;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RpcProvider
public class DeptServiceImpl implements DeptService{
    @Autowired
    DeptMapper deptMapper;

    public Dept getById(Long id) {
        return deptMapper.findById(id);
    }

    public boolean update(Dept dept){
        return deptMapper.update(dept);
    }

    public boolean add(Dept dept){
        dept.setCode(UuidUtils.newUuid());
        dept.setCreateBy("小狼");//TODO 由于用户功能暂未开发完，先写死，后期改
        dept.setUpdateBy("小狼");
        return deptMapper.create(dept);
    }

    public boolean del(Long id){
        return  deptMapper.updatedel(id);
    }

    public Dept getByName(String name){
        return deptMapper.findByName(name);
    }

    public List<Dept> getAll( ){
        return deptMapper.getAll( );
    }

    public Paging findpage(int pageSize, int pageNum, Dept dept){
        int maxResult = (pageNum - 1) * pageSize;
        Paging page = deptMapper.paging(maxResult, pageNum, dept);
        return page;
    }
    public List<String> findName(){
        return deptMapper.getName();
    }
}
