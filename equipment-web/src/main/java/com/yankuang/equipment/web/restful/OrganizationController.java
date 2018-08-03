package com.yankuang.equipment.web.restful;


import com.yankuang.equipment.authority.service.OrganizationService;
import com.yankuang.equipment.authority.util.CommonResponse;
import io.terminus.boot.rpc.common.annotation.RpcConsumer;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/v1/orgs")
public class OrganizationController {

    @RpcConsumer
    private OrganizationService organizationService;

    @GetMapping(value = "/{id}")
    CommonResponse getById(@PathVariable Long id) {
        return CommonResponse.ok(organizationService.getById(id));
    }
}
