package com.yankuang.equipment.web.restful;

import com.alibaba.fastjson.JSON;
import com.yankuang.equipment.common.util.CommonResponse;
import com.yankuang.equipment.common.util.JsonUtils;
import com.yankuang.equipment.equipment.model.ElSurfaceParam;
import com.yankuang.equipment.equipment.service.ElSurfaceParamService;
import com.yankuang.equipment.web.dto.UserDTO;
import com.yankuang.equipment.web.util.UserFromRedis;
import io.terminus.boot.rpc.common.annotation.RpcConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Auther: zyy
 * @Date: 2018-10-11 16:53
 * @Description:
 */
@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/v1/elSurfaceParam")
public class ElSurfaceParamController {

    @RpcConsumer
    ElSurfaceParamService elSurfaceParamService;
    @Autowired
    UserFromRedis userFromRedis;

    /**
     * 添加工作面参数
     * @param jsonString
     * @return
     */
    @PostMapping
    public CommonResponse create(@RequestBody String jsonString) {

        try {
            if (StringUtils.isEmpty(jsonString)) return CommonResponse.errorMsg("参数不得为空");
            ElSurfaceParam surfaceParam = JsonUtils.jsonToPojo(jsonString, ElSurfaceParam.class);
            if (surfaceParam == null) return CommonResponse.errorMsg("参数不得为空");
            if (StringUtils.isEmpty(surfaceParam.getSbPositionCode())) return CommonResponse.errorMsg("工作面不得为空");
            ElSurfaceParam surfaceParams = new ElSurfaceParam();
            surfaceParams.setSbPositionCode(surfaceParam.getSbPositionCode());
            List<ElSurfaceParam> elSurfaceParams = elSurfaceParamService.findByCondition(surfaceParams);
            if (elSurfaceParams != null && elSurfaceParams.size() > 0) return CommonResponse.errorMsg("该工作面参数已配置，如需改动请使用编辑功能");
            // 获取当前登录用户信息
            UserDTO userDTO = userFromRedis.findByToken();
            if (userDTO != null) {
                surfaceParam.setCreateBy(userDTO.getCode());
                surfaceParam.setUpdateBy(userDTO.getCode());
            }

            boolean res = elSurfaceParamService.create(surfaceParam);
            if (!res) return CommonResponse.errorMsg("添加失败");

            return CommonResponse.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResponse.errorException("服务异常："+ JSON.toJSONString(e));
        }
    }

    /**
     * 查询工作面参数
     * @param positionCode
     * @return
     */
    @GetMapping
    public CommonResponse findByCondition(@RequestParam(defaultValue = "") String positionCode) {

        try {
            ElSurfaceParam surfaceParam = new ElSurfaceParam();
            surfaceParam.setSbPositionCode(positionCode);
            List<ElSurfaceParam> elSurfaceParams = elSurfaceParamService.findByCondition(surfaceParam);
            if (elSurfaceParams == null || elSurfaceParams.size() <= 0) return CommonResponse.build(200, "查询为空", null);
            return CommonResponse.ok(elSurfaceParams);
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResponse.errorException("服务异常："+JSON.toJSONString(e));
        }
    }

    /**
     * 主键查询
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public CommonResponse findById(@PathVariable("id") Long id) {
        try {
            if (id == null) return CommonResponse.errorMsg("参数不得为空");
            ElSurfaceParam param = elSurfaceParamService.findById(id);
            if (param == null) return CommonResponse.build(200, "查询为空", null);
            return CommonResponse.ok(param);
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResponse.errorException("服务异常："+JSON.toJSONString(e));
        }
    }

    /**
     * 删除工作面参数
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public CommonResponse delete(@PathVariable("id") Long id) {
        try {
            if (id == null) return CommonResponse.errorMsg("id不得为空");
            boolean res = elSurfaceParamService.delete(id);
            if (!res) return CommonResponse.errorMsg("删除失败");
            return CommonResponse.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResponse.errorException("服务异常："+JSON.toJSONString(e));
        }
    }

    /**
     * 编辑更新工作面参数
     * @param jsonString
     * @return
     */
    @PutMapping
    public CommonResponse update(@RequestBody String jsonString) {
        try {
            if (StringUtils.isEmpty(jsonString)) return CommonResponse.errorMsg("参数不得为空");
            ElSurfaceParam param = JsonUtils.jsonToPojo(jsonString, ElSurfaceParam.class);
            if (param == null) return CommonResponse.errorMsg("参数不得为空");
            if (param.getId() == null) return CommonResponse.errorMsg("主键ID不得为空");
            boolean res = elSurfaceParamService.update(param);
            if (!res) return CommonResponse.errorMsg("更新失败");
            return CommonResponse.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResponse.errorException("服务异常："+JSON.toJSONString(e));
        }
    }
}
