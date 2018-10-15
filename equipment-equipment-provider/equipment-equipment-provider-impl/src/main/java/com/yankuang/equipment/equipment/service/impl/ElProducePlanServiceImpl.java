package com.yankuang.equipment.equipment.service.impl;

import com.yankuang.equipment.common.util.UuidUtils;
import com.yankuang.equipment.equipment.mapper.ElEquipmentGroupMapper;
import com.yankuang.equipment.equipment.mapper.ElProducePlanMapper;
import com.yankuang.equipment.equipment.mapper.ElProduceSurfaceMapper;
import com.yankuang.equipment.equipment.mapper.ElSurfaceParamMapper;
import com.yankuang.equipment.equipment.model.ElEquipmentGroup;
import com.yankuang.equipment.equipment.model.ElProducePlan;
import com.yankuang.equipment.equipment.model.ElProduceSurface;
import com.yankuang.equipment.equipment.model.ElSurfaceParam;
import com.yankuang.equipment.equipment.service.ElProducePlanService;
import io.terminus.boot.rpc.common.annotation.RpcProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.*;

/**
 * @Auther: zyy
 * @Date: 2018-10-08 11:39
 * @Description:
 */
@Service
@RpcProvider
@Transactional
public class ElProducePlanServiceImpl implements ElProducePlanService {

    @Autowired
    ElProducePlanMapper elProducePlanMapper;
    @Autowired
    ElProduceSurfaceMapper elProduceSurfaceMapper;
    @Autowired
    ElEquipmentGroupMapper elEquipmentGroupMapper;
    @Autowired
    ElSurfaceParamMapper elSurfaceParamMapper;

    public boolean create(ElProducePlan elProducePlan) {

        try {
            boolean flag = true;

            // 添加生产接续计划数据
            elProducePlan.setCode(UuidUtils.newUuid());
            // 接续计划添加结果
            int planRes = Integer.parseInt(elProducePlanMapper.create(elProducePlan)+"");
            if (planRes <= 0) {
                flag = false;
                return flag;
            }

            // 添加生产工作面数据
            List<ElProduceSurface> surfaces = elProducePlan.getElProduceSurfaces();
            if (surfaces == null || surfaces.size() <= 0) return planRes > 0;
            for (ElProduceSurface surface : surfaces) {
                if (surface == null) continue;
                surface.setPlanId(elProducePlan.getId());
                surface.setPlanCode(elProducePlan.getCode());
                surface.setCode(UuidUtils.newUuid());
                surface.setCreateBy(elProducePlan.getCreateBy());
                surface.setUpdateBy(elProducePlan.getUpdateBy());
                // 生产工作面添加结果
                int surfaceRes = Integer.parseInt(elProduceSurfaceMapper.create(surface)+"");
                if (surfaceRes <= 0) {
                    flag = false;
                    break;
                }
                List<ElEquipmentGroup> groups = surface.getElEquipmentGroups();
                if (groups == null || groups.size() <= 0) continue;

                // 添加设备组数据
                for (ElEquipmentGroup group : groups) {
                    if (group == null) continue;
                    group.setCode(UuidUtils.newUuid());
                    group.setPlanCode(elProducePlan.getCode());
                    group.setPlanId(elProducePlan.getId());
                    group.setSurfaceCode(surface.getCode());
                    group.setSurfaceId(surface.getId());
                    group.setCreateBy(elProducePlan.getCreateBy());
                    group.setUpdateBy(elProducePlan.getUpdateBy());
                    // 设备组添加结果
                    int groupRes = Integer.parseInt(elEquipmentGroupMapper.create(group)+"");
                    if (groupRes <= 0) {
                        flag = false;
                        break;
                    }
                }
            }
            if (!flag) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return flag;
            }

            return flag;
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
    }

    public boolean update(ElProducePlan elProducePlan) {
        return false;
    }

    public List<Map<String,Object>> findByCondition(ElProducePlan elProducePlan) {

        // 校验生产接续计划
        Map<String, Object> res = new HashMap<String, Object>();
        if (elProducePlan == null) return null;
        List<ElProducePlan> elProducePlans = elProducePlanMapper.list(elProducePlan);
        if (elProducePlans == null || elProducePlans.size() <= 0) return null;
        for (ElProducePlan plan : elProducePlans) {
            if (plan == null) continue;
            List<ElProduceSurface> surfaces = elProduceSurfaceMapper.findByPlanCode(plan.getCode());
            for (ElProduceSurface surface : surfaces) {
                if (surface == null) continue;
                List<ElEquipmentGroup> groups = elEquipmentGroupMapper.findBySurfaceCode(surface.getCode());
                surface.setElEquipmentGroups(groups);
            }
            plan.setElProduceSurfaces(surfaces);
        }

        List<Map<String,Object>> mapList = new ArrayList<Map<String, Object>>();
        for (ElProducePlan producePlan : elProducePlans) {
            Map<String,Object> map = new HashMap<String, Object>();
            if (producePlan == null) continue;
            List<ElProduceSurface> produceSurfaces = producePlan.getElProduceSurfaces();
//            // 校验生产工作面
//            if (produceSurfaces == null || produceSurfaces.size() <= 0) {
//                res.put("msg", producePlan.getPlanPositionName()+"所在位置工作面数据为空");
//                res.put("data", null);
//                return res;
//            }
//            for (ElProduceSurface produceSurface : produceSurfaces) {
//                // 校验配套设备组
//                if (produceSurface == null) continue;
//                List<ElEquipmentGroup> equipmentGroups = produceSurface.getElEquipmentGroups();
//                if (equipmentGroups == null || equipmentGroups.size() <= 0) {
//                    res.put("msg", produceSurface.getEffectName()+"所在工作面设备组数据为空");
//                    res.put("data", null);
//                    return res;
//                }
//            }

            // 填充数据
            map.put("id", producePlan.getId());
            // 矿单位code
            map.put("positionCode", producePlan.getPlanPosition());
            // 矿单位名称
            map.put("positionName", producePlan.getPlanPositionName());
            List<Map<String, Object>> partitionMaps = new ArrayList<Map<String, Object>>();
            if (produceSurfaces != null && produceSurfaces.size() > 0) {
                // 对工作面数据按照矿单位分区分组
                Map<String, List<ElProduceSurface>> surfaceGroupMap = getSurfaceGroupMap(produceSurfaces);
                for (String partitionName : surfaceGroupMap.keySet()) {
                    Map<String, Object> partitionMap = new HashMap<String, Object>();
                    if (surfaceGroupMap.get(partitionName) == null || surfaceGroupMap.get(partitionName).size() <= 0) continue;
                    // 矿单位分区名称
                    partitionMap.put("partitionName", partitionName);
                    // 矿单位分区code
                    String positionCode = surfaceGroupMap.get(partitionName).get(0) == null ? null:surfaceGroupMap.get(partitionName).get(0).getPositionCode();
                    partitionMap.put("partitionCode", positionCode);
                    List<Map<String, Object>> surfaceMaps = new ArrayList<Map<String, Object>>();
                    for (ElProduceSurface surfacei : surfaceGroupMap.get(partitionName)) {
                        Map<String, Object> surfaceMap = new HashMap<String, Object>();
                        if (surfacei == null) continue;
                        // 工作面code
                        surfaceMap.put("surfaceCode", surfacei.getEffectCode());
                        // 工作面名称
                        surfaceMap.put("surfaceName", surfacei.getEffectName());
                        // 工作面参数
                        List<ElSurfaceParam> paramsI = elSurfaceParamMapper.findByPositionCode(surfacei.getEffectCode());
                        ElSurfaceParam param = null;
                        if (paramsI != null && paramsI.size() > 0) param = paramsI.get(0);
                        if (param != null) {
                            // 面长
                            surfaceMap.put("surfaceLength",param.getSurfaceLength());
                            // 采高
                            surfaceMap.put("surfaceHigh",param.getSurfaceHigh());
                            // 走向
                            surfaceMap.put("surfaceTrend",param.getSurfaceTrend());
                            // 储量
                            surfaceMap.put("surfaceReserve",param.getSurfaceReserves());
                        } else {
                            // 面长
                            surfaceMap.put("surfaceLength",null);
                            // 采高
                            surfaceMap.put("surfaceHigh",null);
                            // 走向
                            surfaceMap.put("surfaceTrend",null);
                            // 储量
                            surfaceMap.put("surfaceReserve",null);
                        }
                        // 准备工期
                        String readyTimeStr = surfacei.getReadyTime();
                        List<String> readyTime = Arrays.asList(readyTimeStr.split(","));
                        List<Map<String, Object>> readyTimeMaps = getTimeMaps(readyTime);
                        surfaceMap.put("readyTime", readyTimeMaps);
                        // 生产工期
                        String produceTimeStr = surfacei.getProduceTime();
                        List<String> produceTime = Arrays.asList(produceTimeStr.split(","));
                        List<Map<String, Object>> produceTimeMaps = getTimeMaps(produceTime);
                        surfaceMap.put("produceTime", produceTimeMaps);
                        // 撤回工期
                        String revokeTimeStr = surfacei.getRevokeTime();
                        List<String> revokeTime = Arrays.asList(revokeTimeStr.split(","));
                        List<Map<String, Object>> revokeTimeMaps = getTimeMaps(revokeTime);
                        surfaceMap.put("revokeTime", revokeTimeMaps);
                        // 封面工期
                        String bankTimeStr = surfacei.getBankTime();
                        List<String> bankTime = Arrays.asList(bankTimeStr.split(","));
                        List<Map<String, Object>> bankTimeMaps = getTimeMaps(bankTime);
                        surfaceMap.put("bankTime", bankTimeMaps);
                        // 配套设备组
                        List<ElEquipmentGroup> equipmentGroups = surfacei.getElEquipmentGroups();
                        List<Map<String, Object>> equipmentGroupMaps = new ArrayList<Map<String, Object>>();
                        if (equipmentGroups != null && equipmentGroups.size() > 0) {
                            // 将设备按照设备类型分组
                            Map<String, List<ElEquipmentGroup>> equipmentByType = getEquipmentGroupMap(equipmentGroups);
                            for (String typeName : equipmentByType.keySet()) {
                                Map<String, Object> equipmentGroupMap = new HashMap<String, Object>();
                                if (equipmentByType.get(typeName) == null || equipmentByType.get(typeName).size() <= 0) continue;
                                // 设备类型
                                equipmentGroupMap.put("sbTypeName", typeName);
                                String sbTypeCode = equipmentByType.get(typeName).get(0) == null ? null:equipmentByType.get(typeName).get(0).getSbTypeCode();
                                equipmentGroupMap.put("sbTypeCode", sbTypeCode);

                                Map<String, Object> sbModelMap = new HashMap<String, Object>();
                                List<Map<String, Object>> sbModelMaps = new ArrayList<Map<String, Object>>();
                                for (ElEquipmentGroup group : equipmentByType.get(typeName)) {
                                    // 设备规格
                                    sbModelMap.put("sbModelName", group.getSbModelName());
                                    sbModelMap.put("sbModelCode", group.getSbModelCode());
                                    // 设备参数名称
                                    sbModelMap.put("paramName", group.getParamName());
                                    // 设备参数值
                                    sbModelMap.put("paramValue", group.getParamValue());
                                    // 设备数量
                                    sbModelMap.put("sbNum", group.getSbNum());
                                    // 计量单位
                                    sbModelMap.put("sbUnit", group.getSbUnit());
                                    // 备注
                                    sbModelMap.put("remarks", group.getRemarks());

                                    sbModelMaps.add(sbModelMap);
                                }
                                // 设备规格 数量 单位
                                equipmentGroupMap.put("sbModelMap",sbModelMaps);
                                equipmentGroupMaps.add(equipmentGroupMap);
                            }
                        }

                        // 设备来源
                        surfaceMap.put("sbSource", surfacei.getSbSource());
                        // 设备组数据
                        surfaceMap.put("sbGroupMap",equipmentGroupMaps);
                        surfaceMaps.add(surfaceMap);
                    }
                    // 工作面数据
                    partitionMap.put("surfaceMap",surfaceMaps);
                    partitionMaps.add(partitionMap);
                }
            }

            // 分区数据
            map.put("partitionMap",partitionMaps);
            mapList.add(map);
        }
        if (mapList == null || mapList.size() <= 0) return null;
        return mapList;
    }

    public boolean delete(Long id) {
        return elProducePlanMapper.delete(id) > 0;
    }

    public ElProducePlan findById(Long id) {
        ElProducePlan plan = elProducePlanMapper.findById(id);
        if (plan == null) return null;
        List<ElProduceSurface> surfaces = elProduceSurfaceMapper.findByPlanCode(plan.getCode());
        for (ElProduceSurface surface : surfaces) {
            if (surface == null) continue;
            List<ElEquipmentGroup> groups = elEquipmentGroupMapper.findBySurfaceCode(surface.getCode());
            surface.setElEquipmentGroups(groups);
        }
        plan.setElProduceSurfaces(surfaces);
        return plan;
    }

    public List<ElEquipmentGroup> findBySurfaceCode(String configYear, String effectCode) {
        ElProducePlan plan = new ElProducePlan();
        plan.setPlanYear(configYear);
        plan.setAssetCode("1180");
        List<ElProducePlan> plans = elProducePlanMapper.list(plan);
        if (plans == null || plans.size() <= 0) return null;
        List<ElEquipmentGroup> groupList = new ArrayList<ElEquipmentGroup>();
        for (ElProducePlan planI:plans) {
            if (planI == null) continue;
            ElProduceSurface produceSurface = new ElProduceSurface();
            produceSurface.setEffectCode(effectCode);
            produceSurface.setPlanCode(planI.getCode());
            List<ElProduceSurface> surfaces = elProduceSurfaceMapper.findByCondition(produceSurface);
            if (surfaces == null || surfaces.size() <= 0) continue;
            for (ElProduceSurface surfaceI:surfaces) {
                if (surfaceI == null) continue;
                List<ElEquipmentGroup> groups = elEquipmentGroupMapper.findBySurfaceCode(surfaceI.getCode());
                if (groups != null && groups.size() > 0) groupList.addAll(groups);
            }
        }
        return groupList;
    }

    public Map<String, List<ElProduceSurface>> getSurfaceGroupMap(List<ElProduceSurface> produceSurfaces) {
        Map<String, List<ElProduceSurface>> surfaceGroupMap = new HashMap<String, List<ElProduceSurface>>();
        for (ElProduceSurface surface : produceSurfaces) {
            if (surface == null) continue;
            List<ElProduceSurface> tempList = surfaceGroupMap.get(surface.getPartitionName());
            if (tempList == null) {
                tempList = new ArrayList<ElProduceSurface>();
                tempList.add(surface);
                surfaceGroupMap.put(surface.getPartitionName(), tempList);
            } else {
                tempList.add(surface);
            }
        }
        return surfaceGroupMap;
    }

    public Map<String, List<ElEquipmentGroup>> getEquipmentGroupMap(List<ElEquipmentGroup> equipmentGroups) {
        Map<String, List<ElEquipmentGroup>> equipmentGroupMap = new HashMap<String, List<ElEquipmentGroup>>();
        for (ElEquipmentGroup equipmentGroup : equipmentGroups) {
            if (equipmentGroup == null) continue;
            List<ElEquipmentGroup> tempList = equipmentGroupMap.get(equipmentGroup.getSbTypeName());
            if (tempList == null) {
                tempList = new ArrayList<ElEquipmentGroup>();
                tempList.add(equipmentGroup);
                equipmentGroupMap.put(equipmentGroup.getSbTypeName(), tempList);
            } else {
                tempList.add(equipmentGroup);
            }
        }
        return equipmentGroupMap;
    }

    public List<Map<String, Object>> getTimeMaps(List<String> timeList) {
        List<Map<String, Object>> timeMaps = new ArrayList<Map<String, Object>>();
        if (timeList != null && timeList.size() > 0) {
            for (String readyT : timeList) {
                Map<String, Object> timeMap = new HashMap<String, Object>();
                if (readyT == null) continue;
                String startTime = readyT.split("\\|")[0];
                String endTime = readyT.split("\\|")[1];
                timeMap.put("startTime", startTime);
                timeMap.put("endTime", endTime);
                timeMaps.add(timeMap);
            }
        }
        return timeMaps;
    }
}
