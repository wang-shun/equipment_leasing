package com.yankuang.equipment.web.restful;

import com.yankuang.equipment.excel.model.*;
import com.yankuang.equipment.web.util.ExcelUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xhh
 * @date 2018/8/15 9:25
 * EXCEL导出测试类.
 */
@RestController
public class ReportExcelController {

    /**
     * 矿处通用机电租赁费用明细表
     * @param request
     * @param response
     * @throws IOException
     */
    @GetMapping("excelDetails")
    public void excelDetails(HttpServletRequest request, HttpServletResponse response) throws IOException {

        List<GeneralMechanicalDetails> list = new ArrayList<GeneralMechanicalDetails>();

        for (int i = 0 ; i<6 ; i++) {
            //组装测试数据
            GeneralMechanicalDetails generalMDetails = new GeneralMechanicalDetails();
            generalMDetails.setItem_position("鲍店煤矿");
            generalMDetails.setEquipment_middle_type("电动机");
            generalMDetails.setEquipment_small_type("异步电动机");
            generalMDetails.setCode("010101010101123");
            generalMDetails.setTech_code("2-0049");
            generalMDetails.setEquipment_specification("YB2315L8-4");
            generalMDetails.setItem_effect("6311工作面");
            generalMDetails.setLease_end_day("31");
            generalMDetails.setLease_price(9.9999);
            generalMDetails.setReadjust_prices(22.44);
            generalMDetails.setRemark("有借有还，再借不难");

            list.add(generalMDetails);
        }
        ExcelUtil reportExcel = new ExcelUtil();
        HSSFWorkbook wb = reportExcel.exportDetails(list);
        response.setContentType("application/vnd.ms-excel");
        response.addHeader("Content-Disposition", "attachment;filename="+ java.net.URLEncoder.encode("矿处通用机电租赁费用明细表", "UTF-8")+".xls");
        OutputStream outputStream = response.getOutputStream();
        wb.write(outputStream);
        outputStream.flush();
        outputStream.close();
    }

    /**
     * 矿处通用机电租赁费用汇总表
     * @param request
     * @param response
     * @throws IOException
     */
    @GetMapping("excelCollect")
    public void exportExcelCommon(HttpServletRequest request, HttpServletResponse response )throws IOException{

        List<GeneralMechanicalCollect> list = new ArrayList<>();
        for (int i = 0 ; i<6 ; i++) {
            //组装测试数据
            GeneralMechanicalCollect generalMechanicalCollect = new GeneralMechanicalCollect();
            generalMechanicalCollect.setEquipment_middle_type("电动机");
            generalMechanicalCollect.setEquipment_num("22");
            generalMechanicalCollect.setQuantity_used("010101010101123");
            generalMechanicalCollect.setRental_fee(123.456);
            generalMechanicalCollect.setAdjustment_expenses(12.55);
            generalMechanicalCollect.setRemark("有借有还，再借不难");

            list.add(generalMechanicalCollect);
        }

        ExcelUtil reportExcel = new ExcelUtil();
        HSSFWorkbook wb = reportExcel.exportCommon(list);
        response.setContentType("application/vnd.ms-excel");
        response.addHeader("Content-Disposition", "attachment;filename="+ java.net.URLEncoder.encode("矿处通用机电租赁费用汇总表", "UTF-8")+".xls");
        OutputStream outputStream = response.getOutputStream();
        wb.write(outputStream);
        outputStream.flush();
        outputStream.close();
    }

    /**
     * 通用设备折旧修理费确认单
     * @param request
     * @param response
     * @throws IOException
     */
    @GetMapping("excelDepreciationCost")
    public void exportExcelCommonDepreciationCost(HttpServletRequest request, HttpServletResponse response )throws IOException{

        List<CommonDepreciationCost> list = new ArrayList<>();
        for (int i = 0 ; i<6 ; i++) {
            //组装测试数据
            CommonDepreciationCost commonDepreciationCost = new CommonDepreciationCost();
            commonDepreciationCost.setMonth(i+2+"月");
            commonDepreciationCost.setQuantity_used("22");
            commonDepreciationCost.setMonthly_fee(12365.55);
            commonDepreciationCost.setAdjustment_expenses(123.456);
            commonDepreciationCost.setUsage_days("31");
            commonDepreciationCost.setRemark("有借有还，再借不难");

            list.add(commonDepreciationCost);
        }

        ExcelUtil reportExcel = new ExcelUtil();
        HSSFWorkbook wb = reportExcel.exportCommonDepreciationCost(list);
        response.setContentType("application/vnd.ms-excel");
        response.addHeader("Content-Disposition", "attachment;filename="+ java.net.URLEncoder.encode("通用设备折旧修理费确认单", "UTF-8")+".xls");
        OutputStream outputStream = response.getOutputStream();
        wb.write(outputStream);
        outputStream.flush();
        outputStream.close();
    }

    /**
     * 通用设备折旧修理费月报（跨矿）
     * @param request
     * @param response
     * @throws IOException
     */
    @GetMapping("excelDepreciationCostMonthly")
    public void excelDepreciationCostMonthly(HttpServletRequest request, HttpServletResponse response )throws IOException{

        List<CommonDepreciationCostMonthly> list = new ArrayList<>();
        for (int i = 0 ; i<6 ; i++) {
            //组装测试数据
            CommonDepreciationCostMonthly commonDepreciationCostMonthly = new CommonDepreciationCostMonthly();
            commonDepreciationCostMonthly.setEquipment_middle_type("局部扇风机");
            commonDepreciationCostMonthly.setNantun(11.11);
            commonDepreciationCostMonthly.setXinglongzhuang(22.22);
            commonDepreciationCostMonthly.setBaodian(33.33);
            commonDepreciationCostMonthly.setDongtan(44.44);
            commonDepreciationCostMonthly.setJier(55.55);
            commonDepreciationCostMonthly.setJisan(66.66);
            commonDepreciationCostMonthly.setYangcun(77.77);
            list.add(commonDepreciationCostMonthly);
        }

        ExcelUtil reportExcel = new ExcelUtil();
        HSSFWorkbook wb = reportExcel.commonDepreciationCostMonthly(list);
        response.setContentType("application/vnd.ms-excel");
        response.addHeader("Content-Disposition", "attachment;filename="+ java.net.URLEncoder.encode("通用设备折旧修理费月报（跨矿）", "UTF-8")+".xls");
        OutputStream outputStream = response.getOutputStream();
        wb.write(outputStream);
        outputStream.flush();
        outputStream.close();
    }

    /**
     * 综机折旧修理费表
     * @param request
     * @param response
     * @throws IOException
     */
    @GetMapping("comprehensiveMachineryDRCost")
    public void ComprehensiveMachineryDRCost(HttpServletRequest request, HttpServletResponse response )throws IOException{

        List<ComprehensiveMachineryDRCost> list = new ArrayList<>();
        for (int i = 0 ; i<6 ; i++) {
            //组装测试数据
            ComprehensiveMachineryDRCost comprehensiveMachineryDRCost = new ComprehensiveMachineryDRCost();
            comprehensiveMachineryDRCost.setItem_position("东滩矿");
            comprehensiveMachineryDRCost.setUsing_site("1309");
            comprehensiveMachineryDRCost.setForm_number("16-24");
            comprehensiveMachineryDRCost.setDetailed_list(1);
            comprehensiveMachineryDRCost.setDepreciation_repair_charge(354666);
            comprehensiveMachineryDRCost.setRemark("设备基本租赁费（A1）");
            list.add(comprehensiveMachineryDRCost);
        }

        ExcelUtil reportExcel = new ExcelUtil();
        HSSFWorkbook wb = reportExcel.comprehensiveMachineryDRCost(list);
        response.setContentType("application/vnd.ms-excel");
        response.addHeader("Content-Disposition", "attachment;filename="+ java.net.URLEncoder.encode("综机折旧修理费表", "UTF-8")+".xls");
        OutputStream outputStream = response.getOutputStream();
        wb.write(outputStream);
        outputStream.flush();
        outputStream.close();
    }

    /**
     * 综机设备使用清单
     * @param request
     * @param response
     * @throws IOException
     */
    @GetMapping("comprehensiveuer")
    public void ComprehensiveUsedEquipmentRecord( HttpServletRequest request, HttpServletResponse response )throws IOException{

        List<ComprehensiveUsedEquipmentRecord> list = new ArrayList<>();
        for (int i = 0 ; i<6 ; i++) {
            //组装测试数据
            ComprehensiveUsedEquipmentRecord comprehensiveUsedEquipmentRecord = new ComprehensiveUsedEquipmentRecord();
            comprehensiveUsedEquipmentRecord.setItem_position("东滩矿");
            comprehensiveUsedEquipmentRecord.setEquipment_name("掘进机");
            comprehensiveUsedEquipmentRecord.setEquipment_specification("EBZ150");
            comprehensiveUsedEquipmentRecord.setUnit("台");
            comprehensiveUsedEquipmentRecord.setEquipment_num(1);
            comprehensiveUsedEquipmentRecord.setDaily_billing_standard(1495);
            comprehensiveUsedEquipmentRecord.setCharge_days(30);
            comprehensiveUsedEquipmentRecord.setRemark("1309东轨顺");
            list.add(comprehensiveUsedEquipmentRecord);
        }

        ExcelUtil reportExcel = new ExcelUtil();
        HSSFWorkbook wb = reportExcel.comprehensiveUsedEquipmentRecord(list);
        response.setContentType("application/vnd.ms-excel");
        response.addHeader("Content-Disposition", "attachment;filename="+ java.net.URLEncoder.encode("综机设备使用清单", "UTF-8")+".xls");
        OutputStream outputStream = response.getOutputStream();
        wb.write(outputStream);
        outputStream.flush();
        outputStream.close();
    }

    /**
     * 综机设备使用交接单
     * @param request
     * @param response
     * @throws IOException
     */
    @GetMapping("comprehensiveUseInterchangeForm")
    public void ComprehensiveUseInterchangeForm( HttpServletRequest request, HttpServletResponse response )throws IOException{

        List<ComprehensiveUseInterchangeForm> list = new ArrayList<>();
        for (int i = 0 ; i<6 ; i++) {
            //组装测试数据
            ComprehensiveUseInterchangeForm comprehensiveUseInterchangeForm = new ComprehensiveUseInterchangeForm();
            comprehensiveUseInterchangeForm.setItem_position("东滩矿");
            comprehensiveUseInterchangeForm.setEquipment_name("掘进机");
            comprehensiveUseInterchangeForm.setEquipment_specification("EBZ150");
            comprehensiveUseInterchangeForm.setUse_place("工作面");
            comprehensiveUseInterchangeForm.setEquipment_num(1);
            comprehensiveUseInterchangeForm.setTech_code("78-522");
            comprehensiveUseInterchangeForm.setCompany_affiliation("1");
            comprehensiveUseInterchangeForm.setRemark("1309东轨顺");
            comprehensiveUseInterchangeForm.setWhether_new_equ("是");
            comprehensiveUseInterchangeForm.setHandover_date("2018-08-09");
            list.add(comprehensiveUseInterchangeForm);
        }

        ExcelUtil reportExcel = new ExcelUtil();
        HSSFWorkbook wb = reportExcel.comprehensiveUseInterchangeForm(list);
        response.setContentType("application/vnd.ms-excel");
        response.addHeader("Content-Disposition", "attachment;filename="+ java.net.URLEncoder.encode("综机设备使用交接单", "UTF-8")+".xls");
        OutputStream outputStream = response.getOutputStream();
        wb.write(outputStream);
        outputStream.flush();
        outputStream.close();
    }

    /**
     * 综机折旧修理费汇总表（综机折旧修理费月报（煤业）、综机折旧修理费月报（东华）、煤业综机折旧修理费、东华综机折旧修理费可用）
     * @param request
     * @param response
     * @throws IOException
     */
    @GetMapping("comprehensiveDepreciationRCostCollect")
    public void ComprehensiveDepreciationRCostCollect( HttpServletRequest request, HttpServletResponse response )throws IOException{

        List<ComprehensiveDepreciationRCostCollect> list = new ArrayList<>();
        for (int i = 0 ; i<6 ; i++) {
            //组装测试数据
            ComprehensiveDepreciationRCostCollect comprehensiveDepreciationRCostCollect = new ComprehensiveDepreciationRCostCollect();
            comprehensiveDepreciationRCostCollect.setOrg_name("南屯");
            comprehensiveDepreciationRCostCollect.setJanuary_repairs_cost(310.9626);
            comprehensiveDepreciationRCostCollect.setFebruary_repairs_cost(341.5833);
            comprehensiveDepreciationRCostCollect.setMarch_repairs_cost(880.1766);
            comprehensiveDepreciationRCostCollect.setApril_repairs_cost(1050.5463);
            comprehensiveDepreciationRCostCollect.setMay_repairs_cost(876.3456);
            comprehensiveDepreciationRCostCollect.setJune_repairs_cost(896.4751);
            comprehensiveDepreciationRCostCollect.setJuly_repairs_cost(456.1234);
            comprehensiveDepreciationRCostCollect.setAugust_repairs_cost(237.4563);
            comprehensiveDepreciationRCostCollect.setSep_repairs_cost(23.6666);
            comprehensiveDepreciationRCostCollect.setOct_repairs_cost(875.1568);
            comprehensiveDepreciationRCostCollect.setNov_repairs_cost(7563.4126);
            comprehensiveDepreciationRCostCollect.setDec_repairs_cost(7542.6677);
            list.add(comprehensiveDepreciationRCostCollect);
        }

        ExcelUtil reportExcel = new ExcelUtil();
        HSSFWorkbook wb = reportExcel.comprehensiveDepreciationRCostCollect(list);
        response.setContentType("application/vnd.ms-excel");
        response.addHeader("Content-Disposition", "attachment;filename="+ java.net.URLEncoder.encode("综机折旧修理费汇总表", "UTF-8")+".xls");
        OutputStream outputStream = response.getOutputStream();
        wb.write(outputStream);
        outputStream.flush();
        outputStream.close();
    }

    /**
     * 综机折旧修理费月报（汇总）月度明细
     * @param request
     * @param response
     * @throws IOException
     */
    @GetMapping("comprehensiveMachineryDRCostMonthly")
    public void ComprehensiveMachineryDRCostMonthly( HttpServletRequest request, HttpServletResponse response )throws IOException{

        List<ComprehensiveMachineryDRCostMonthly> list = new ArrayList<>();
        for (int i = 0 ; i<6 ; i++) {
            //组装测试数据
            ComprehensiveMachineryDRCostMonthly comprehensiveMachineryDRCostMonthly = new ComprehensiveMachineryDRCostMonthly();
            comprehensiveMachineryDRCostMonthly.setOrg_name("石拉乌素");
            comprehensiveMachineryDRCostMonthly.setCoal_annual_plan_indicator(310.9626);
            comprehensiveMachineryDRCostMonthly.setYh_annual_plan_indicator(341.5833);
            comprehensiveMachineryDRCostMonthly.setFirst_half_indicator(880.1766);
            comprehensiveMachineryDRCostMonthly.setFirst_half_hy_predictor(1050.5463);
            comprehensiveMachineryDRCostMonthly.setOnetosix_real_income(876.3456);
            comprehensiveMachineryDRCostMonthly.setOnetofive_real_income(896.4751);
            comprehensiveMachineryDRCostMonthly.setSix_real_income(456.1234);
            list.add(comprehensiveMachineryDRCostMonthly);
        }

        ExcelUtil reportExcel = new ExcelUtil();
        HSSFWorkbook wb = reportExcel.comprehensiveMachineryDRCostMonthly(list);
        response.setContentType("application/vnd.ms-excel");
        response.addHeader("Content-Disposition", "attachment;filename="+ java.net.URLEncoder.encode("综机折旧修理费月报（汇总）月度明细", "UTF-8")+".xls");
        OutputStream outputStream = response.getOutputStream();
        wb.write(outputStream);
        outputStream.flush();
        outputStream.close();
    }

    /**
     * 综机折旧修理费月报（汇总）全年汇总
     * @param request
     * @param response
     * @throws IOException
     */
    @GetMapping("comprehensiveMachineryDRCostMonthlyCollect")
    public void ComprehensiveMachineryDRCostMonthlyCollect( HttpServletRequest request, HttpServletResponse response )throws IOException{

        List<ComprehensiveMachineryDRCostMonthlyCollect> list = new ArrayList<>();
        for (int i = 0 ; i<6 ; i++) {
            //组装测试数据
            ComprehensiveMachineryDRCostMonthlyCollect comprehensiveMachineryDRCostMonthlyCollect = new ComprehensiveMachineryDRCostMonthlyCollect();
            comprehensiveMachineryDRCostMonthlyCollect.setOrg_name("兴隆");
            comprehensiveMachineryDRCostMonthlyCollect.setAnnual_plan_indicator(33310.9626);
            comprehensiveMachineryDRCostMonthlyCollect.setMonthly_plan_receivables(341.5833);
            comprehensiveMachineryDRCostMonthlyCollect.setTotal_plan_receivables(880.1766);
            comprehensiveMachineryDRCostMonthlyCollect.setJanuary_repairs_cost(1050.5463);
            comprehensiveMachineryDRCostMonthlyCollect.setFebruary_repairs_cost(876.3456);
            comprehensiveMachineryDRCostMonthlyCollect.setMarch_repairs_cost(896.4751);
            comprehensiveMachineryDRCostMonthlyCollect.setApril_repairs_cost(456.1234);
            comprehensiveMachineryDRCostMonthlyCollect.setMay_repairs_cost(456.1234);
            comprehensiveMachineryDRCostMonthlyCollect.setJune_repairs_cost(45.1234);
            comprehensiveMachineryDRCostMonthlyCollect.setJuly_repairs_cost(46.7234);
            comprehensiveMachineryDRCostMonthlyCollect.setAugust_repairs_cost(56.6234);
            comprehensiveMachineryDRCostMonthlyCollect.setSep_repairs_cost(4564.9234);
            comprehensiveMachineryDRCostMonthlyCollect.setOct_repairs_cost(463.8234);
            comprehensiveMachineryDRCostMonthlyCollect.setNov_repairs_cost(4562.1238);
            comprehensiveMachineryDRCostMonthlyCollect.setDec_repairs_cost(4256.1823);
            list.add(comprehensiveMachineryDRCostMonthlyCollect);
        }

        ExcelUtil reportExcel = new ExcelUtil();
        HSSFWorkbook wb = reportExcel.comprehensiveMachineryDRCostMonthlyCollect(list);
        response.setContentType("application/vnd.ms-excel");
        response.addHeader("Content-Disposition", "attachment;filename="+ java.net.URLEncoder.encode("综机折旧修理费月报（汇总）全年汇总", "UTF-8")+".xls");
        OutputStream outputStream = response.getOutputStream();
        wb.write(outputStream);
        outputStream.flush();
        outputStream.close();
    }

}
