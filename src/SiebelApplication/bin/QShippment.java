/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SiebelApplication.bin;

import SiebelApplication.MyLogging;
import SiebelApplication.SiebelServiceExtended;
import com.siebel.data.SiebelBusComp;
import com.siebel.data.SiebelDataBean;
import com.siebel.data.SiebelException;
import com.siebel.data.SiebelPropertySet;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

/**
 *
 * @author Adeyemi
 */
public class QShippment extends SiebelServiceExtended implements IQuote
{
    
    private static SiebelPropertySet set;
    private static String Id;
    private String searchSpec;
    private String searchKey;
    private String value = "";
    List<Map<String, String>> quoteItem;
    
    public QShippment(SiebelDataBean conn)
    {
        super(conn);
    }
    
    @Override
    public List<Map<String, String>> getOrderItems(String id) throws SiebelException
    {
        List<Map<String, String>> listFinal;
        findOrderItem(id);
        listFinal = orderItem(searchSpec);
        MyLogging.log(Level.INFO,"Creating siebel objects Customer: " + listFinal);
        return listFinal;
    }
    
    private void findOrderItem(String quote_id) throws SiebelException
    {
        Id = quote_id;
        
        set = new SiebelPropertySet();
        set.setProperty("Account", "2");
        this.value = "Order Number";
        searchKey = "Order Number";
        this.setSField(set);
        quoteItem = this.getSField("Order Entry", "Order Entry - Line Items", this);
            
        //return quoteItem;
    }
    
    private List<Map<String, String>> orderItem(String account_id) throws SiebelException
    {
        Id = account_id;
        System.out.println("Account: " + account_id);
        set = new SiebelPropertySet();
        set.setProperty("Scheduled Delivery Date", "3");
        set.setProperty("Waybill Number", "3");
        this.value = "";
        searchKey = "Order Id";
        this.setSField(set);
        quoteItem = this.getSField("Order Entry", "FS Shipment", this);
        return quoteItem;
    }
    

    @Override
    public void searchSpec(SiebelBusComp sbBC) throws SiebelException 
    {
        sbBC.setSearchSpec(searchKey, Id);  
    }
    
    @Override
    public void getExtraParam(SiebelBusComp sbBC)
    {
        try 
        {
            if(!"".equals(this.value))
            {
                this.searchSpec = sbBC.getFieldValue(this.value);
            }
        } 
        catch (SiebelException ex) {
            MyLogging.log(Level.SEVERE, "Caught Exception: " + ex.getMessage());
        }
    }
}
