/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package invoiceapplication;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;

/**
 *
 * @author hp
 */
public class CopyRowOriginal {
     private static void doMerge(Sheet worksheet,int rowIndex, int columnIndex, int rowSpan, int columnSpan){
        Cell cell = worksheet.getRow(rowIndex).getCell(columnIndex);
        CellRangeAddress range = new CellRangeAddress(rowIndex, rowIndex + rowSpan - 1, columnIndex, columnIndex + columnSpan - 1);
        worksheet.addMergedRegion(range);
        RegionUtil.setBorderTop(CellStyle.BORDER_THIN, range, worksheet, worksheet.getWorkbook());
        RegionUtil.setBorderLeft(CellStyle.BORDER_THIN, range, worksheet, worksheet.getWorkbook());
        RegionUtil.setBorderRight(CellStyle.BORDER_THIN, range, worksheet, worksheet.getWorkbook());
        RegionUtil.setBorderBottom(CellStyle.BORDER_THIN, range, worksheet, worksheet.getWorkbook());
    }
    
    private static String nameOf(Object o) {
        return o.getClass().getSimpleName();
    }
    
    public static void copyRow(Sheet worksheet, int sourceRowNum, int destRowNum) {
       // Get the source / new row
       Row newRow = worksheet.getRow(destRowNum);
       Row sourceRow = worksheet.getRow(sourceRowNum);
       
       // If the row exists in destination, push down all rows by 1 else create a new row
       if(newRow != null){
           worksheet.shiftRows(newRow.getRowNum(), worksheet.getLastRowNum(), 1, true, true);
       }else{
           newRow = worksheet.createRow(destRowNum);
       }
       copyAnyMergedRegions(worksheet, sourceRow, newRow);
    // Loops through source column to add to new row
       for(int i = 0; i < sourceRow.getLastCellNum(); i++){
           //Grab a copy of the old/new cell
           Cell oldCell = sourceRow.getCell(i);
           Cell newCell = newRow.createCell(i);
           
           // if the old cell is null jump to next cell
           if(oldCell == null){
               newCell = null;
               continue;
           }
           
           // Use old cell style
           newCell.setCellStyle(oldCell.getCellStyle());
           
           // If there is a cell comment, copy
           if(newCell.getCellComment() != null){
               newCell.setCellComment(oldCell.getCellComment());
           }
           
           // If there is a cell hyperlink, copy
           if(oldCell.getHyperlink() != null){
               newCell.setHyperlink(oldCell.getHyperlink());
           }
           
           // Set the cell data type
           newCell.setCellType(oldCell.getCellType());
           
           // Set the cell data value
           switch(oldCell.getCellType()){
               case Cell.CELL_TYPE_BLANK:
               break;
               case Cell.CELL_TYPE_BOOLEAN:
                   newCell.setCellValue(oldCell.getBooleanCellValue());
               break;
               case Cell.CELL_TYPE_FORMULA:
                   newCell.setCellValue(oldCell.getCellFormula());
               break;
               case Cell.CELL_TYPE_NUMERIC:
                   newCell.setCellValue(oldCell.getNumericCellValue());
               break;
               case Cell.CELL_TYPE_STRING:
                   newCell.setCellValue(oldCell.getStringCellValue());
               break;
           }
       }
    }
    
    private static boolean checkIfRowIsEmpty(Row row){
        if(row == null)
            return true;
        if(row.getLastCellNum() <= 0)
            return true;
        boolean isEmptyRow = true;
        for(int cellNum = row.getFirstCellNum(); cellNum < row.getLastCellNum(); cellNum++){
            Cell cell = row.getCell(cellNum);
            if(cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK ){
                isEmptyRow = false;
            }
        }
        return isEmptyRow;
    }
    
     private static void copyAnyMergedRegions(Sheet worksheet, Row sourceRow, Row newRow) {
         System.out.println(worksheet.getNumMergedRegions());
        for (int i = 0; i < worksheet.getNumMergedRegions(); i++){
            copyMergeRegion(worksheet, sourceRow, newRow, worksheet.getMergedRegion(i));
        }
    }

    private static void copyMergeRegion(Sheet worksheet, Row sourceRow, Row newRow, CellRangeAddress mergedRegion) {
        CellRangeAddress range = mergedRegion;
        if (range.getFirstRow() == sourceRow.getRowNum()) {
            //System.out.println(range.formatAsString());
            int lastRow = newRow.getRowNum() + (range.getFirstRow() - range.getLastRow());
            worksheet.addMergedRegion(new CellRangeAddress(newRow.getRowNum(), lastRow, range.getFirstColumn(), range.getLastColumn()));
        }
    }
}
