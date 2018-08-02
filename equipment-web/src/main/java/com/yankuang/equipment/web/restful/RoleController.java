package com.yankuang.equipment.web.restful;

import com.yankuang.equipment.authority.service.RolService;
import com.yankuang.equipment.web.util.CommonResponse;
import io.terminus.boot.rpc.common.annotation.RpcConsumer;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/v1/rols")
public class RoleController {
    @RpcConsumer
    private RolService rolService;

    @GetMapping(value = "/{id}")
    CommonResponse getById(@PathVariable Long id) {
        return CommonResponse.ok(rolService.getById(id));
    }
}
