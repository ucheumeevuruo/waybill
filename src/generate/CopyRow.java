package generate;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hp
 */
//import src.ClonedStyleFactory.newStyleBasedOn;
//import java.ClonedStyleFactory.newStyleBasedOn;
//import static ClonedStyleFactory.newStyleBasedOn;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
//import InvoiceApplication.ClonedStyleFactory.newStyleBasedOn;

public class CopyRow {

    /**
     * Copies a row from a row index on the given workbook and sheet to another row index. If the destination row is
     * already occupied, shift all rows down to make room.
     *
     * @param workbook
     * @param worksheet
     * @param from
     * @param to
     */
    public static void copyRow(Workbook workbook, Sheet worksheet,  Integer from, Integer to) {
        Row sourceRow = worksheet.getRow(from);
        Row newRow = worksheet.getRow(to);

        
        if (alreadyExists(newRow))
            worksheet.shiftRows(to, worksheet.getLastRowNum(), 1, true, true);
        else{
            newRow = worksheet.createRow(to);
        }
        for (int i = 0; i < sourceRow.getLastCellNum(); i++) {
            Cell oldCell = sourceRow.getCell(i);
            Cell newCell = newRow.createCell(i);
            if (oldCell != null) {
                copyCellStyle(workbook, oldCell, newCell);
                copyCellComment(oldCell, newCell);
                copyCellHyperlink(oldCell, newCell);
                copyCellDataTypeAndValue(oldCell, newCell);
            }
        }
        copyAnyMergedRegions(worksheet, sourceRow, newRow);
    }

    private static void copyCellStyle(Workbook workbook, Cell oldCell, Cell newCell) {
        //ClonedStyleFactory c = new ClonedStyleFactory();
        newCell.setCellStyle(oldCell.getCellStyle());
    }

    private static void copyCellComment(Cell oldCell, Cell newCell) {
        if (newCell.getCellComment() != null)
            newCell.setCellComment(oldCell.getCellComment());
    }

    private static void copyCellHyperlink(Cell oldCell, Cell newCell) {
        if (oldCell.getHyperlink() != null)
            newCell.setHyperlink(oldCell.getHyperlink());
    }

    private static void copyCellDataTypeAndValue(Cell oldCell, Cell newCell) {
        setCellDataType(oldCell, newCell);
        //setCellDataValue(oldCell, newCell);
    }

    private static void setCellDataType(Cell oldCell, Cell newCell) {
        newCell.setCellType(oldCell.getCellType());
    }

    private static void setCellDataValue(Cell oldCell, Cell newCell) {
        switch (oldCell.getCellType()) {
            case Cell.CELL_TYPE_BLANK:
                newCell.setCellValue(oldCell.getStringCellValue());
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                newCell.setCellValue(oldCell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_ERROR:
                newCell.setCellErrorValue(oldCell.getErrorCellValue());
                break;
            case Cell.CELL_TYPE_FORMULA:
                newCell.setCellFormula(oldCell.getCellFormula());
                break;
            case Cell.CELL_TYPE_NUMERIC:
                newCell.setCellValue(oldCell.getNumericCellValue());
                break;
            case Cell.CELL_TYPE_STRING:
                newCell.setCellValue(oldCell.getRichStringCellValue());
                break;
        }
    }

    private static boolean alreadyExists(Row newRow) {
        return newRow != null;
    }

    private static void copyAnyMergedRegions(Sheet worksheet, Row sourceRow, Row newRow) {
        for (int i = 0; i < worksheet.getNumMergedRegions(); i++){
            copyMergeRegion(worksheet, sourceRow, newRow, worksheet.getMergedRegion(i));
        }
    }

    private static void copyMergeRegion(Sheet worksheet, Row sourceRow, Row newRow, CellRangeAddress mergedRegion) {
        CellRangeAddress range = mergedRegion;
        if (range.getFirstRow() == sourceRow.getRowNum()) {
            int lastRow = newRow.getRowNum() + (range.getFirstRow() - range.getLastRow());
            CellRangeAddress newCellRangeAddress = new CellRangeAddress(newRow.getRowNum(), lastRow, range.getFirstColumn(), range.getLastColumn());
            worksheet.addMergedRegion(newCellRangeAddress);
        }
    }
    
    public static int getNbOfMergedRegions(Sheet sheet, int row)
    {
        int count = 0;
        for(int i = 0; i < sheet.getNumMergedRegions(); ++i)
        {
            CellRangeAddress range = sheet.getMergedRegion(i);
            if (range.getFirstRow() <= row && range.getLastRow() >= row)
                ++count;
        }
        return count;
    }

    
}
