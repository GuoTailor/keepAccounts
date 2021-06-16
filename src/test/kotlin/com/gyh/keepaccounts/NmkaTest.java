package com.gyh.keepaccounts;

import com.gyh.keepaccounts.model.view.BillResponseInfo;
import org.apache.poi.hssf.usermodel.*;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by GYH on 2021/6/7
 */
public class NmkaTest {
    @Test
    public void te() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyMM");
        System.out.println(dateFormat.format(new Date()));
    }

    @Test
    public void test4() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        long last = calendar.getTimeInMillis();
        System.out.println((int) ((last - Calendar.getInstance().getTimeInMillis()) / 1000) + 1);
    }

    public void nmka(List<BillResponseInfo> list) throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        //创建Excel工作表对象
        HSSFSheet sheet = workbook.createSheet("工作表");
        HSSFCellStyle style = workbook.createCellStyle();

        HSSFRow row = sheet.createRow(0);
        HSSFCell cell1 = row.createCell(1);
        cell1.setCellValue("开单人");
        HSSFCell cell2 = row.createCell(2);
        cell2.setCellValue("车型");
        HSSFCell cell3 = row.createCell(3);
        cell3.setCellValue("规格");
        HSSFCell cell4 = row.createCell(4);
        cell4.setCellValue("数量");
        HSSFCell cell5 = row.createCell(5);
        cell5.setCellValue("价格");
        HSSFCell cell6 = row.createCell(6);
        cell6.setCellValue("备注");
        HSSFCell cell7 = row.createCell(7);
        cell7.setCellValue("日期");

        for (int i = 1; i <= list.size(); i++) {
            HSSFRow dataRow = sheet.createRow(i);
            HSSFCell name = dataRow.createCell(1);
            name.setCellValue(list.get(i - 1).getUsername());
            HSSFCell type = dataRow.createCell(2);
            type.setCellValue(list.get(i - 1).getType());
            HSSFCell specification = dataRow.createCell(3);
            specification.setCellValue(list.get(i - 1).getSpecification());
            HSSFCell amount = dataRow.createCell(4);
            amount.setCellValue(list.get(i - 1).getAmount());
            HSSFCell price = dataRow.createCell(5);
            price.setCellValue(list.get(i - 1).getPrice().toPlainString());
            HSSFCell remark = dataRow.createCell(6);
            remark.setCellValue(list.get(i - 1).getRemark());
            HSSFCell time = dataRow.createCell(7);
            time.setCellValue(list.get(i - 1).getCreateTime());
            style.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));
            time.setCellStyle(style);
        }
        FileOutputStream out = new FileOutputStream(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()).toString() + ".xls");
        workbook.write(out);
        out.close();
    }

    //Excel的单元格操作
    @Test
    public void testExcel6() throws IOException {
        //创建Excel工作薄对象
        HSSFWorkbook workbook = new HSSFWorkbook();
        //创建Excel工作表对象
        HSSFSheet sheet = workbook.createSheet("工作表");
        sheet.setColumnWidth(0, 256 * 35);
        //创建行的单元格，从0开始
        HSSFRow row = sheet.createRow(0);
        //创建单元格
        HSSFCell cell = row.createCell(0);
        //设置值
        cell.setCellValue(new Date());
        //创建单元格样式
        HSSFCellStyle style = workbook.createCellStyle();
        style.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));
        cell.setCellStyle(style);

        //设置保留2位小数--使用Excel内嵌的格式
        HSSFCell cell1 = row.createCell(1);
        cell1.setCellValue(12.3456789);
        style = workbook.createCellStyle();


        //设置货币格式--使用自定义的格式
        HSSFCell cell2 = row.createCell(2);
        cell2.setCellValue(12345.6789);
        style = workbook.createCellStyle();
        style.setDataFormat(workbook.createDataFormat().getFormat("￥#,##0"));
        cell2.setCellStyle(style);

        //设置百分比格式--使用自定义的格式
        HSSFCell cell3 = row.createCell(3);
        cell3.setCellValue(0.123456789);
        style = workbook.createCellStyle();
        style.setDataFormat(workbook.createDataFormat().getFormat("0.00%"));
        cell3.setCellStyle(style);
        //设置中文大写格式--使用自定义的格式
        HSSFCell cell4 = row.createCell(4);
        cell4.setCellValue(12345);
        style = workbook.createCellStyle();
        style.setDataFormat(workbook.createDataFormat().getFormat("[DbNum2][$-804]0"));
        cell4.setCellStyle(style);
        //设置科学计数法格式--使用自定义的格式
        HSSFCell cell5 = row.createCell(5);
        cell5.setCellValue(12345);
        style = workbook.createCellStyle();
        style.setDataFormat(workbook.createDataFormat().getFormat("0.00E+00"));
        cell5.setCellStyle(style);

        //文档输出
        FileOutputStream out = new FileOutputStream(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()).toString() + ".xls");
        workbook.write(out);
        out.close();
    }

}
