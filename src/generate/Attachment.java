/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package generate;

import SiebelApplication.MyLogging;
import com.siebel.data.SiebelBusComp;
import com.siebel.data.SiebelBusObject;
import com.siebel.data.SiebelDataBean;
import com.siebel.data.SiebelException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

/**
 *
 * @author Adeyemi
 */
public class Attachment {
    private final SiebelBusObject sbBO;
    private final SiebelBusComp sbBC;
    private final static String YES = "Y";
    private final static String NO = "N";
    private String sGetFileReturn = "";
    private Map<String, String> properties = new HashMap();
    
    /**
     *
     * @param conn
     * @param BO
     * @param BC
     * @throws SiebelException
     */
    public Attachment(SiebelDataBean conn, String BO, String BC) throws SiebelException
    {
        sbBO = conn.getBusObject(BO);
        sbBC = sbBO.getBusComp(BC);
    }

    public void Attach(String sAbsoluteFileName, String sAttachmentName, Boolean cond, String quote_id) throws SiebelException, IOException
    {
        //create a new attachment record with Name = TestAttachment
        sbBC.newRecord(false);
        sbBC.setFieldValue("QuoteFileName", sAttachmentName);
        sbBC.setFieldValue("QuoteFileSrcType", "FILE");
        sbBC.setFieldValue("Quote Id", quote_id);
        sbBC.setFieldValue("QuoteFileDeferFlg", "R");
        sbBC.setFieldValue("QuoteFileDockStatFlg", "E");
        sbBC.setFieldValue("QuoteFileAutoUpdFlg", YES);
        sbBC.setFieldValue("QuoteFileDockReqFlg", NO);
        sbBC.setFieldValue("PLXQuoteAttFileType", "ESTIMATE");
        MyLogging.log(Level.INFO, "sAbsoluteFileName: "+sAbsoluteFileName);
        MyLogging.log(Level.INFO, "sAttachmentName: "+sAttachmentName);
        MyLogging.log(Level.INFO, "storeAsLink(cond): "+storeAsLink(cond));
        String[] args = new String[3];
        args[0] = sAbsoluteFileName;
        //args[0] = "/usr/app/siebel/intg/excel/webstar_1-1028K_23032017173917.xls";
        args[1] = "QuoteFileName";
        args[2] = storeAsLink(cond);
        //call CreateFile method to attach a file on the server to the Siebel
        //file system
        sGetFileReturn = sbBC.invokeMethod("CreateFile", args);
        properties.put("aGetFileReturn", sGetFileReturn);
        sbBC.writeRecord();
        if (!"Success".equals(sGetFileReturn))
        {
            throw new IOException("Error attaching file!");
        }
    }
    
    /**
     *
     * @param K
     * @return
     */
    public String getProperty(String K)
    {
        return properties.get(K);
    }
    
    /**
     * 
     * @param cond
     * @return 
     */
    private String storeAsLink(Boolean cond)
    {
        String output = NO;
        if(cond)
        {
            output = YES;
        }
        return output;
    }
}
