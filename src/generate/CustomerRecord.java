/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package generate;

import SiebelApplication.bin.IQuote;
import invoiceapplication.IKey;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 *
 * @author hp
 */
public class CustomerRecord  extends AExcel{
    
    public CustomerRecord()
    {
        this.firstRow = 0;
        this.nextRow = 0;
    }

    public CustomerRecord(Workbook book, Sheet sheet) {
        super(book, sheet);
    }
    
    public CustomerRecord(Workbook book, Sheet sheet, int startRow)
    {
        super(book, sheet, startRow);
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
    }
    
    @Override
    public void createCellFromList(IQuote qM, IKey iKey) throws Exception
    {
        createCell(qM, iKey);
    }
}