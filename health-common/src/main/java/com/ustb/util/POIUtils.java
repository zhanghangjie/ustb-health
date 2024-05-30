package com.ustb.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class POIUtils {
    //定义读取数据的格式
    private final static String xls = "xls";
    private final static String xlsx = "xlsx";
    private final static String DATE_FORMAT = "yyyy/MM/dd";

    //读取excel文件
    public static List<String[]> readExcel(MultipartFile file) throws IOException {
        //检查文件
        checkFile(file);
        //获取工作簿对象
        Workbook workbook = getWorkbook(file);
        //创建List对象
        List<String[]> list = new ArrayList<>();
        if(workbook!=null){
            //循环当前excel文件中所有的sheet页
            for(int sheetNum = 0;sheetNum<workbook.getNumberOfSheets();sheetNum++){
                //获取当前sheet页中的对象
                Sheet sheet = workbook.getSheetAt(sheetNum);
                if(sheet == null){
                    continue;
                }
                //获取当前sheet页的开始行
                int firstRowNum = sheet.getFirstRowNum();
                //获取当前sheet的结束行
                int lastRowNum = sheet.getLastRowNum();
                //循环除了第一行外的所有行 第一行是名称不是数据
                for(int rowNum = firstRowNum+1;rowNum<=lastRowNum;rowNum++){
                    //获取当前行的对象
                    Row row = sheet.getRow(rowNum);
                    if(row == null){
                        continue;
                    }
                    //获取当前行的开始列
                    short firstCellNum = row.getFirstCellNum();
                    //获取当前行的结束列
                    short lastCellNum = row.getLastCellNum();
                    //把每一行的数据放在String类型的数组中,数组的长度为lastCellNum
                    String [] cells = new String[lastCellNum];
                    //循环当前行
                    for(int cellNum=firstCellNum;cellNum<lastCellNum;cellNum++){
                        Cell cell = row.getCell(cellNum);
                        //获取单元格中的数据
                        cells[cellNum] = getCellValue(cell);
                    }
                    list.add(cells);
                }
            }
            workbook.close();
        }
        return list;
    }

    //校验文件是否合法
    public static void checkFile(MultipartFile file) throws IOException {
        //判断文件是否存在
        if(null == file){
            throw new FileNotFoundException("文件不存在");
        }
        //获取文件名
        String filename = file.getOriginalFilename();
        //判断文件是否是excel文件
        if(!filename.endsWith(xls)&&!filename.endsWith(xlsx)){
            throw new IOException("当前文件不是excel文件");
        }
    }

    public static Workbook getWorkbook(MultipartFile file){
        //获取文件名
        String filename = file.getOriginalFilename();
        //创建工作簿对象
        Workbook workbook = null;
        try {
            InputStream is = file.getInputStream();
            if(filename.endsWith(xls)){
                workbook = new HSSFWorkbook(is);
            }else if(filename.endsWith(xlsx)){
                workbook = new XSSFWorkbook(is);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return workbook;
    }

    //获取单元格中的数据
    public static String getCellValue(Cell cell){
        String cellValue = "";
        if(cell == null){
            return cellValue;
        }
        //如果单元格内的数据是时间类型，需要按"**/**/**"的格式单独处理
        String dataFormatString = cell.getCellStyle().getDataFormatString();
        if(dataFormatString.equals("m/d/yy")){
            cellValue = new SimpleDateFormat(DATE_FORMAT).format(cell.getDateCellValue());
            return cellValue;
        }
        //如果是数字，会出现1-》1.0,要将其设置为单元格形式(string)
        if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
            cell.setCellType(Cell.CELL_TYPE_STRING);
        }
        //判断数据的类型
        switch (cell.getCellType()){
            case Cell.CELL_TYPE_NUMERIC:
                cellValue = String.valueOf(cell.getNumericCellValue());
                break;
            case Cell.CELL_TYPE_STRING:
                cellValue = String.valueOf(cell.getStringCellValue());
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_FORMULA:
                cellValue = String.valueOf(cell.getCellFormula());
                break;
            case Cell.CELL_TYPE_BLANK:
                cellValue = "";
                break;
            case Cell.CELL_TYPE_ERROR:
                cellValue = "非法字符";
                break;
            default:
                cellValue = "未知类型";
                break;
        }
        return cellValue;

    }
}
