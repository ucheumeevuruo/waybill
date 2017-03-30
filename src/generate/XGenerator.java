/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package generate;

import Helper.HelperAP;
import SiebelApplication.MyLogging;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;

/**
 *
 * @author Adeyemi
 */
public class XGenerator {
    private static final String EXCEL_EXT = ".xls";
    
    private static String dateFormat;
    
    private static CellRangeAddress range;
    
    private static final Map<String, String> PROPERTY_SET = new HashMap();
    
    public static void doMerge(Sheet worksheet,int rowIndex, int columnIndex, int rowSpan, int columnSpan, boolean border)
    {
        range = new CellRangeAddress(rowIndex, rowIndex + rowSpan - 1, columnIndex, columnIndex + columnSpan - 1);
        Cell cell = worksheet.getRow(rowIndex).getCell(columnIndex);
        if(CopyRow.getNbOfMergedRegions(worksheet, rowIndex + rowSpan - 1) <= 0)
        {
           worksheet.addMergedRegion(range);
        }
        if(border == true)
        {            
            RegionUtil.setBorderTop(CellStyle.BORDER_THIN, range, worksheet);
            RegionUtil.setBorderLeft(CellStyle.BORDER_THIN, range, worksheet);
            RegionUtil.setBorderRight(CellStyle.BORDER_THIN, range, worksheet);
            RegionUtil.setBorderBottom(CellStyle.BORDER_THIN, range, worksheet);
        }
    }
    
    public static void doCreateBook(Workbook my_workbook, String filename) throws IOException, Exception
    {
        dateFormat = new SimpleDateFormat("ddMMyyyyHHmmss").format(new Date());
        filename = filename + "_" + dateFormat;
        String filepath = dirpath() + filename + EXCEL_EXT;
        FileOutputStream outputStream = new FileOutputStream(filepath);
        my_workbook.write(outputStream);
        if(!dirExists(filepath))
        {
            MyLogging.log(Level.SEVERE, " Excel file " + filepath + " was not built.");
        }
        else
        {
            MyLogging.log(Level.INFO, filepath + " build successfully.");
        }
        outputStream.close();
        
        PROPERTY_SET.put("filepath", filepath);
        PROPERTY_SET.put("filename", filename);
    }
    
    public static boolean dirExists(String outputFile)
    {
        Boolean output = false;
        File file = new File(outputFile);
        if(file.exists())
        {
            output = true;
        }
        return output;
    }
    
    public static Boolean createDir(String outputFile)
    {
        Boolean output = true;
        if(!dirExists(outputFile))
        {
            File file = new File(outputFile);
            boolean mkdir = file.mkdir();
            output = mkdir;
        }
        return output;
    }
    
    /**
     *
     * @return
     * @throws Exception
     */
    public static String dirpath() throws Exception
    {
        String dirpath = "";
        dirpath = HelperAP.getGeneratedPath();
        if(!createDir(dirpath)){
            throw new Exception("Directory " + dirpath + " not found or could not be created");
        }
        return dirpath;
    }
    
    /**
     *
     * @return
     */
    public static String getExcelExt()
    {
        return EXCEL_EXT;
    }
    
    public static void setPropertySet(String K, String V){
        PROPERTY_SET.put(K, V);
    }
    
    public static String getProperty(String K)
    {
        return PROPERTY_SET.get(K);
    }
}
