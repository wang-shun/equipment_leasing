package com.yankuang.equipment.web.log;

import com.yankuang.equipment.syslog.model.SysLog;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xhh
 * @date 2018/8/9 11:55
 * 日志测试类
 */
@RestController
public class HelloController {

    public Integer id=null;
    @GetMapping( "/add")
    public String add(@RequestParam int name){

        SysLog sysLog = new SysLog();
        sysLog.setUser_id(id);
        return "add:"+name;
    }

    @GetMapping( "/hello")
    public String hello(@RequestParam int name){

        return "add:"+name;
    }

    @GetMapping( "/update")
    public String update(@RequestParam String name){

        return "update:"+name;
    }

    @GetMapping( "/delete")
    public String delete(@RequestParam String name){

        return "delete:"+name;
    }

    @GetMapping( "/find")
    public String find(@RequestParam String name){

        return "find:"+name;
    }


}
