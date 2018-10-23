/**
 * Copyright (C) 2018 - present by Marc Henrard.
 */
package marc.henrard.murisq.basics.data.export;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.google.common.collect.ImmutableList;
import com.opengamma.strata.basics.currency.CurrencyAmount;
import com.opengamma.strata.basics.currency.MultiCurrencyAmount;
import com.opengamma.strata.collect.array.DoubleArray;
import com.opengamma.strata.market.param.CurrencyParameterSensitivities;
import com.opengamma.strata.market.param.CurrencyParameterSensitivity;
import com.opengamma.strata.market.param.ParameterMetadata;

/**
 * Utilities to export objects in Excel sheets. Typically used in the tutorials.
 */
public class ExcelExportUtil {

  /* The muRisQ colors used in the Excel sheets */
  public static final XSSFColor MR_GREY_LIGHT = new XSSFColor(new byte[] {(byte) 224, (byte) 224, (byte) 224 });
  public static final XSSFColor MR_BLUE_LIGHT = new XSSFColor(new byte[] {(byte) 0, (byte) 191, (byte) 255 });
  public static final XSSFColor MR_BLUE_DARK = new XSSFColor(new byte[] {(byte) 16, (byte) 16, (byte) 96 });
  
  public static final int COLUMN_WIDTH = 15*256;  
  
  /**
   * Add a sheet with the name, PV and rate of each trade
   * 
   * @param names  the trade's names
   * @param rates  the trade's rates
   * @param pv  the trade's present values
   * @param sheetName  the name of the sheet to be added
   * @param workbook  the workbook to which the sheet is added
   * @param headerStyle  the cell style for the headers
   * @param labelStyle  the cell style for the labels
   * @param amountStyle  the cell style for the amounts
   */
  public static void addSheet(
      List<String> names,
      List<Double> rates,
      List<MultiCurrencyAmount> pv, 
      String sheetName, 
      XSSFWorkbook workbook,
      CellStyle headerStyle,
      CellStyle labelStyle,
      CellStyle amountStyle,
      CellStyle rateStyle) {
    
    String safeName = WorkbookUtil.createSafeSheetName(sheetName);
    Sheet sheet = workbook.createSheet(safeName);
    Row row0 = sheet.createRow(0);
    row0.createCell(0).setCellValue("ID");
    row0.getCell(0).setCellStyle(headerStyle);
    row0.createCell(1).setCellValue("Rate");
    row0.getCell(1).setCellStyle(headerStyle);
    row0.createCell(2).setCellValue("PresentValue");
    row0.getCell(2).setCellStyle(headerStyle);
    for (int loopinstrument = 0; loopinstrument < pv.size(); loopinstrument++) {
      Row row = sheet.createRow(1 + loopinstrument);
      row.createCell(0).setCellValue(names.get(loopinstrument));
      row.createCell(1).setCellValue(rates.get(loopinstrument));
      row.getCell(1).setCellStyle(rateStyle);
      int j = 0;
      for (CurrencyAmount ca : pv.get(loopinstrument).getAmounts()) {
        row.createCell(2 + 2 * j).setCellValue(ca.getCurrency().toString());
        row.createCell(3 + 2 * j).setCellValue(ca.getAmount());
        row.getCell(3 + 2 * j).setCellStyle(amountStyle);
        if (row0.getCell(2 + 2 * j) == null) {
          row0.createCell(2 + 2 * j);
        }
        row0.getCell(2 + 2 * j).setCellStyle(headerStyle);
        sheet.setColumnWidth(2 + 2 * j, COLUMN_WIDTH);
        if (row0.getCell(3 + 2 * j) == null) {
          row0.createCell(3 + 2 * j);
        }
        row0.getCell(3 + 2 * j).setCellStyle(headerStyle);
        sheet.setColumnWidth(3 + 2 * j, COLUMN_WIDTH);
        j++;
      }
    }
  }
  
  /**
   * Add a sheet with the curve sensitivities.
   * 
   * @param sensitivity  the sensitivity
   * @param sheetName  the name of the sheet to be added
   * @param workbook  the workbook to which the sheet is added
   * @param headerStyle  the cell style for the headers
   * @param labelStyle  the cell style for the labels
   * @param amountStyle  the cell style for the amounts
   */
  public static void addSheet(
      CurrencyParameterSensitivities sensitivity, 
      String sheetName, 
      XSSFWorkbook workbook,
      CellStyle headerStyle,
      CellStyle labelStyle,
      CellStyle deltaStyle) {
    
    String safeName = WorkbookUtil.createSafeSheetName(sheetName);
    Sheet sheet = workbook.createSheet(safeName);
    ImmutableList<CurrencyParameterSensitivity> sl = sensitivity.getSensitivities();
    // Create rows
    for (int loopcurve = 0; loopcurve < sl.size(); loopcurve++) {
      sheet.createRow(0);
      sheet.createRow(1);
      DoubleArray sv = sl.get(loopcurve).getSensitivity();
      for (short i = 0; i < sv.size(); i++) {
        sheet.createRow(i + 2);
      }
    }
    // Fill cells
    for (int loopcurve = 0; loopcurve < sl.size(); loopcurve++) {
      Row row0 = sheet.getRow((short) 0);
      row0.createCell(2 * loopcurve).setCellValue(sl.get(loopcurve).getMarketDataName().toString());
      row0.getCell(2 * loopcurve).setCellStyle(headerStyle);
      row0.createCell(2 * loopcurve + 1);
      row0.getCell(2 * loopcurve + 1).setCellStyle(headerStyle);
      Row row1 = sheet.getRow((short) 1);
      row1.createCell(2 * loopcurve + 1).setCellValue(sl.get(loopcurve).getCurrency().toString());
      row1.getCell(2 * loopcurve + 1).setCellStyle(labelStyle);
      sheet.setColumnWidth(2 * loopcurve, COLUMN_WIDTH);
      sheet.setColumnWidth(2 * loopcurve + 1, COLUMN_WIDTH);
      ImmutableList<ParameterMetadata> m = sl.get(loopcurve).getParameterMetadata();
      DoubleArray sv = sl.get(loopcurve).getSensitivity();
      if (m.size() == sv.size()) {
        for (short i = 0; i < sv.size(); i++) {
          Row row = sheet.getRow(i + 2);
          row.createCell(2 * loopcurve + 0).setCellValue(m.get(i).getLabel());
          row.getCell(2 * loopcurve + 0).setCellStyle(labelStyle);
          row.createCell(2 * loopcurve + 1).setCellValue(sv.get(i));
          row.getCell(2 * loopcurve + 1).setCellStyle(deltaStyle);
        }
      } else {
        for (short i = 0; i < sv.size(); i++) {
          Row row = sheet.getRow(i + 2);
          row.createCell(2 * loopcurve + 0).setCellValue("x");
          row.getCell(2 * loopcurve + 0).setCellStyle(labelStyle);
          row.createCell(2 * loopcurve + 1).setCellValue(sv.get(i));
          row.getCell(2 * loopcurve + 1).setCellStyle(deltaStyle);
        }        
      }
    }
  }
  
  /**
   * Export trade's name, rate and PV and total bucketed PV01 in an Excel sheet.
   * 
   * @param ids  the trade's ids
   * @param rates  the trade's rates
   * @param pv  the trade's pv
   * @param sensitivity  the sensitivity
   * @param excelWorksheetName  the workbook file's name
   * @throws IOException
   */
  public static void export(
      List<String> ids,
      List<Double> rates,
      List<MultiCurrencyAmount> pv, 
      CurrencyParameterSensitivities sensitivity, 
      String excelWorksheetName) throws IOException {
    
    FileOutputStream fileOut;
    try {
      XSSFWorkbook wb = new XSSFWorkbook();
      // Formatting 
      XSSFFont headerFont = wb.createFont();
      headerFont.setBold(true);
      headerFont.setColor(MR_GREY_LIGHT);
      XSSFFont labelFont = wb.createFont();
      labelFont.setBold(true);
      labelFont.setColor(MR_BLUE_DARK);      
      XSSFCellStyle headerStyle = wb.createCellStyle();
      headerStyle.setAlignment(CellStyle.ALIGN_CENTER_SELECTION); 
      headerStyle.setFont(headerFont);
      headerStyle.setFillPattern((short) 1);
      headerStyle.setFillForegroundColor(MR_BLUE_DARK);
      XSSFCellStyle labelStyle = wb.createCellStyle();
      labelStyle.setFont(labelFont);
      labelStyle.setBorderLeft(CellStyle.BORDER_MEDIUM);
      labelStyle.setBorderRight(CellStyle.BORDER_THIN);
      XSSFCellStyle amountStyle = wb.createCellStyle();
      amountStyle.setAlignment(CellStyle.ALIGN_RIGHT);
      amountStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0"));
      amountStyle.setBorderRight(CellStyle.BORDER_MEDIUM);
      XSSFCellStyle rateStyle = wb.createCellStyle();
      rateStyle.setAlignment(CellStyle.ALIGN_RIGHT);
      rateStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00%"));
      rateStyle.setBorderRight(CellStyle.BORDER_MEDIUM);
      
      addSheet(ids, rates, pv, "PV", wb, headerStyle, labelStyle, amountStyle, rateStyle);
      
      addSheet(sensitivity, "Delta", wb, headerStyle, labelStyle, amountStyle);
      
      fileOut = new FileOutputStream(excelWorksheetName);
      wb.write(fileOut);
      fileOut.close();
    } catch (FileNotFoundException ex) {
      // TODO Auto-generated catch block
      ex.printStackTrace();
    }
  }

}
