/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package generate;

import SiebelApplication.bin.IQuote;
import invoiceapplication.IKey;
import invoiceapplication.ProductKey;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;


/**
 *
 * @author hp
 */
public class InvoiceExcel extends AExcel{
    private final char lastColumn;
    private String total;
    
    public InvoiceExcel()
    {
        super();
        this.lastColumn = ((char)'A' + 9);
    }

    public InvoiceExcel(Workbook book, Sheet sheet) {
        super(book, sheet);
        this.lastColumn = ((char)'A' + 9);
    }
    
    public InvoiceExcel(Workbook book, Sheet sheet, int startRow) {
        super(book, sheet, startRow);
        this.lastColumn = ((char)'A' + 9);
    }
    /**
     *
     * @param book
     * @param sheet
     * @param qM
     * @param iKey
     * @throws java.lang.Exception
     */ 
    @Override
    public void createCellFromList( IQuote qM, IKey iKey, Workbook book, Sheet sheet) throws Exception
    {
        workbook = book;
        worksheet = sheet;
        createCell(qM, iKey);
        //Total(iKey);
    }
    
    @Override
    public void createCellFromList(IQuote qM, IKey iKey) throws Exception
    {
        createCell(qM, iKey);
        //Total(iKey);
    }
    
    public String getTotal(){
        return this.total;
    }
    
    public void Total(IKey iKey)
    {
        if(rowCount % 1 == 0)
        {
            
        }
        int sumFirstRow = (firstRow), sumLastRow = firstRow + (rowCount) + 1;
        CellReference cr = new CellReference(lastColumn + String.valueOf(sumLastRow));
        if(iKey instanceof ProductKey){
            sheetrow = worksheet.getRow(cr.getRow());
            sheetcell = sheetrow.getCell(cr.getCol());
                XGenerator.doMerge(worksheet, sumLastRow - 1, 7, 1, 2, false);
            if(sumFirstRow != sumLastRow){
                this.total = "SUM(SUM("+lastColumn+sumFirstRow+":"+lastColumn+(sumLastRow-1)+")+0.00)";
            }else{
                this.total = "SUM(0.00)";
            }
            sheetcell.setCellFormula(this.total);
        }
    }
}   
