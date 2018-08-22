package com.yankuang.equipment.web.util;

import com.yankuang.equipment.excel.model.*;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author xhh
 * 2018/8/15 9:07
 * excel工具类
 */
public class ExcelUtil {

    private static final String UNIT = "万千佰拾亿千佰拾万千佰拾元角分";
    private static final String DIGIT = "零壹贰叁肆伍陆柒捌玖";
    private static final double MAX_VALUE = 9999999999999.99D;

    /**
     * 功能 :获取表单导出数据
     *  矿处通用机电租赁费用明细表
     */
    public HSSFWorkbook exportDetails(List<GeneralMechanicalDetails> list) {

        //第一行表头字段，合并单元格时字段跨几列就将该字段重复几次
        String[] excelHeader0 = {  "矿处通用机电租赁费用明细表", "", "", "", "", "","","","","","",""};
        String[] headnum0 = { "0,0,0,11", "0,0,0,11","0,0,0,11","0,0,0,11", "0,0,0,11","0,0,0,11","0,0,0,11","0,0,0,11","0,0,0,11","0,0,0,11","0,0,0,11","0,0,0,11"};

        //第二行表头字段
        String[] excelHeader1 = {  "矿处单位", "设备中类", "设备小类", "设备识别码", "技术标识号", "规格型号","功能位置","租赁结束天数","租赁价格","调整金额","合计","备注"};
        String[] headnum1 = { "1,1,0,0", "1,1,1,1", "1,1,2,2", "1,1,3,3", "1,1,4,4", "1,1,5,5", "1,1,6,6", "1,1,7,7", "1,1,8,8", "1,1,9,9", "1,1,10,10", "1,1,11,11"};

        // 声明一个工作簿
        HSSFWorkbook wb = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = wb.createSheet("矿处通用机电租赁费用明细表");

        // 生成一种样式
        HSSFCellStyle style = wb.createCellStyle();
        // 设置样式
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);//下边框
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        // 生成一种字体
        HSSFFont font = wb.createFont();
        // 设置字体
        font.setFontName("微软雅黑");
        // 设置字体大小
        font.setFontHeightInPoints((short) 12);
        // 字体加粗
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        // 在样式中引用这种字体
        style.setFont(font);

        // 生成并设置另一个样式
        HSSFCellStyle style2 = wb.createCellStyle();
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        // 生成另一种字体2
        HSSFFont font2 = wb.createFont();
        // 设置字体
        font2.setFontName("微软雅黑");
        // 设置字体大小
        font2.setFontHeightInPoints((short) 12);
        // 在样式2中引用这种字体
        style.setFont(font2);

        // 第一行表头
        HSSFRow row = sheet.createRow(0);
        row.setHeightInPoints(30);//设置行高

        this.first_column(row,sheet,style,excelHeader0,headnum0);

        // 第二行表头
        row = sheet.createRow(1);
        row.setHeightInPoints(20);//设置行高
        this.first_column(row,sheet,style,excelHeader1,headnum1);

        // 第四行数据
        for (int i = 0; i < list.size(); i++) {

            row = sheet.createRow(i + 2);
            row.setHeightInPoints(20);//设置行高
            GeneralMechanicalDetails report = list.get(i);

            // 导入对应列的数据
            HSSFCell cell = row.createCell(0);
            cell.setCellValue(report.getItem_position());
            cell.setCellStyle(style2);

            HSSFCell cell1 = row.createCell(1);
            cell1.setCellValue(report.getEquipment_middle_type());
            cell1.setCellStyle(style2);

            HSSFCell cell2 = row.createCell(2);
            cell2.setCellValue(report.getEquipment_small_type());
            cell2.setCellStyle(style2);

            HSSFCell cell3 = row.createCell(3);
            cell3.setCellValue(report.getCode());
            cell3.setCellStyle(style2);

            HSSFCell cell4 = row.createCell(4);
            cell4.setCellValue(report.getTech_code());
            cell4.setCellStyle(style2);

            HSSFCell cell5 = row.createCell(5);
            cell5.setCellValue(report.getEquipment_specification());
            cell5.setCellStyle(style2);

            HSSFCell cell6 = row.createCell(6);
            cell6.setCellValue(report.getItem_effect());
            cell6.setCellStyle(style2);

            HSSFCell cell7 = row.createCell(7);
            cell7.setCellValue(report.getLease_end_day());
            cell7.setCellStyle(style2);

            HSSFCell cell8 = row.createCell(8);
            cell8.setCellValue(report.getLease_price());
            cell8.setCellStyle(style2);

            HSSFCell cell9 = row.createCell(9);
            cell9.setCellValue(report.getReadjust_prices());
            cell9.setCellStyle(style2);


            BigDecimal b1 = new BigDecimal(Double.toString(report.getLease_price()));
            BigDecimal b2 = new BigDecimal(report.getLease_end_day());
            BigDecimal b3 = new BigDecimal(report.getReadjust_prices());

            double total = b1.multiply(b2).add(b3).doubleValue();

            HSSFCell cell10 = row.createCell(10);

            cell10.setCellValue(total);
            cell10.setCellStyle(style2);

            HSSFCell cell11 = row.createCell(11);
            cell11.setCellValue(report.getRemark());
            cell11.setCellStyle(style2);

        }

        return wb;
    }


    /**
     *  功能 :获取表单导出数据
     *  矿处通用机电租赁费用汇总表
     */
    public HSSFWorkbook exportCommon(List<GeneralMechanicalCollect> list) {

        // 声明String数组，并初始化元素（表头名称）
        //第一行表头字段，合并单元格时字段跨几列就将该字段重复几次
        String[] excelHeader0 = {  "矿处通用机电租赁费用汇总表", "", "", "", "", "",""};
        String[] headnum0 = { "0,0,0,6", "0,0,0,6","0,0,0,6","0,0,0,6", "0,0,0,6",};

        //第二行表头字段
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH )+1;
        String[] excelHeader1 = {  "矿处单位:", "", "", "", "", year + " 年 " + month + " 月",""};
        String[] headnum1 = { "1,1,0,1", "1,1,2,2","1,1,3,3","1,1,4,4", "1,1,5,6"};

        //第三行表头字段
        String[] excelHeader2 = {  "设备名称（中类）", "在籍数量", "在用数量", "租赁费", "费用调整", "合  计","备   注"};
        String[] headnum2 = { "2,2,0,0", "2,2,1,1", "2,2,2,2", "2,2,3,3", "2,2,4,4", "2,2,5,5", "2,2,6,6"};

        //最后一行
        String[] excelHeader3 = {  "制表:", "", "审核：", "", "", "矿方确认签字:",""};
        int a = list.size();
        int b = a+3;
        String[] headnum3 = { b+","+b+",0,1", b+","+b+",2,2", b+","+b+",3,3", b+","+b+",4,4", b+","+b+",5,6"};

        // 声明一个工作簿
        HSSFWorkbook wb = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = wb.createSheet("矿处通用机电租赁费用汇总表");

        // 生成一种样式
        HSSFCellStyle style = wb.createCellStyle();
        // 设置样式
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);//下边框
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        // 生成一种字体
        HSSFFont font = wb.createFont();
        // 设置字体
        font.setFontName("微软雅黑");
        // 设置字体大小
        font.setFontHeightInPoints((short) 12);
        // 字体加粗
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        // 在样式中引用这种字体
        style.setFont(font);

        // 生成并设置另一个样式
        HSSFCellStyle style2 = wb.createCellStyle();
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        // 生成并设置另一个样式
        HSSFCellStyle style3 = wb.createCellStyle();
        style3.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style3.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style3.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style3.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style3.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        style3.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        // 第一行表头
        HSSFRow row = sheet.createRow(0);
        row.setHeightInPoints(30);//设置行高
        this.first_column(row,sheet,style,excelHeader0,headnum0);

        // 第二行表头
        row = sheet.createRow(1);
        row.setHeightInPoints(30);//设置行高
        this.first_column(row,sheet,style3,excelHeader1,headnum1);

        // 第三行表头
        row = sheet.createRow(2);
        row.setHeightInPoints(30);//设置行高
        this.first_column(row,sheet,style,excelHeader2,headnum2);

        // 第四行数据
        for (int i = 0; i < list.size(); i++) {

            row = sheet.createRow(i + 3);
            row.setHeightInPoints(30);//设置行高
            GeneralMechanicalCollect report = list.get(i);

            // 导入对应列的数据
            HSSFCell cell = row.createCell(0);
            cell.setCellValue(report.getEquipment_middle_type());
            cell.setCellStyle(style2);

            HSSFCell cell1 = row.createCell(1);
            cell1.setCellValue(report.getEquipment_num());
            cell1.setCellStyle(style2);

            HSSFCell cell2 = row.createCell(2);
            cell2.setCellValue(report.getQuantity_used());
            cell2.setCellStyle(style2);

            HSSFCell cell3 = row.createCell(3);
            cell3.setCellValue(report.getRental_fee());
            cell3.setCellStyle(style2);

            HSSFCell cell4 = row.createCell(4);
            cell4.setCellValue(report.getAdjustment_expenses());
            cell4.setCellStyle(style2);

            BigDecimal b1 = new BigDecimal(Double.toString(report.getRental_fee()));
            BigDecimal b2 = new BigDecimal(Double.toString(report.getAdjustment_expenses()));

            double total = b1.add(b2).doubleValue();

            HSSFCell cell5 = row.createCell(5);
            cell5.setCellValue(total);
            cell5.setCellStyle(style2);

            HSSFCell cell6 = row.createCell(6);
            cell6.setCellValue(report.getRemark());
            cell6.setCellStyle(style2);

        }

        // 最后一行
        row = sheet.createRow(list.size()+3);
        row.setHeightInPoints(30);//设置行高
        this.last_column(list,row,sheet,style3,excelHeader3,headnum3);
        return wb;
    }

    /**
     * 功能 :获取表单导出数据
     * 表名：通用设备折旧修理费确认单
     */
    public HSSFWorkbook exportCommonDepreciationCost(List<CommonDepreciationCost> list) {

        //第一行表头字段，合并单元格时字段跨几列就将该字段重复几次
        String[] excelHeader0 = {  "兖州煤业股份有限公司", "", "", "", "", "","",};
        String[] headnum0 = { "0,0,0,6", "0,0,0,6","0,0,0,6","0,0,0,6", "0,0,0,6",};

        //第二行表头字段
        String[] excelHeader1 = {  "通用设备折旧修理费确认单", "", "", "", "", "",""};
        String[] headnum1 = { "1,1,0,6", "1,1,0,6","1,1,0,6","1,1,0,6", "1,1,0,6"};

        //第三行表头字段
        String[] excelHeader2 = {  "使用单位:", "", "", "", "", "日期：2018年___月___日",""};
        String[] headnum2 = { "2,2,0,1", "2,2,2,2","2,2,3,3","2,2,4,4", "2,2,5,6"};

        //第四行表头字段
        String[] excelHeader3 = {  "月份", "在用数量", "月度费用", "费用调整", "月度合计", "使用天数","备   注"};
        String[] headnum3 = { "3,3,0,0", "3,3,1,1", "3,3,2,2", "3,3,3,3", "3,3,4,4", "3,3,5,5", "3,3,6,6"};

        //最后一行
        String[] excelHeader5 = {  "中心主管:", "", "设备使用方机电矿长：","中心科室负责人：",  "", "中心经办人:",""};
        int c = list.size();
        int d = c+5;
        String[] headnum5 = { d+","+d+",0,1", d+","+d+",2,2", d+","+d+",3,3", d+","+d+",4,4", d+","+d+",5,6"};

        // 声明一个工作簿
        HSSFWorkbook wb = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = wb.createSheet("通用设备折旧修理费确认单");

        // 生成一种样式
        HSSFCellStyle style = wb.createCellStyle();
        // 设置样式
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);//下边框
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        // 生成一种字体
        HSSFFont font = wb.createFont();
        // 设置字体
        font.setFontName("微软雅黑");
        // 设置字体大小
        font.setFontHeightInPoints((short) 12);
        // 字体加粗
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        // 在样式中引用这种字体
        style.setFont(font);

        // 生成并设置另一个样式
        HSSFCellStyle style2 = wb.createCellStyle();
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        // 生成并设置另一个样式
        HSSFCellStyle style3 = wb.createCellStyle();
        style3.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style3.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style3.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style3.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style3.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        style3.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        // 第一行表头
        HSSFRow row = sheet.createRow(0);
        row.setHeightInPoints(30);//设置行高
        this.first_column(row,sheet,style,excelHeader0,headnum0);

        // 第二行表头
        row = sheet.createRow(1);
        row.setHeightInPoints(30);//设置行高
        this.first_column(row,sheet,style,excelHeader1,headnum1);
        // 第三行表头
        row = sheet.createRow(2);
        row.setHeightInPoints(30);//设置行高
        this.first_column(row,sheet,style,excelHeader2,headnum2);
        // 第三行表头
        row = sheet.createRow(3);
        row.setHeightInPoints(30);//设置行高
        this.first_column(row,sheet,style2,excelHeader3,headnum3);

        // 第四行数据
        BigDecimal monthTotal = new BigDecimal(0);
        Double totalSubTotal = new Double(0);
        for (int i = 0; i < list.size(); i++) {

            row = sheet.createRow(i + 4);
            row.setHeightInPoints(30);//设置行高
            CommonDepreciationCost report = list.get(i);

            // 导入对应列的数据
            HSSFCell cell = row.createCell(0);
            cell.setCellValue(report.getMonth());
            cell.setCellStyle(style2);

            HSSFCell cell1 = row.createCell(1);
            cell1.setCellValue(report.getQuantity_used());
            cell1.setCellStyle(style2);

            HSSFCell cell2 = row.createCell(2);
            cell2.setCellValue(report.getMonthly_fee());
            cell2.setCellStyle(style2);

            HSSFCell cell3 = row.createCell(3);
            cell3.setCellValue(report.getAdjustment_expenses());
            cell3.setCellStyle(style2);

            HSSFCell cell5 = row.createCell(5);
            cell5.setCellValue(report.getUsage_days());
            cell5.setCellStyle(style2);

            HSSFCell cell6 = row.createCell(6);
            cell6.setCellValue(report.getRemark());
            cell6.setCellStyle(style2);


            HSSFCell cell4 = row.createCell(4);

            BigDecimal b1 = new BigDecimal(Double.toString(report.getMonthly_fee()));
            BigDecimal b2 = new BigDecimal(Double.toString(report.getAdjustment_expenses()));
            double subTotal = b1.add(b2).doubleValue();
            cell4.setCellValue(subTotal);
            cell4.setCellStyle(style2);
            BigDecimal b8 = new BigDecimal(Double.toString(subTotal));
            monthTotal  = monthTotal.add(b8);
            totalSubTotal = monthTotal.doubleValue();

        }

        //合计（计算 倒数第二行数据）
        BigDecimal total = new BigDecimal(0);
        BigDecimal total1 = new BigDecimal(0);
        BigDecimal total2 = new BigDecimal(0);

        Double monthFee = new Double(0);
        Double adjustment_expenses = new Double(0);
        Double month_total = new Double(0);

        for (int i = 0; i < list.size(); i++) {

            CommonDepreciationCost report = list.get(i);
            BigDecimal b1 = new BigDecimal(Double.toString(report.getMonthly_fee()));
            total = total.add(b1);
            monthFee = total.doubleValue();

            BigDecimal b2 = new BigDecimal(Double.toString(report.getAdjustment_expenses()));
            total1 = total1.add(b2);
            adjustment_expenses = total1.doubleValue();

            BigDecimal b3 = new BigDecimal(Double.toString(report.getMonth_total()));
            total2 = total2.add(b3);
            month_total = month_total.doubleValue();

        }

        //倒数第二行
        String[] excelHeader4 = {  "合计:", "", monthFee+"", adjustment_expenses+"", totalSubTotal+"", "",""};
        int a = list.size();
        int b = a+4;
        String[] headnum4 = { b+","+b+",0,0", b+","+b+",1,1", b+","+b+",2,2", b+","+b+",3,3", b+","+b+",4,4", b+","+b+",5,5", b+","+b+",6,6"};

        // 倒数第二行
        row = sheet.createRow(list.size()+4);
        row.setHeightInPoints(30);//设置行高
        this.last_column(list,row,sheet,style2,excelHeader4,headnum4);

        // 最后一行
        row = sheet.createRow(list.size()+5);
        row.setHeightInPoints(30);//设置行高
        this.last_column(list,row,sheet,style3,excelHeader5,headnum5);
        return wb;
    }

    /**
     * 功能 :获取表单导出数据
     *  表名：通用设备折旧修理费月报（跨矿）
     * @throws Exception
     */
    public HSSFWorkbook commonDepreciationCostMonthly(List<CommonDepreciationCostMonthly> list) {

        //第一行表头字段，合并单元格时字段跨几列就将该字段重复几次（此月份得从前台获取）
        String[] excelHeader0 = {  "2018年通用设备折旧修理费月报（本部七矿）---8月份", "", "", "", "", "","","","",""};
        String[] headnum0 = { "0,0,0,9", "0,0,0,9","0,0,0,9","0,0,0,9","0,0,0,9","0,0,0,9","0,0,0,9","0,0,0,9","0,0,0,9"};

        //第二行表头字段
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH )+1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        String[] excelHeader1 = {  "", "", "", "", "", "","","","",year + " . " + month + " ."+ day + " "};
        String[] headnum1 = { "1,1,0,0", "1,1,1,1","1,1,2,2","1,1,3,3", "1,1,4,4","1,1,5,5","1,1,6,6","1,1,7,7","1,1,8,8","1,1,9,9"};

        //第三行表头字段
        String[] excelHeader2 = {  "序号", "设备中类", "矿处单位", "矿处单位", "矿处单位", "矿处单位","矿处单位","矿处单位","矿处单位","小计"};
        String[] headnum2 = { "2,3,0,0", "2,3,1,1", "2,2,2,8", "2,2,2,8", "2,2,2,8", "2,2,2,8", "2,2,2,8", "2,2,2,8", "2,2,2,8", "2,3,9,9"};

        //第四行表头字段
        String[] excelHeader3 = {  "","","南屯煤矿", "兴隆庄", "鲍店", "东滩", "济二", "济三","杨村",""};
        String[] headnum3 = { "3,3,2,2", "3,3,3,3", "3,3,4,4", "3,3,5,5", "3,3,6,6", "3,3,7,7", "3,3,7,7"};


        //最后一行
        String[] excelHeader5 = {  "备注：以上中类汇总含新投入设备管理费用", "", "","", "", "","","","",""};
        int c = list.size();
        int d = c+5;
        String[] headnum5 = { d+","+d+",0,9", d+","+d+",0,9", d+","+d+",0,9", d+","+d+",0,9", d+","+d+",0,9",d+","+d+",0,9",d+","+d+",0,9",d+","+d+",0,9",d+","+d+",0,9"};

        // 声明一个工作簿
        HSSFWorkbook wb = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = wb.createSheet("通用设备折旧修理费月报（跨矿）");

        // 生成一种样式
        HSSFCellStyle style = wb.createCellStyle();
        // 设置样式
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);//下边框
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        // 生成一种字体
        HSSFFont font = wb.createFont();
        // 设置字体
        font.setFontName("微软雅黑");
        // 设置字体大小
        font.setFontHeightInPoints((short) 12);
        // 字体加粗
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        // 在样式中引用这种字体
        style.setFont(font);

        // 生成并设置另一个样式
        HSSFCellStyle style2 = wb.createCellStyle();
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        // 生成并设置另一个样式
        HSSFCellStyle style3 = wb.createCellStyle();
        style3.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style3.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style3.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style3.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style3.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        style3.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        // 第一行表头
        HSSFRow row = sheet.createRow(0);
        row.setHeightInPoints(30);//设置行高
        this.first_column(row,sheet,style,excelHeader0,headnum0);

        // 第二行表头
        row = sheet.createRow(1);
        row.setHeightInPoints(20);//设置行高
        this.first_column(row,sheet,style,excelHeader1,headnum1);
        // 第三行表头
        row = sheet.createRow(2);
        row.setHeightInPoints(20);//设置行高
        this.first_column(row,sheet,style,excelHeader2,headnum2);
        // 第四行表头
        row = sheet.createRow(3);
        row.setHeightInPoints(20);//设置行高
        this.first_column(row,sheet,style2,excelHeader3,headnum3);

        // 第四行数据
        BigDecimal total = new BigDecimal(0);
        Double totalxiaoji = new Double(0);
        for (int i = 0; i < list.size(); i++) {

            row = sheet.createRow(i + 4);
            row.setHeightInPoints(20);//设置行高
            CommonDepreciationCostMonthly report = list.get(i);

            // 导入对应列的数据
            HSSFCell cell = row.createCell(0);
            cell.setCellValue(i+1);
            cell.setCellStyle(style2);

            HSSFCell cell1 = row.createCell(1);
            cell1.setCellValue(report.getEquipment_middle_type());
            cell1.setCellStyle(style2);

            HSSFCell cell2 = row.createCell(2);
            cell2.setCellValue(report.getNantun());
            cell2.setCellStyle(style2);

            HSSFCell cell3 = row.createCell(3);
            cell3.setCellValue(report.getXinglongzhuang());
            cell3.setCellStyle(style2);

            HSSFCell cell4 = row.createCell(4);
            cell4.setCellValue(report.getBaodian());
            cell4.setCellStyle(style2);

            HSSFCell cell5 = row.createCell(5);
            cell5.setCellValue(report.getDongtan());
            cell5.setCellStyle(style2);

            HSSFCell cell6 = row.createCell(6);
            cell6.setCellValue(report.getJier());
            cell6.setCellStyle(style2);

            HSSFCell cell7 = row.createCell(7);
            cell7.setCellValue(report.getJisan());
            cell7.setCellStyle(style2);

            HSSFCell cell8 = row.createCell(8);
            cell8.setCellValue(report.getYangcun());
            cell8.setCellStyle(style2);

            HSSFCell cell9 = row.createCell(9);

            BigDecimal b1 = new BigDecimal(Double.toString(report.getNantun()));
            BigDecimal b2 = new BigDecimal(Double.toString(report.getXinglongzhuang()));
            BigDecimal b3 = new BigDecimal(Double.toString(report.getBaodian()));
            BigDecimal b4 = new BigDecimal(Double.toString(report.getDongtan()));
            BigDecimal b5 = new BigDecimal(Double.toString(report.getJier()));
            BigDecimal b6 = new BigDecimal(Double.toString(report.getJisan()));
            BigDecimal b7 = new BigDecimal(Double.toString(report.getYangcun()));
            double subTotal = b1.add(b2).add(b3).add(b4).add(b5).add(b6).add(b7).doubleValue();
            cell9.setCellValue(subTotal);//小计
            cell9.setCellStyle(style2);
            BigDecimal b8 = new BigDecimal(Double.toString(subTotal));
            total  = total.add(b8);
            totalxiaoji = total.doubleValue();//合计（所有小计合计的数）
        }
        //合计（计算 倒数第二行数据）
        BigDecimal subTotal = new BigDecimal(0);
        BigDecimal subTotal1 = new BigDecimal(0);
        BigDecimal subTotal2 = new BigDecimal(0);
        BigDecimal subTotal3 = new BigDecimal(0);
        BigDecimal subTotal4 = new BigDecimal(0);
        BigDecimal subTotal5 = new BigDecimal(0);
        BigDecimal subTotal6 = new BigDecimal(0);

        Double nantun = new Double(0);
        Double xinglongzhuang = new Double(0);
        Double baodian = new Double(0);
        Double dongtan = new Double(0);
        Double jier = new Double(0);
        Double jisan = new Double(0);
        Double yangcun = new Double(0);

        for (int i = 0; i < list.size(); i++) {

            CommonDepreciationCostMonthly report = list.get(i);
            BigDecimal b1 = new BigDecimal(Double.toString(report.getNantun()));
            subTotal = subTotal.add(b1);
            nantun = subTotal.doubleValue();

            BigDecimal b2 = new BigDecimal(Double.toString(report.getXinglongzhuang()));
            subTotal1 = subTotal1.add(b2);
            xinglongzhuang = subTotal1.doubleValue();

            BigDecimal b3 = new BigDecimal(Double.toString(report.getBaodian()));
            subTotal2 = subTotal2.add(b3);
            baodian = subTotal2.doubleValue();

            BigDecimal b4 = new BigDecimal(Double.toString(report.getDongtan()));
            subTotal3 = subTotal3.add(b4);
            dongtan = subTotal3.doubleValue();

            BigDecimal b5 = new BigDecimal(Double.toString(report.getJier()));
            subTotal4 = subTotal4.add(b5);
            jier = subTotal4.doubleValue();

            BigDecimal b6 = new BigDecimal(Double.toString(report.getJisan()));
            subTotal5 = subTotal5.add(b6);
            jisan = subTotal5.doubleValue();

            BigDecimal b7 = new BigDecimal(Double.toString(report.getYangcun()));
            subTotal6 = subTotal6.add(b7);
            yangcun = subTotal6.doubleValue();
        }

        //倒数第二行
        String[] excelHeader4 = {  "合计:", "",  nantun+"", xinglongzhuang+"", baodian+"", dongtan+"",jier+"",jisan+"",yangcun+"",totalxiaoji+""};
        int a = list.size();
        int b = a+4;
        String[] headnum4 = { b+","+b+",0,1",b+","+b+",2,2", b+","+b+",3,3", b+","+b+",4,4", b+","+b+",5,5",b+","+b+",6,6",b+","+b+",7,7",b+","+b+",8,8",b+","+b+",9,9"};

        // 倒数第二行
        row = sheet.createRow(list.size()+4);
        row.setHeightInPoints(20);//设置行高
        this.last_column(list,row,sheet,style2,excelHeader4,headnum4);

        // 最后一行
        row = sheet.createRow(list.size()+5);
        row.setHeightInPoints(20);//设置行高
        this.last_column(list,row,sheet,style3,excelHeader5,headnum5);

        return wb;
    }

    /**
     * 功能 :获取表单导出数据
     * 表名：综机折旧修理费表
     */
    public HSSFWorkbook comprehensiveMachineryDRCost(List<ComprehensiveMachineryDRCost> list) {

        //第一行表头字段，合并单元格时字段跨几列就将该字段重复几次
        String[] excelHeader0 = {  "东华重工有限公司（6）月综机折旧修理费", "", "", "", ""};
        String[] headnum0 = { "0,0,0,4", "0,0,0,4","0,0,0,4","0,0,0,4"};

        //第二行表头字段
        String[] excelHeader1 = {  "使用单位:", "", "2018年7月8日", "", "编号：18-04"};
        String[] headnum1 = { "1,1,0,1", "1,1,2,2","1,1,3,3", "1,1,4,4"};

        //第三行表头字段
        String[] excelHeader2 = {  "使用地点", "申请单编号", "附清单（张）", "综机折旧大修费（元）", "备   注"};

        String[] headnum2 = { "2,2,0,0", "2,2,1,1", "2,2,2,2", "2,2,3,3", "2,2,4,4"};

        //最后一行
        String[] excelHeader4 = {  "主管:", "审核：", "公司经办人：","东滩矿机电环保科科长：",""};
        int c = list.size();
        int d = c+5;
        String[] headnum4 = { d+","+d+",0,0", d+","+d+",1,1", d+","+d+",2,2", d+","+d+",3,4"};

        // 声明一个工作簿
        HSSFWorkbook wb = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = wb.createSheet("综机折旧修理费表");

        // 生成一种样式
        HSSFCellStyle style = wb.createCellStyle();
        // 设置样式
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);//下边框
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        // 生成一种字体
        HSSFFont font = wb.createFont();
        // 设置字体
        font.setFontName("微软雅黑");
        // 设置字体大小
        font.setFontHeightInPoints((short) 12);
        // 字体加粗
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        // 在样式中引用这种字体
        style.setFont(font);

        // 生成并设置另一个样式
        HSSFCellStyle style2 = wb.createCellStyle();
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        // 生成并设置另一个样式
        HSSFCellStyle style3 = wb.createCellStyle();
        style3.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style3.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style3.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style3.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style3.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        style3.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 生成另一种字体3
        HSSFFont font3 = wb.createFont();
        // 设置字体大小
        font3.setFontHeightInPoints((short) 12);
        // 在样式2中引用这种字体
        style3.setFont(font3);

        // 第一行表头
        HSSFRow row = sheet.createRow(0);
        row.setHeightInPoints(30);//设置行高
        this.first_column(row,sheet,style,excelHeader0,headnum0);

        // 第二行表头
        row = sheet.createRow(1);
        row.setHeightInPoints(30);//设置行高
        this.first_column(row,sheet,style3,excelHeader1,headnum1);
        // 第三行表头
        row = sheet.createRow(2);
        row.setHeightInPoints(30);//设置行高
        this.first_column(row,sheet,style,excelHeader2,headnum2);

        // 第四行数据
        for (int i = 0; i < list.size(); i++) {

            row = sheet.createRow(i + 3);
            row.setHeightInPoints(30);//设置行高
            ComprehensiveMachineryDRCost report = list.get(i);

            // 导入对应列的数据
            HSSFCell cell = row.createCell(0);
            cell.setCellValue(report.getUsing_site());
            cell.setCellStyle(style2);

            HSSFCell cell1 = row.createCell(1);
            cell1.setCellValue(report.getForm_number());
            cell1.setCellStyle(style2);

            HSSFCell cell2 = row.createCell(2);
            cell2.setCellValue(report.getDetailed_list());
            cell2.setCellStyle(style2);

            HSSFCell cell3 = row.createCell(3);
            cell3.setCellValue(report.getDepreciation_repair_charge());
            cell3.setCellStyle(style2);

            HSSFCell cel4 = row.createCell(4);
            cel4.setCellValue(report.getRemark());
            cel4.setCellStyle(style2);

        }

        //合计（计算 倒数第二行数据）
        BigDecimal total = new BigDecimal(0);
        Double depreciation_repair_charge_total = new Double(0);
        int e = 0;
        for (int i = 0; i < list.size(); i++) {

            ComprehensiveMachineryDRCost report = list.get(i);
            BigDecimal b1 = new BigDecimal(Double.toString(report.getDepreciation_repair_charge()));
            total = total.add(b1);
            depreciation_repair_charge_total = total.doubleValue();

           int f = report.getDetailed_list();
            e = e+f;

        }

        //倒数第三行
        String[] excelHeader3 =   { "合计:", "", e+"", depreciation_repair_charge_total+"", ""};
        int a = list.size();
        int b = a+3;
        int g = b+1;
        String[] headnum3 = { b+","+g+",0,0", b+","+b+",1,1", b+","+b+",2,2", b+","+b+",3,3", b+","+b+",4,4"};

        // 倒数第二行
        row = sheet.createRow(list.size()+3);
        row.setHeightInPoints(30);//设置行高
        this.last_column(list,row,sheet,style2,excelHeader3,headnum3);

        //倒数第二行
        String total_money =change(depreciation_repair_charge_total);
        String[] excelHeader6 = {  "", "大写:", total_money+"", "", ""};
        String[] headnum6 = {g+","+g+",1,1", g+","+g+",2,4"};
        row = sheet.createRow(list.size()+4);
        row.setHeightInPoints(30);//设置行高
        this.last_column(list,row,sheet,style2,excelHeader6,headnum6);

        // 最后一行
        row = sheet.createRow(list.size()+5);
        row.setHeightInPoints(30);//设置行高
        this.last_column(list,row,sheet,style3,excelHeader4,headnum4);

        return wb;
    }

    /**
     * 功能 :获取表单导出数据
     * 表名：综机设备使用清单
     */
    public HSSFWorkbook comprehensiveUsedEquipmentRecord(List<ComprehensiveUsedEquipmentRecord> list) {

        //第一行表头字段，合并单元格时字段跨几列就将该字段重复几次
        String[] excelHeader0 = {  "综机设备使用清单", "", "", "", "", "", "", "", ""};
        String[] headnum0 = { "0,0,0,8", "0,0,0,8","0,0,0,8","0,0,0,8","0,0,0,8","0,0,0,8","0,0,0,8","0,0,0,8","0,0,0,8"};

        //第二行表头字段
        String[] excelHeader1 = {  "使用单位:", "", "", "","","","","编号：18-04", ""};
        String[] headnum1 = { "1,1,0,1", "1,1,2,6","1,1,7,8"};

        //第三行表头字段
        String[] excelHeader2 = { "", "名称", "规格型号", "单位", "数量", "日计费标准（元）", "收费天数", "金额（元）", "备   注"};
        String[] headnum2 = { "2,2,0,0","2,2,1,1", "2,2,2,2", "2,2,3,3", "2,2,4,4", "2,2,5,5", "2,2,6,6", "2,2,7,7", "2,2,8,8"};

        // 声明一个工作簿
        HSSFWorkbook wb = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = wb.createSheet("综机设备使用清单");

        // 生成一种样式
        HSSFCellStyle style = wb.createCellStyle();
        // 设置样式
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);//下边框
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        // 生成一种字体
        HSSFFont font = wb.createFont();
        // 设置字体
        font.setFontName("微软雅黑");
        // 设置字体大小
        font.setFontHeightInPoints((short) 12);
        // 字体加粗
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        // 在样式中引用这种字体
        style.setFont(font);

        // 生成并设置另一个样式
        HSSFCellStyle style2 = wb.createCellStyle();
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        // 生成并设置另一个样式
        HSSFCellStyle style3 = wb.createCellStyle();
        style3.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style3.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style3.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style3.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style3.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style3.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 生成另一种字体3
        HSSFFont font3 = wb.createFont();
        // 设置字体大小
        font3.setFontHeightInPoints((short) 12);
        // 在样式2中引用这种字体
        style3.setFont(font3);


        // 第一行表头
        HSSFRow row = sheet.createRow(0);
        row.setHeightInPoints(30);//设置行高
        this.first_column(row,sheet,style,excelHeader0,headnum0);

        // 第二行表头
        row = sheet.createRow(1);
        row.setHeightInPoints(30);//设置行高
        this.first_column(row,sheet,style3,excelHeader1,headnum1);
        // 第三行表头
        row = sheet.createRow(2);
        row.setHeightInPoints(30);//设置行高
        this.first_column(row,sheet,style,excelHeader2,headnum2);

        // 第五行数据
        for (int i = 0; i < list.size(); i++) {

            row = sheet.createRow(i + 4);
            row.setHeightInPoints(30);//设置行高
            ComprehensiveUsedEquipmentRecord report = list.get(i);

            // 导入对应列的数据
            HSSFCell cell = row.createCell(0);
            cell.setCellValue("");
            cell.setCellStyle(style2);

            HSSFCell cell1 = row.createCell(1);
            cell1.setCellValue(report.getEquipment_name());
            cell1.setCellStyle(style2);

            HSSFCell cell2 = row.createCell(2);
            cell2.setCellValue(report.getEquipment_specification());
            cell2.setCellStyle(style2);

            HSSFCell cell3 = row.createCell(3);
            cell3.setCellValue(report.getUnit());
            cell3.setCellStyle(style2);

            HSSFCell cell4 = row.createCell(4);
            cell4.setCellValue(report.getEquipment_num());
            cell4.setCellStyle(style2);

            HSSFCell cell5 = row.createCell(5);
            cell5.setCellValue(report.getDaily_billing_standard());
            cell5.setCellStyle(style2);

            HSSFCell cell6 = row.createCell(6);
            cell6.setCellValue(report.getCharge_days());
            cell6.setCellStyle(style2);


            BigDecimal b1 = new BigDecimal(Double.toString(report.getDaily_billing_standard()));
            BigDecimal b2 = new BigDecimal(report.getCharge_days());
            double total = b1.multiply(b2).doubleValue();

            HSSFCell cell7 = row.createCell(7);
            cell7.setCellValue(total);
            cell7.setCellStyle(style2);

            HSSFCell cel8 = row.createCell(8);
            cel8.setCellValue(report.getRemark());
            cel8.setCellStyle(style2);

        }

        //合计（计算 倒数第二行数据）
        BigDecimal total = new BigDecimal(0);
        Double daily_billing_standard = new Double(0);
        int e = 0;
        for (int i = 0; i < list.size(); i++) {

            ComprehensiveUsedEquipmentRecord report = list.get(i);
            BigDecimal b1 = new BigDecimal(Double.toString(report.getDaily_billing_standard()));
            BigDecimal b2 = new BigDecimal(report.getCharge_days());
            BigDecimal b3 =  b1.multiply(b2);
            total = total.add(b3);
            daily_billing_standard = total.doubleValue();

        }

        //第四行表头字段
        String[] excelHeader3 = {  "合计", "", "", "", "", "", "", daily_billing_standard+"", ""};
        String[] headnum3 = { "3,3,0,0","3,3,1,1","3,3,2,2","3,3,3,3","3,3,4,4","3,3,5,5","3,3,6,6","3,3,7,7","3,3,8,8"};
        row = sheet.createRow(3);
        row.setHeightInPoints(30);//设置行高
        this.first_column(row,sheet,style,excelHeader3,headnum3);

        return wb;
    }


    /**
     * 功能 :获取表单导出数据
     * 表名：综机设备使用交接单
     */
    public HSSFWorkbook comprehensiveUseInterchangeForm(List<ComprehensiveUseInterchangeForm> list) {

        //第一行表头字段，合并单元格时字段跨几列就将该字段重复几次
        String[] excelHeader0 = {  "兖州煤业股份有限公司设备管理中心", "", "", "", "", "", "", "", "", "", ""};
        String[] headnum0 = { "0,0,0,10", "0,0,0,10","0,0,0,10","0,0,0,10","0,0,0,10","0,0,0,10","0,0,0,10","0,0,0,10","0,0,0,10","0,0,0,10","0,0,0,10"};

        //第二行表头字段
        String[] excelHeader1 = {  "综机设备使用交接单", "", "", "","","","","", "", "", ""};
        String[] headnum1 = { "1,1,0,10", "1,1,0,10","1,1,0,10","1,1,0,10","1,1,0,10","1,1,0,10","1,1,0,10","1,1,0,10","1,1,0,10","1,1,0,10","1,1,0,10"};

        //第三行表头字段
        String[] excelHeader2 = {  "", "", "", "","","","","", "", "交接日期：", ""};
        String[] headnum2 = { "2,2,0,8", "2,2,0,8","2,2,0,8","2,2,0,8","2,2,0,8","2,2,0,8","2,2,0,8","2,2,0,8","2,2,0,8","2,2,9,10","2,2,9,10"};


        //第四行表头字段
        String[] excelHeader3 = { "序号", "使用单位（矿）", "使用地点（工作面）", "设备名称", "设备型号", "数量", "技术标识符", "归属公司", "归属公司","是否新设备","备   注"};
        String[] headnum3 = { "3,4,0,0","3,4,1,1", "3,4,2,2", "3,4,3,3", "3,4,4,4", "3,4,5,5", "3,4,6,6", "3,3,7,8", "3,3,7,8","3,4,9,9","3,4,10,10"};

        //第五行表头字段
        String[] excelHeader4 = { "", "", "", "", "", "", "", "1180煤业", "1730东华","",""};
        String[] headnum4 = {  "4,4,7,7", "4,4,8,8"};



        // 声明一个工作簿
        HSSFWorkbook wb = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = wb.createSheet("综机设备使用交接单");

        // 生成一种样式
        HSSFCellStyle style = wb.createCellStyle();
        // 设置样式
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);//下边框
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        // 生成一种字体
        HSSFFont font = wb.createFont();
        // 设置字体
        font.setFontName("微软雅黑");
        // 设置字体大小
        font.setFontHeightInPoints((short) 12);
        // 字体加粗
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        // 在样式中引用这种字体
        style.setFont(font);

        // 生成并设置另一个样式
        HSSFCellStyle style2 = wb.createCellStyle();
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        // 生成并设置另一个样式
        HSSFCellStyle style3 = wb.createCellStyle();
        style3.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style3.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style3.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style3.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style3.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style3.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 生成另一种字体3
        HSSFFont font3 = wb.createFont();
        // 设置字体大小
        font3.setFontHeightInPoints((short) 12);
        // 在样式2中引用这种字体
        style3.setFont(font3);

        // 生成并设置另一个样式
        HSSFCellStyle style4 = wb.createCellStyle();
        style4.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style4.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style4.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style4.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style4.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        style4.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        // 生成并设置另一个样式
        HSSFCellStyle style5 = wb.createCellStyle();
        style5.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style5.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style5.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style5.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style5.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style5.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        // 生成一种字体
        HSSFFont font4 = wb.createFont();
        // 设置字体
        font4.setFontName("微软雅黑");
        // 设置字体大小
        font4.setFontHeightInPoints((short) 16);
        // 字体加粗
        font4.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        // 在样式中引用这种字体
        style5.setFont(font4);

        // 生成并设置另一个样式
        HSSFCellStyle style6 = wb.createCellStyle();
        style6.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style6.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style6.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style6.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style6.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        style6.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        // 生成一种字体
        HSSFFont font5 = wb.createFont();
        // 设置字体
        font5.setFontName("微软雅黑");
        // 设置字体大小
        font5.setFontHeightInPoints((short) 12);
        // 字体加粗
        font5.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        // 在样式中引用这种字体
        style6.setFont(font5);

        // 第一行表头
        HSSFRow row = sheet.createRow(0);
        row.setHeightInPoints(30);//设置行高
        this.first02(row,sheet,style3,excelHeader0,headnum0);

        // 第二行表头
        row = sheet.createRow(1);
        row.setHeightInPoints(30);//设置行高
        this.first02(row,sheet,style5,excelHeader1,headnum1);

        // 第三行表头
        row = sheet.createRow(2);
        row.setHeightInPoints(30);//设置行高
        this.first02(row,sheet,style4,excelHeader2,headnum2);

        // 第四行表头
        row = sheet.createRow(3);
        row.setHeightInPoints(30);//设置行高
        this.first02(row,sheet,style,excelHeader3,headnum3);

        // 第五行表头
        row = sheet.createRow(4);
        row.setHeightInPoints(30);//设置行高
        this.first02(row,sheet,style,excelHeader4,headnum4);

        // 第五行数据
        for (int i = 0; i < list.size(); i++) {

            row = sheet.createRow(i + 5);
            row.setHeightInPoints(30);//设置行高
            ComprehensiveUseInterchangeForm report = list.get(i);

            // 导入对应列的数据
            HSSFCell cell = row.createCell(0);
            cell.setCellValue(i+1);
            cell.setCellStyle(style2);

            HSSFCell cell1 = row.createCell(1);
            cell1.setCellValue(report.getItem_position());
            cell1.setCellStyle(style2);

            HSSFCell cell2 = row.createCell(2);
            cell2.setCellValue(report.getUse_place());
            cell2.setCellStyle(style2);

            HSSFCell cell3 = row.createCell(3);
            cell3.setCellValue(report.getEquipment_name());
            cell3.setCellStyle(style2);

            HSSFCell cell4 = row.createCell(4);
            cell4.setCellValue(report.getEquipment_specification());
            cell4.setCellStyle(style2);

            HSSFCell cell5 = row.createCell(5);
            cell5.setCellValue(report.getEquipment_num());
            cell5.setCellStyle(style2);

            HSSFCell cell6 = row.createCell(6);
            cell6.setCellValue(report.getTech_code());
            cell6.setCellStyle(style2);

            HSSFCell cell7 = row.createCell(7);//此处是归属公司，需要判断属于哪一个（1180煤业，1730东华）
            cell7.setCellValue("√");
            cell7.setCellStyle(style2);

            HSSFCell cel8 = row.createCell(8);
            cel8.setCellValue("");
            cel8.setCellStyle(style2);

            HSSFCell cel9 = row.createCell(9);
            cel9.setCellValue(report.getWhether_new_equ());
            cel9.setCellStyle(style2);

            HSSFCell cel10 = row.createCell(10);
            cel10.setCellValue(report.getRemark());
            cel10.setCellStyle(style2);

        }

        //倒数第6行
        String[] excelHeader5 = {  "注:", "",  "", "", "", "","","","","",""};//注的后面有一句话。看不清
        int a = list.size();
        int b = a+6;
        String[] headnum5 = { b+","+b+",0,10",b+","+b+",0,10", b+","+b+",0,10", b+","+b+",0,10", b+","+b+",0,10",b+","+b+",0,10",b+","+b+",0,10",b+","+b+",0,10",b+","+b+",0,10",b+","+b+",0,10",b+","+b+",0,10"};
        // 倒数第6行
        row = sheet.createRow(list.size()+6);
        row.setHeightInPoints(20);//设置行高
        this.last_column(list,row,sheet,style4,excelHeader5,headnum5);

        //倒数第5行
        String[] excelHeader6 = {  "培养要求:", "",  "", "", "", "","","","","",""};
        int c = a+7;
        String[] headnum6 = { c+","+c+",0,10",c+","+c+",0,10", c+","+c+",0,10", c+","+c+",0,10", c+","+c+",0,10",c+","+c+",0,10",c+","+c+",0,10",c+","+c+",0,10",c+","+c+",0,10",c+","+c+",0,10",c+","+c+",0,10"};
        // 倒数第5行
        row = sheet.createRow(list.size()+7);
        row.setHeightInPoints(20);//设置行高
        this.last_column(list,row,sheet,style4,excelHeader6,headnum6);

        //倒数第4行
        String[] excelHeader7 = {  "        1.那发到更符合规范化高合金钢", "",  "", "", "", "","","","","",""};//后面有一句话。看不清
        int d = a+8;
        String[] headnum7 = { d+","+d+",0,10",d+","+d+",0,10", d+","+d+",0,10", d+","+d+",0,10",d+","+d+",0,10",d+","+d+",0,10",d+","+d+",0,10",d+","+d+",0,10",d+","+d+",0,10",d+","+d+",0,10",d+","+d+",0,10"};
        // 倒数第5行
        row = sheet.createRow(list.size()+8);
        row.setHeightInPoints(20);//设置行高
        this.last_column(list,row,sheet,style4,excelHeader7,headnum7);

        //倒数第3行
        String[] excelHeader8 = {  "        2.阿法狗多或过扩绿绿绿绿", "",  "", "", "", "","","","","",""};//后面有一句话。看不清
        int e = a+9;
        String[] headnum8 = { e+","+e+",0,10",e+","+e+",0,10", e+","+e+",0,10", e+","+e+",0,10",e+","+e+",0,10",e+","+e+",0,10",e+","+e+",0,10",e+","+e+",0,10",e+","+e+",0,10",e+","+e+",0,10",e+","+e+",0,10"};
        // 倒数第5行
        row = sheet.createRow(list.size()+9);
        row.setHeightInPoints(20);//设置行高
        this.last_column(list,row,sheet,style4,excelHeader8,headnum8);

        //倒数第2行
        String[] excelHeader9 = {  "        3.他人委托人规范化大家好看看", "",  "", "", "", "","","","","",""};//后面有一句话。看不清
        int f = a+10;
        String[] headnum9 = { f+","+f+",0,10",f+","+f+",0,10", f+","+f+",0,10",f+","+f+",0,10", f+","+f+",0,10",f+","+f+",0,10",f+","+f+",0,10",f+","+f+",0,10",f+","+f+",0,10",f+","+f+",0,10",f+","+f+",0,10"};
        // 倒数第2行
        row = sheet.createRow(list.size()+10);
        row.setHeightInPoints(20);//设置行高
        this.last_column(list,row,sheet,style4,excelHeader9,headnum9);

       //倒数第1行
        String[] excelHeader10 = {  "设备管理中心经办人：", "",  "", "", "", "","使用单位经办人：","","","",""};//后面有一句话。看不清
        int g = a+11;
        String[] headnum10 = { g+","+g+",0,5",g+","+g+",0,5", g+","+g+",0,5",g+","+g+",0,5", g+","+g+",0,5",g+","+g+",0,5",  g+","+g+",6,10",g+","+g+",6,10",g+","+g+",6,10",g+","+g+",6,10",g+","+g+",6,10"};
        // 最后一行
        row = sheet.createRow(list.size()+11);
        row.setHeightInPoints(20);//设置行高
        this.last_column(list,row,sheet,style6,excelHeader10,headnum10);

        return wb;
    }

    /**
     * 功能 :获取表单导出数据
     * 表名：2018年综机折旧修理费汇总表
     */
    public HSSFWorkbook comprehensiveDepreciationRCostCollect(List<ComprehensiveDepreciationRCostCollect> list) {

        //第一行表头字段，合并单元格时字段跨几列就将该字段重复几次
        String[] excelHeader0 = {  "2018年综机折旧修理费汇总表", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};
        String[] headnum0 = { "0,0,0,14", "0,0,0,14","0,0,0,14","0,0,0,14","0,0,0,14","0,0,0,14","0,0,0,14","0,0,0,14","0,0,0,14","0,0,0,14","0,0,0,14","0,0,0,14","0,0,0,14","0,0,0,14","0,0,0,14"};

        //第二行表头字段
        String[] excelHeader1 = {  "序号", "矿别", "逐月累计收费金额（万元，不含税）", "","","","","", "", "", "","","","",""};
        String[] headnum1 = { "1,2,0,0", "1,2,1,1","1,1,2,12","1,1,2,12","1,1,2,12","1,1,2,12","1,1,2,12","1,1,2,12","1,1,2,12","1,1,2,12","1,1,2,12","1,1,2,12","1,1,2,12","1,1,13,14"};

        //第三行表头字段
        String[] excelHeader2 = { "","", "1月", "2月", "3月", "4月","5月","6月","7月","8月", "9月", "10月","11月","12月","合计"};
        String[] headnum2 = { "2,2,2,2", "2,2,3,3","2,2,4,4","2,2,5,5","2,2,6,6","2,2,7,7","2,2,8,8","2,2,9,9","2,2,10,10","2,2,11,11","2,2,12,12","2,2,13,13","2,2,14,14"};


//        第四行表头字段
//        String[] excelHeader3 = { "序号", "使用单位（矿）", "使用地点（工作面）", "设备名称", "设备型号", "数量", "技术标识符", "归属公司", "归属公司","是否新设备","备   注"};
//        String[] headnum3 = { "3,4,0,0","3,4,1,1", "3,4,2,2", "3,4,3,3", "3,4,4,4", "3,4,5,5", "3,4,6,6", "3,3,7,8", "3,3,7,8","3,4,9,9","3,4,10,10"};
//
//        第五行表头字段
//        String[] excelHeader4 = { "", "", "", "", "", "", "", "1180煤业", "1730东华","",""};
//        String[] headnum4 = {  "4,4,7,7", "4,4,8,8"};
//

        // 声明一个工作簿
        HSSFWorkbook wb = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = wb.createSheet("2018年综机折旧修理费汇总表");

        // 生成一种样式
        HSSFCellStyle style = wb.createCellStyle();
        // 设置样式
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);//下边框
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        // 生成一种字体
        HSSFFont font = wb.createFont();
        // 设置字体
        font.setFontName("微软雅黑");
        // 设置字体大小
        font.setFontHeightInPoints((short) 12);
        // 字体加粗
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        // 在样式中引用这种字体
        style.setFont(font);

        // 生成并设置另一个样式
        HSSFCellStyle style2 = wb.createCellStyle();
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        // 生成并设置另一个样式
        HSSFCellStyle style3 = wb.createCellStyle();
        style3.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style3.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style3.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style3.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style3.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style3.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 生成另一种字体3
        HSSFFont font3 = wb.createFont();
        // 设置字体大小
        font3.setFontHeightInPoints((short) 12);
        // 在样式2中引用这种字体
        style3.setFont(font3);

        // 第一行表头
        HSSFRow row = sheet.createRow(0);
        row.setHeightInPoints(30);//设置行高
        this.first02(row,sheet,style,excelHeader0,headnum0);

        // 第二行表头
        row = sheet.createRow(1);
        row.setHeightInPoints(30);//设置行高
        this.first02(row,sheet,style,excelHeader1,headnum1);

        // 第三行表头
        row = sheet.createRow(2);
        row.setHeightInPoints(30);//设置行高
        this.first02(row,sheet,style,excelHeader2,headnum2);

        // 第四行表头
//        row = sheet.createRow(3);
//        row.setHeightInPoints(30);//设置行高
//        this.first02(row,sheet,style,excelHeader3,headnum3);

        // 第五行表头
//        row = sheet.createRow(4);
//        row.setHeightInPoints(30);//设置行高
//        this.first02(row,sheet,style,excelHeader4,headnum4);

        // 第五行数据
        BigDecimal total = new BigDecimal(0);
        Double totalxiaoji = new Double(0);
        for (int i = 0; i < list.size(); i++) {

            row = sheet.createRow(i + 3);
            row.setHeightInPoints(30);//设置行高
            ComprehensiveDepreciationRCostCollect report = list.get(i);

            // 导入对应列的数据
            HSSFCell cell = row.createCell(0);
            cell.setCellValue(i+1);
            cell.setCellStyle(style2);

            HSSFCell cell1 = row.createCell(1);
            cell1.setCellValue(report.getOrg_name());
            cell1.setCellStyle(style2);

            HSSFCell cell2 = row.createCell(2);
            cell2.setCellValue(report.getJanuary_repairs_cost());
            cell2.setCellStyle(style2);

            HSSFCell cell3 = row.createCell(3);
            cell3.setCellValue(report.getFebruary_repairs_cost());
            cell3.setCellStyle(style2);

            HSSFCell cell4 = row.createCell(4);
            cell4.setCellValue(report.getMarch_repairs_cost());
            cell4.setCellStyle(style2);

            HSSFCell cell5 = row.createCell(5);
            cell5.setCellValue(report.getApril_repairs_cost());
            cell5.setCellStyle(style2);

            HSSFCell cell6 = row.createCell(6);
            cell6.setCellValue(report.getMay_repairs_cost());
            cell6.setCellStyle(style2);

            HSSFCell cell7 = row.createCell(7);
            cell7.setCellValue(report.getJune_repairs_cost());
            cell7.setCellStyle(style2);

            HSSFCell cel8 = row.createCell(8);
            cel8.setCellValue(report.getJuly_repairs_cost());
            cel8.setCellStyle(style2);

            HSSFCell cel9 = row.createCell(9);
            cel9.setCellValue(report.getAugust_repairs_cost());
            cel9.setCellStyle(style2);

            HSSFCell cel10 = row.createCell(10);
            cel10.setCellValue(report.getSep_repairs_cost());
            cel10.setCellStyle(style2);

            HSSFCell cel11 = row.createCell(11);
            cel11.setCellValue(report.getOct_repairs_cost());
            cel11.setCellStyle(style2);

            HSSFCell cel12 = row.createCell(12);
            cel12.setCellValue(report.getNov_repairs_cost());
            cel12.setCellStyle(style2);

            HSSFCell cel13 = row.createCell(13);
            cel13.setCellValue(report.getDec_repairs_cost());
            cel13.setCellStyle(style2);

            BigDecimal b1 = new BigDecimal(Double.toString(report.getJanuary_repairs_cost()));
            BigDecimal b2 = new BigDecimal(Double.toString(report.getFebruary_repairs_cost()));
            BigDecimal b3 = new BigDecimal(Double.toString(report.getMarch_repairs_cost()));
            BigDecimal b4 = new BigDecimal(Double.toString(report.getApril_repairs_cost()));
            BigDecimal b5 = new BigDecimal(Double.toString(report.getMay_repairs_cost()));
            BigDecimal b6 = new BigDecimal(Double.toString(report.getJune_repairs_cost()));
            BigDecimal b7 = new BigDecimal(Double.toString(report.getJuly_repairs_cost()));
            BigDecimal b8 = new BigDecimal(Double.toString(report.getAugust_repairs_cost()));
            BigDecimal b9 = new BigDecimal(Double.toString(report.getSep_repairs_cost()));
            BigDecimal b10 = new BigDecimal(Double.toString(report.getOct_repairs_cost()));
            BigDecimal b11 = new BigDecimal(Double.toString(report.getNov_repairs_cost()));
            BigDecimal b12 = new BigDecimal(Double.toString(report.getDec_repairs_cost()));

            double totalT = b1.add(b2).add(b3).add(b4).add(b5).add(b6).add(b7).add(b8).add(b9).add(b10).add(b11).add(b12).doubleValue();


            HSSFCell cel14 = row.createCell(14);
            cel14.setCellValue(totalT);
            cel14.setCellStyle(style2);

            BigDecimal b13 = new BigDecimal(Double.toString(totalT));
            total  = total.add(b13);
            totalxiaoji = total.doubleValue();//合计（所有小计合计的数）

        }

        //合计（计算 倒数第二行数据）
        BigDecimal subTotal1 = new BigDecimal(0);
        BigDecimal subTotal2 = new BigDecimal(0);
        BigDecimal subTotal3 = new BigDecimal(0);
        BigDecimal subTotal4 = new BigDecimal(0);
        BigDecimal subTotal5 = new BigDecimal(0);
        BigDecimal subTotal6 = new BigDecimal(0);
        BigDecimal subTotal7 = new BigDecimal(0);
        BigDecimal subTotal8 = new BigDecimal(0);
        BigDecimal subTotal9 = new BigDecimal(0);
        BigDecimal subTotal10 = new BigDecimal(0);
        BigDecimal subTotal11 = new BigDecimal(0);
        BigDecimal subTotal12 = new BigDecimal(0);

        Double january = new Double(0);
        Double february = new Double(0);
        Double march = new Double(0);
        Double april = new Double(0);
        Double may = new Double(0);
        Double june = new Double(0);
        Double july = new Double(0);
        Double august = new Double(0);
        Double sep = new Double(0);
        Double oct = new Double(0);
        Double nov = new Double(0);
        Double dec = new Double(0);


        for (int i = 0; i < list.size(); i++) {

            ComprehensiveDepreciationRCostCollect report = list.get(i);

            BigDecimal b1 = new BigDecimal(Double.toString(report.getJanuary_repairs_cost()));
            subTotal1 = subTotal1.add(b1);
            january = subTotal1.doubleValue();

            BigDecimal b2 = new BigDecimal(Double.toString(report.getFebruary_repairs_cost()));
            subTotal2 = subTotal2.add(b2);
            february = subTotal2.doubleValue();

            BigDecimal b3 = new BigDecimal(Double.toString(report.getMarch_repairs_cost()));
            subTotal3 = subTotal3.add(b3);
            march = subTotal3.doubleValue();

            BigDecimal b4 = new BigDecimal(Double.toString(report.getApril_repairs_cost()));
            subTotal4 = subTotal4.add(b4);
            april = subTotal4.doubleValue();

            BigDecimal b5 = new BigDecimal(Double.toString(report.getMay_repairs_cost()));
            subTotal5 = subTotal5.add(b5);
            may = subTotal5.doubleValue();

            BigDecimal b6 = new BigDecimal(Double.toString(report.getJune_repairs_cost()));
            subTotal6 = subTotal6.add(b6);
            june = subTotal6.doubleValue();

            BigDecimal b7 = new BigDecimal(Double.toString(report.getJune_repairs_cost()));
            subTotal7 = subTotal7.add(b7);
            july = subTotal7.doubleValue();

            BigDecimal b8 = new BigDecimal(Double.toString(report.getJune_repairs_cost()));
            subTotal8 = subTotal8.add(b8);
            august = subTotal6.doubleValue();

            BigDecimal b9 = new BigDecimal(Double.toString(report.getJune_repairs_cost()));
            subTotal9 = subTotal6.add(b9);
            sep = subTotal9.doubleValue();

            BigDecimal b10 = new BigDecimal(Double.toString(report.getJune_repairs_cost()));
            subTotal10 = subTotal10.add(b10);
            oct = subTotal10.doubleValue();

            BigDecimal b11 = new BigDecimal(Double.toString(report.getJune_repairs_cost()));
            subTotal11 = subTotal11.add(b11);
            nov = subTotal11.doubleValue();

            BigDecimal b12 = new BigDecimal(Double.toString(report.getJune_repairs_cost()));
            subTotal12 = subTotal12.add(b12);
            dec = subTotal12.doubleValue();


        }

        //倒数第6行
        String[] excelHeader3 = {  "", "小计",  january+"", february+"", march+"", april+"",may+"",june+"",july+"",august+"",sep+"",oct+"",nov+"",dec+"",totalxiaoji+""};
        int a = list.size();
        int b = a+3;
        String[] headnum3 = { b+","+b+",0,0",b+","+b+",1,1", b+","+b+",2,2", b+","+b+",3,3", b+","+b+",4,4",b+","+b+",5,5",b+","+b+",6,6",b+","+b+",7,7",b+","+b+",8,8",b+","+b+",9,9",b+","+b+",10,10",b+","+b+",11,11",b+","+b+",12,12",b+","+b+",13,13",b+","+b+",14,14"};
        // 倒数第6行
        row = sheet.createRow(list.size()+3);
        row.setHeightInPoints(20);//设置行高
        this.first02(row,sheet,style2,excelHeader3,headnum3);

        //倒数第6行
        String[] excelHeader4 = {  "", "小计",  january+"", february+"", march+"", april+"",may+"",june+"",july+"",august+"",sep+"",oct+"",nov+"",dec+"",totalxiaoji+""};
        int c = a+5;
        String[] headnum4 = { b+","+b+",0,0",b+","+b+",1,1", b+","+b+",2,2", b+","+b+",3,3", b+","+b+",4,4",b+","+b+",5,5",b+","+b+",6,6",b+","+b+",7,7",b+","+b+",8,8",b+","+b+",9,9",b+","+b+",10,10",b+","+b+",11,11",b+","+b+",12,12",b+","+b+",13,13",b+","+b+",14,14"};


        // 倒数第6行
        row = sheet.createRow(list.size()+5);
        row.setHeightInPoints(20);//设置行高
        this.first02(row,sheet,style2,excelHeader4,headnum4);

        // 第五行数据
//        BigDecimal total = new BigDecimal(0);
//        Double totalxiaoji = new Double(0);
        for (int i = 0; i < list.size(); i++) {

            row = sheet.createRow(i + 12);
            row.setHeightInPoints(30);//设置行高
            ComprehensiveDepreciationRCostCollect report = list.get(i);

            // 导入对应列的数据
            HSSFCell cell = row.createCell(0);
            cell.setCellValue(i+1);
            cell.setCellStyle(style2);

            HSSFCell cell1 = row.createCell(1);
            cell1.setCellValue(report.getOrg_name());
            cell1.setCellStyle(style2);

            HSSFCell cell2 = row.createCell(2);
            cell2.setCellValue(report.getJanuary_repairs_cost());
            cell2.setCellStyle(style2);

            HSSFCell cell3 = row.createCell(3);
            cell3.setCellValue(report.getFebruary_repairs_cost());
            cell3.setCellStyle(style2);

            HSSFCell cell4 = row.createCell(4);
            cell4.setCellValue(report.getMarch_repairs_cost());
            cell4.setCellStyle(style2);

            HSSFCell cell5 = row.createCell(5);
            cell5.setCellValue(report.getApril_repairs_cost());
            cell5.setCellStyle(style2);

            HSSFCell cell6 = row.createCell(6);
            cell6.setCellValue(report.getMay_repairs_cost());
            cell6.setCellStyle(style2);

            HSSFCell cell7 = row.createCell(7);
            cell7.setCellValue(report.getJune_repairs_cost());
            cell7.setCellStyle(style2);

            HSSFCell cel8 = row.createCell(8);
            cel8.setCellValue(report.getJuly_repairs_cost());
            cel8.setCellStyle(style2);

            HSSFCell cel9 = row.createCell(9);
            cel9.setCellValue(report.getAugust_repairs_cost());
            cel9.setCellStyle(style2);

            HSSFCell cel10 = row.createCell(10);
            cel10.setCellValue(report.getSep_repairs_cost());
            cel10.setCellStyle(style2);

            HSSFCell cel11 = row.createCell(11);
            cel11.setCellValue(report.getOct_repairs_cost());
            cel11.setCellStyle(style2);

            HSSFCell cel12 = row.createCell(12);
            cel12.setCellValue(report.getNov_repairs_cost());
            cel12.setCellStyle(style2);

            HSSFCell cel13 = row.createCell(13);
            cel13.setCellValue(report.getDec_repairs_cost());
            cel13.setCellStyle(style2);

            BigDecimal b1 = new BigDecimal(Double.toString(report.getJanuary_repairs_cost()));
            BigDecimal b2 = new BigDecimal(Double.toString(report.getFebruary_repairs_cost()));
            BigDecimal b3 = new BigDecimal(Double.toString(report.getMarch_repairs_cost()));
            BigDecimal b4 = new BigDecimal(Double.toString(report.getApril_repairs_cost()));
            BigDecimal b5 = new BigDecimal(Double.toString(report.getMay_repairs_cost()));
            BigDecimal b6 = new BigDecimal(Double.toString(report.getJune_repairs_cost()));
            BigDecimal b7 = new BigDecimal(Double.toString(report.getJuly_repairs_cost()));
            BigDecimal b8 = new BigDecimal(Double.toString(report.getAugust_repairs_cost()));
            BigDecimal b9 = new BigDecimal(Double.toString(report.getSep_repairs_cost()));
            BigDecimal b10 = new BigDecimal(Double.toString(report.getOct_repairs_cost()));
            BigDecimal b11 = new BigDecimal(Double.toString(report.getNov_repairs_cost()));
            BigDecimal b12 = new BigDecimal(Double.toString(report.getDec_repairs_cost()));

            double totalT = b1.add(b2).add(b3).add(b4).add(b5).add(b6).add(b7).add(b8).add(b9).add(b10).add(b11).add(b12).doubleValue();


            HSSFCell cel14 = row.createCell(14);
            cel14.setCellValue(totalT);
            cel14.setCellStyle(style2);

            BigDecimal b13 = new BigDecimal(Double.toString(totalT));
            total  = total.add(b13);
            totalxiaoji = total.doubleValue();//合计（所有小计合计的数）

        }
//
//        //倒数第5行
//        String[] excelHeader6 = {  "培养要求:", "",  "", "", "", "","","","","",""};
//        int c = a+7;
//        String[] headnum6 = { c+","+c+",0,10",c+","+c+",0,10", c+","+c+",0,10", c+","+c+",0,10", c+","+c+",0,10",c+","+c+",0,10",c+","+c+",0,10",c+","+c+",0,10",c+","+c+",0,10",c+","+c+",0,10",c+","+c+",0,10"};
//        // 倒数第5行
//        row = sheet.createRow(list.size()+7);
//        row.setHeightInPoints(20);//设置行高
//        this.last_column(list,row,sheet,style4,excelHeader6,headnum6);
//
//        //倒数第4行
//        String[] excelHeader7 = {  "        1.那发到更符合规范化高合金钢", "",  "", "", "", "","","","","",""};//后面有一句话。看不清
//        int d = a+8;
//        String[] headnum7 = { d+","+d+",0,10",d+","+d+",0,10", d+","+d+",0,10", d+","+d+",0,10",d+","+d+",0,10",d+","+d+",0,10",d+","+d+",0,10",d+","+d+",0,10",d+","+d+",0,10",d+","+d+",0,10",d+","+d+",0,10"};
//        // 倒数第5行
//        row = sheet.createRow(list.size()+8);
//        row.setHeightInPoints(20);//设置行高
//        this.last_column(list,row,sheet,style4,excelHeader7,headnum7);
//
//        //倒数第3行
//        String[] excelHeader8 = {  "        2.阿法狗多或过扩绿绿绿绿", "",  "", "", "", "","","","","",""};//后面有一句话。看不清
//        int e = a+9;
//        String[] headnum8 = { e+","+e+",0,10",e+","+e+",0,10", e+","+e+",0,10", e+","+e+",0,10",e+","+e+",0,10",e+","+e+",0,10",e+","+e+",0,10",e+","+e+",0,10",e+","+e+",0,10",e+","+e+",0,10",e+","+e+",0,10"};
//        // 倒数第5行
//        row = sheet.createRow(list.size()+9);
//        row.setHeightInPoints(20);//设置行高
//        this.last_column(list,row,sheet,style4,excelHeader8,headnum8);
//
//        //倒数第2行
//        String[] excelHeader9 = {  "        3.他人委托人规范化大家好看看", "",  "", "", "", "","","","","",""};//后面有一句话。看不清
//        int f = a+10;
//        String[] headnum9 = { f+","+f+",0,10",f+","+f+",0,10", f+","+f+",0,10",f+","+f+",0,10", f+","+f+",0,10",f+","+f+",0,10",f+","+f+",0,10",f+","+f+",0,10",f+","+f+",0,10",f+","+f+",0,10",f+","+f+",0,10"};
//        // 倒数第2行
//        row = sheet.createRow(list.size()+10);
//        row.setHeightInPoints(20);//设置行高
//        this.last_column(list,row,sheet,style4,excelHeader9,headnum9);
//
//        //倒数第1行
//        String[] excelHeader10 = {  "设备管理中心经办人：", "",  "", "", "", "","使用单位经办人：","","","",""};//后面有一句话。看不清
//        int g = a+11;
//        String[] headnum10 = { g+","+g+",0,5",g+","+g+",0,5", g+","+g+",0,5",g+","+g+",0,5", g+","+g+",0,5",g+","+g+",0,5",  g+","+g+",6,10",g+","+g+",6,10",g+","+g+",6,10",g+","+g+",6,10",g+","+g+",6,10"};
//        // 最后一行
//        row = sheet.createRow(list.size()+11);
//        row.setHeightInPoints(20);//设置行高
//        this.last_column(list,row,sheet,style6,excelHeader10,headnum10);

        return wb;
    }

    /**
     * 第一行合并单元格，填充数据
     * @param row
     * @param sheet
     * @param style
     * @param excelHeader0
     * @param headnum0
     */
    private void first_column(HSSFRow row,HSSFSheet sheet,HSSFCellStyle style,String[] excelHeader0 ,String[] headnum0){

        for (int i = 0; i < excelHeader0.length; i++) {
            sheet.setColumnWidth(i,6500);
            HSSFCell cell = row.createCell(i);
            cell.setCellValue(excelHeader0[i]);
            cell.setCellStyle(style);
            if (i >= 0 && i <=6 ) {
                for (int j = 0; j < excelHeader0.length; j++) {
                    // 从第j+1列开始填充
                    cell = row.createCell(j);
                    // 填充excelHeader1[j]第j个元素
                    cell.setCellValue(excelHeader0[j]);
                    cell.setCellStyle(style);
                }
            }
        }

        // 动态合并单元格
        for (int i = 0; i < headnum0.length; i++) {
            sheet.setColumnWidth(i,6500);
            String[] temp = headnum0[i].split(",");
            Integer startrow = Integer.parseInt(temp[0]);
            Integer overrow = Integer.parseInt(temp[1]);
            Integer startcol = Integer.parseInt(temp[2]);
            Integer overcol = Integer.parseInt(temp[3]);
            sheet.addMergedRegion(new CellRangeAddress(startrow, overrow, startcol, overcol));
        }

    }

    /**
     * 第二行合并单元格，填充数据
     * @param row
     * @param sheet
     * @param style
     * @param excelHeader1
     * @param headnum1
     */
    private void first02(HSSFRow row,HSSFSheet sheet,HSSFCellStyle style,String[] excelHeader1 ,String[] headnum1){

        for (int i = 0; i < excelHeader1.length; i++) {
//            sheet.autoSizeColumn(i, true);// 自动调整宽度
            sheet.setColumnWidth(i,5500);
            HSSFCell cell = row.createCell(i);
            cell.setCellValue(excelHeader1[i]);
            cell.setCellStyle(style);
            if (i >= 0 && i <=6 ) {
                for (int j = 0; j < excelHeader1.length; j++) {
                    // 从第j+1列开始填充
                    cell = row.createCell(j);
                    // 填充excelHeader1[j]第j个元素
                    cell.setCellValue(excelHeader1[j]);
                    cell.setCellStyle(style);
                }
            }
        }

        // 动态合并单元格
        for (int i = 0; i < headnum1.length; i++) {
            sheet.setColumnWidth(i,5500);
            String[] temp = headnum1[i].split(",");
            Integer startrow = Integer.parseInt(temp[0]);
            Integer overrow = Integer.parseInt(temp[1]);
            Integer startcol = Integer.parseInt(temp[2]);
            Integer overcol = Integer.parseInt(temp[3]);
            sheet.addMergedRegion(new CellRangeAddress(startrow, overrow, startcol, overcol));
        }

    }

    /**
     * 最后一行(倒数第二行也可)合并单元格，填充数据
     * @param row
     * @param sheet
     * @param style3
     * @param excelHeader3
     * @param headnum3
     */
    private void last_column(List list,HSSFRow row,HSSFSheet sheet,HSSFCellStyle style3,String[] excelHeader3 ,String[] headnum3){

        for (int i = 0; i < excelHeader3.length; i++) {
            sheet.setColumnWidth(i, 6500);

            HSSFCell cell = row.createCell(i);
            cell.setCellValue(excelHeader3[i]);
            cell.setCellStyle(style3);
            for (int j = 0; j < excelHeader3.length; j++) {
                // 从第j+1列开始填充
                cell = row.createCell(j);
                // 填充excelHeader1[j]第j个元素
                cell.setCellValue(excelHeader3[j]);
                cell.setCellStyle(style3);
            }
        }
        // 动态合并单元格
        for (int i = 0; i < headnum3.length; i++) {
            sheet.setColumnWidth(i,6500);
            String[] temp = headnum3[i].split(",");
            Integer startrow = Integer.parseInt(temp[0]);
            Integer overrow = Integer.parseInt(temp[1]);
            Integer startcol = Integer.parseInt(temp[2]);
            Integer overcol = Integer.parseInt(temp[3]);
            sheet.addMergedRegion(new CellRangeAddress(startrow, overrow, startcol, overcol));
        }

    }

    /**
     *  数字小写转大写
     * @param v
     * @return
     */
    public static String change(double v) {
            if (v < 0 || v > MAX_VALUE) {
                return "参数非法!";
            }
            long l = Math.round(v * 100);
            if (l == 0) {
                return "零元整";
            }
            String strValue = l + "";
            // i用来控制数
            int i = 0;
            // j用来控制单位
            int j = UNIT.length() - strValue.length();
            String rs = "";
            boolean isZero = false;
            for (; i < strValue.length(); i++, j++) {
                char ch = strValue.charAt(i);
                if (ch == '0') {
                    isZero = true;
                    if (UNIT.charAt(j) == '亿' || UNIT.charAt(j) == '万'
                            || UNIT.charAt(j) == '元') {
                        rs = rs + UNIT.charAt(j);
                        isZero = false;
                    }
                } else {
                    if (isZero) {
                        rs = rs + "零";
                        isZero = false;
                    }
                    rs = rs + DIGIT.charAt(ch - '0') + UNIT.charAt(j);
                }
            }
//		if (!rs.endsWith("分")) {
//			rs = rs + "整";
//		}
            rs = rs.replaceAll("亿万", "亿");
            return rs;
        }



}
