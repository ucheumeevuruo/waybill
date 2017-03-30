/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import Helper.HelperAP;
import SiebelApplication.ApplicationsConnection;
import SiebelApplication.MyLogging;
import SiebelApplication.bin.QAddress;
import SiebelApplication.bin.QShippment;
import SiebelApplication.bin.QParts;
import com.siebel.data.SiebelDataBean;
import com.siebel.data.SiebelPropertySet;
import com.siebel.eai.SiebelBusinessService;
import com.siebel.eai.SiebelBusinessServiceException;
import generate.Attachment;
import generate.CustomerRecord;
import generate.XGenerator;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import generate.InvoiceExcel;
import invoiceapplication.ContactKey;
import invoiceapplication.ProductKey;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Level;
import org.apache.poi.EncryptedDocumentException;

/**
 *
 * @author gbege
 */
public class ApachePOIExcelWrite  extends SiebelBusinessService{
    private String inputFile = "";
    
    private String order_id;
    
    private String quote_number;
    
    private final StringWriter error_txt = new StringWriter();
    
    private FileInputStream input_document;

    public ApachePOIExcelWrite() {
        this.quote_number = "";
        this.input_document = null;
    }
    
    @Override
    public void doInvokeMethod(String MethodName, SiebelPropertySet inputs, SiebelPropertySet outputs) throws SiebelBusinessServiceException {
        if(MethodName.equalsIgnoreCase("GenerateExcelDoc"))
        {
            try 
            {
                //
                //IProperties AP = new ApplicationProperties();
                SiebelDataBean conn = ApplicationsConnection.connectSiebelServer();
                //Get excel path
                System.out.println(HelperAP.getInvoiceTemplate());
                inputFile = HelperAP.getInvoiceTemplate();
                //Read Excel document first
                input_document = new FileInputStream(new File(inputFile));
                // Convert it into a POI object
                Workbook my_xlsx_workbook = WorkbookFactory.create(input_document);
                // Read excel sheet that needs to be updated
                Sheet my_worksheet = my_xlsx_workbook.getSheet("Sheet1");
                // Declare a Cell object
                this.order_id = inputs.getProperty("QuoteId");
                this.quote_number = inputs.getProperty("QuoteNum");
                
                CustomerRecord customerInfo = new CustomerRecord(my_xlsx_workbook, my_worksheet, 3);
                customerInfo.setQuoteId(this.order_id);
                customerInfo.createCellFromList(new QShippment(conn), new ContactKey());
                customerInfo.setStartRow(8);
                customerInfo.createCellFromList(new QAddress(conn), new ContactKey());
                

                InvoiceExcel parts;

                int startRowAt = 17;
                parts = new InvoiceExcel(my_xlsx_workbook, my_worksheet);

                //
                parts.setStartRow(startRowAt);
                parts.setQuoteId(order_id);
                parts.createCellFromList(new QParts(conn), new ContactKey());
                my_xlsx_workbook.setForceFormulaRecalculation(true);
                input_document.close();
                XGenerator.doCreateBook(my_xlsx_workbook, "weststar_" + this.quote_number.replace(" ", "_"));
                Attachment a = new Attachment(conn, "Quote", "Quote Attachment");
                String filepath = XGenerator.getProperty("filepath");
                String filename = XGenerator.getProperty("filename");
                
                //Attach the file to siebel
                a.Attach(filepath, 
                    filename, 
                    Boolean.FALSE, 
                    order_id
                );
                
                boolean logoff = conn.logoff();
                my_xlsx_workbook.close();
                System.out.println("Done");
                outputs.setProperty("status", "success");
            } 
            catch (FileNotFoundException ex) 
            {
                ex.printStackTrace(new PrintWriter(error_txt));
                MyLogging.log(Level.SEVERE, "Caught File Not Found Exception: " + ex.getMessage() + error_txt.toString());
                outputs.setProperty("status", "failed");
                outputs.setProperty("error_message", error_txt.toString());
            } 
            catch (IOException ex) 
            {
                ex.printStackTrace(new PrintWriter(error_txt));
                MyLogging.log(Level.SEVERE, "Caught IO Exception: " + ex.getMessage() + error_txt.toString());
                outputs.setProperty("status", "failed");
                outputs.setProperty("error_message", error_txt.toString());
            } 
            catch (InvalidFormatException ex) 
            {
                ex.printStackTrace(new PrintWriter(error_txt));
                MyLogging.log(Level.SEVERE, "Caught Invalid Format Exception: " + ex.getMessage() + error_txt.toString());
                outputs.setProperty("status", "failed");
                outputs.setProperty("error_message", error_txt.toString());
            } 
            catch (EncryptedDocumentException ex) 
            {
                ex.printStackTrace(new PrintWriter(error_txt));
                MyLogging.log(Level.SEVERE, "Caught Encrypted Document Exception: " + ex.getMessage() + error_txt.toString());
                outputs.setProperty("status", "failed");
                outputs.setProperty("error_message", error_txt.toString());
            } 
            catch (Exception ex) 
            {
                ex.printStackTrace(new PrintWriter(error_txt));
                MyLogging.log(Level.SEVERE, "Caught Exception: " + ex.getMessage() + error_txt.toString());
                outputs.setProperty("status", "failed");
                outputs.setProperty("error_message", error_txt.toString());
            }
        }
    }
}