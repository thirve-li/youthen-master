package com.youthen.master.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * apache poi
 * 
 * @author chenxh
 *         excel读取功能
 */

public class ExcelUtil {

    /**
     * http://www.cnblogs.com/caiyun/archive/2012/08/22/2650239.html
     * 支持最大列256，最大行65535
     */

    private final short inputColorIdx = HSSFColor.LIGHT_GREEN.index;// 输入框颜色索引42

    private final short resultColorIdx = HSSFColor.CORAL.index;// 结果框颜色索引29

    private final String protectPwd = "abc123";// 受保护excel密码

    private final String[] charArray = new String[] {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
            "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "AA", "AB", "AC", "AD", "AE", "AF", "AG",
            "AH", "AI", "AJ", "AK", "AL", "AM", "AN", "AO", "AP", "AQ", "AR", "AS", "AT", "AU", "AV", "AW", "AX", "AY",
            "AZ"};

    private String excelFilePath; // excel路径

    private Workbook wb; // excel对象

    private String editCellStr = ""; // excel可编辑cell

    private String resultCellStr = ""; // excel返回结果cell

    private final Map<String, String> excelCelInfo = new HashMap<String, String>(); // excel信息

    private final Map<String, Cell> excelCells = new HashMap<String, Cell>(); // excel单元格信息

    public Map<String, Cell> getExcelCells() {
        return this.excelCells;
    }

    public String getExcelFilePath() {
        return this.excelFilePath;
    }

    public Map<String, String> getExcelCelInfo() { // 获取excel单元格信息
        return this.excelCelInfo;
    }

    public String getEditCellStr() {
        return this.editCellStr;
    }

    public String getResultCellStr() {
        return this.resultCellStr;
    }

    public Workbook getWb() {
        return this.wb;
    }

    public String[] getCharArray() {
        return this.charArray;
    }

    /**
     * 获取单元格内文本信息
     * 
     * @param cell
     * @return
     * @date 2013-5-8
     */
    public String getValueFromCell(final Cell cell) {
        if (cell == null) {
            return "";
        }
        String value = null;
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_NUMERIC: // 数字
                if (HSSFDateUtil.isCellDateFormatted(cell)) { // 如果是日期类型
                    value = this.dateFormat.format(cell.getDateCellValue());
                } else value = String.valueOf(cell.getNumericCellValue());
                break;
            case Cell.CELL_TYPE_STRING: // 字符串
                value = cell.getStringCellValue();
                break;
            case Cell.CELL_TYPE_FORMULA: // 公式
                switch (cell.getCachedFormulaResultType()) {
                    case Cell.CELL_TYPE_NUMERIC:
                        // 用数字方式获取公式结果，根据值判断是否为日期类型
                        final double numericValue = cell.getNumericCellValue();
                        if (HSSFDateUtil.isCellDateFormatted(cell)) {
                            value = this.dateFormat.format(cell.getDateCellValue());
                        } else value = String.valueOf(numericValue);
                        break;
                    case Cell.CELL_TYPE_STRING:
                        value = cell.getStringCellValue();
                        break;
                    case Cell.CELL_TYPE_BOOLEAN: // Boolean
                        value = String.valueOf(cell.getBooleanCellValue());
                        break;
                    case Cell.CELL_TYPE_BLANK: // 空白
                        value = "";
                        break;
                    case Cell.CELL_TYPE_ERROR: // Error，返回错误码
                        value = String.valueOf(cell.getErrorCellValue());
                        break;
                    default:
                        value = "";
                        break;
                }
                break;
            case Cell.CELL_TYPE_BLANK: // 空白
                value = "";
                break;
            case Cell.CELL_TYPE_BOOLEAN: // Boolean
                value = String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_ERROR: // Error，返回错误码
                value = String.valueOf(cell.getErrorCellValue());
                break;
            default:
                value = "";
                break;
        }

        // 使用[]记录坐标
        return value;
    }

    // 判断是否是数字类型
    public boolean isNum(final String str) {
        return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
    }

    // 判断是否是boolean类型
    public boolean isBoolean(final String str) {

        return false;
    }

    // 判断是否是日期类型
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public boolean isDate(final String str) {
        try {
            this.dateFormat.parse(str);
            return true;
        } catch (final ParseException e) {
            return false;
        }
    }

    public static void main(final String[] args) {
        final ExcelUtil excelUtil = new ExcelUtil();
        System.out.println(excelUtil.isNum(""));

        System.out.println(excelUtil.isDate("2013-0- 0:1:1"));
    }

    public void setCellVal(final Cell cell, final Object objVal) throws ParseException {
        String valObj = "";
        if (objVal instanceof String[]) {
            valObj = ((String[]) objVal)[0];
        } else {
            valObj = String.valueOf(objVal);
        }
        if (cell != null) {
            if (cell.getCellType() != Cell.CELL_TYPE_FORMULA) {
                if (valObj == null || valObj.trim() == "") {
                    cell.setCellValue("");
                } else if (isNum(valObj)) {
                    System.out.println("-----------|" + valObj + "|-----------");
                    // cell.setCellValue(Double.parseDouble(valObj));
                    cell.setCellValue(valObj);
                } else if (isBoolean(valObj)) {
                    cell.setCellValue(Boolean.parseBoolean(valObj));
                } else if (isDate(valObj)) {
                    cell.setCellValue(this.dateFormat.parse(valObj));
                } else {
                    cell.setCellValue(valObj);
                }
            } else {
                switch (cell.getCachedFormulaResultType()) {
                    case Cell.CELL_TYPE_NUMERIC:
                        // 用数字方式获取公式结果，根据值判断是否为日期类型
                        if (HSSFDateUtil.isCellDateFormatted(cell)) {
                            cell.setCellValue(this.dateFormat.parse(valObj));
                        } else cell.setCellValue(Double.parseDouble(valObj));
                        break;
                    case Cell.CELL_TYPE_STRING:
                        cell.setCellValue(valObj);
                        break;
                    case Cell.CELL_TYPE_BOOLEAN: // Boolean
                        cell.setCellValue(Boolean.parseBoolean(valObj));
                        break;
                    case Cell.CELL_TYPE_BLANK: // 空白
                        cell.setCellValue("");
                        break;
                    case Cell.CELL_TYPE_ERROR: // Error，返回错误码
                        cell.setCellValue("error");
                        break;
                    default:
                        cell.setCellValue("");
                        ;
                        break;
                }
            }
        }
    }

    public ExcelUtil() {
    };

    // 构造函数，初始化excle信息
    public ExcelUtil(final String paramFilePath) throws Exception {
        if (paramFilePath != null && paramFilePath.length() > 0) {
            this.excelFilePath = paramFilePath;
            final String filePath = paramFilePath;
            if (filePath.trim().toLowerCase().endsWith("xls")) {
                this.wb = new HSSFWorkbook(new FileInputStream(filePath));
            } else if (filePath.trim().toLowerCase().endsWith("xlsx")) {
                this.wb = new XSSFWorkbook(new FileInputStream(filePath));
            } else {
                throw new IllegalArgumentException("不支持除：xls/xlsx以外的文件格式!!!");
            }
            if (this.wb == null) {
                throw new RuntimeException("excel error!!!");
            }
            // 获到第一个工作表对象
            final Sheet sheet = this.wb.getSheetAt(0);
            for (int rowN = sheet.getFirstRowNum(); rowN <= sheet.getLastRowNum(); rowN++) {
                final Row row = sheet.getRow(rowN);
                if (row == null) continue;
                for (int colN = row.getFirstCellNum(); colN <= row.getLastCellNum(); colN++) {
                    final Cell cell = row.getCell(colN);
                    if (cell == null) continue;
                    final String cellName = this.charArray[colN] + (rowN + 1);
                    this.excelCelInfo.put(cellName, getValueFromCell(cell));
                    this.excelCells.put(cellName, cell);
                    final short cellColorIdx = cell.getCellStyle().getFillForegroundColor();
                    if (cellColorIdx == this.inputColorIdx) {
                        this.editCellStr += "_" + cellName;
                    } else if (cellColorIdx == this.resultColorIdx) {
                        this.resultCellStr += "_" + cellName;
                    }
                }
            }
            if (this.editCellStr.length() > 0) {
                this.editCellStr = this.editCellStr.substring(1);
            }
            if (this.resultCellStr.length() > 0) {
                this.resultCellStr = this.resultCellStr.substring(1);
            }
        }
    }

    // 把excel转化成模板
    public void convertToModel() throws Exception {
        final FileOutputStream fileOut = new FileOutputStream(this.excelFilePath);
        // 获取可编辑单元格名称
        final String[] editCellStrArray = this.editCellStr.split("_");
        final String[] resultCellStrArray = this.resultCellStr.split("_");

        // 指定单元格是否锁定
        final CellStyle inputCellStyle = this.wb.createCellStyle();
        inputCellStyle.setLocked(false);
        inputCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        inputCellStyle.setFillForegroundColor(this.inputColorIdx);
        for (int i = 0; i < editCellStrArray.length; i++) {
            final Cell editCell = this.excelCells.get(editCellStrArray[i]);
            if (editCell != null && editCell.getCellType() != Cell.CELL_TYPE_FORMULA) editCell
                    .setCellStyle(inputCellStyle);
        }
        final CellStyle resultCellStyle = this.wb.createCellStyle();
        resultCellStyle.setLocked(false);
        resultCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        resultCellStyle.setFillForegroundColor(this.resultColorIdx);
        for (int i = 0; i < resultCellStrArray.length; i++) {
            final Cell resultCell = this.excelCells.get(resultCellStrArray[i]);
            if (resultCell != null && resultCell.getCellType() != Cell.CELL_TYPE_FORMULA) resultCell
                    .setCellStyle(resultCellStyle);
        }
        // 设置excel受保护
        final int sheetNum = this.wb.getNumberOfSheets();
        for (int i = sheetNum - 1; i >= 0; i--) {
            final Sheet curSheet = this.wb.getSheetAt(i);
            if (!curSheet.getProtect()) curSheet.protectSheet(this.protectPwd);
        }

        this.wb.write(fileOut);
        fileOut.close();
    }

}
