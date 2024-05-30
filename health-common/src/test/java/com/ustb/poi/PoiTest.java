package com.ustb.poi;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

//前提：项目中已经导入了poi的依赖
public class PoiTest {
    //读取文件
    //@Test
    public void readFile() throws IOException {
        //获取工作簿对象
        XSSFWorkbook workbook = new XSSFWorkbook("D://test.xlsx");
        //获取工作表(sheet)
        XSSFSheet sheet = workbook.getSheetAt(0);
        //获取每一行对象
        for (Row row : sheet) {
            //获取每个单元格的对象
            for (Cell cell : row) {
                //获取单元格中的值
                String value = cell.getStringCellValue();
                System.out.println(value);
            }
        }
    }

    //数据写入文件中
    //@Test
    public void writeFile() throws IOException {
        //创建工作簿对象
        XSSFWorkbook workbook = new XSSFWorkbook();
        //创建工作表sheet
        XSSFSheet sheet = workbook.createSheet("ustbStudentInfo");
        //创建行对象
        XSSFRow row = sheet.createRow(0);
        //创建单元格对象并且赋值  column为列
        row.createCell(0).setCellValue("姓名");
        row.createCell(1).setCellValue("性别");
        row.createCell(2).setCellValue("籍贯");
        XSSFRow row2 = sheet.createRow(1);
        //创建单元格对象并且赋值  column为列
        row2.createCell(0).setCellValue("张三");
        row2.createCell(1).setCellValue("男");
        row2.createCell(2).setCellValue("浙江");
        XSSFRow row3 = sheet.createRow(2);
        //创建单元格对象并且赋值  column为列
        row3.createCell(0).setCellValue("李四");
        row3.createCell(1).setCellValue("女");
        row3.createCell(2).setCellValue("北极");
        //把工作簿对象通过IO流的方式写入到磁盘中
        FileOutputStream outputStream = new FileOutputStream("D://studentInfo.xlsx");
        workbook.write(outputStream);
        outputStream.close();
        workbook.close();
    }
}
