/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SiebelApplication.bin;

import SiebelApplication.MyLogging;
import SiebelApplication.SiebelSearch;
import SiebelApplication.SiebelServiceExtended;
import com.siebel.data.SiebelBusComp;
import com.siebel.data.SiebelDataBean;
import com.siebel.data.SiebelException;
import com.siebel.data.SiebelPropertySet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

/**
 *
 * @author Adeyemi
 */
public class QAddress extends SiebelServiceExtended implements IQuote
{
    
    private static SiebelPropertySet set;
    private static String Id;
    private String searchSpec;
    private String searchKey;
    private String value = "";
    List<Map<String, String>> quoteItem = new ArrayList();
    private SiebelSearch ss;
    
    public QAddress(SiebelDataBean conn)
    {
        super(conn);
        ss = new SiebelSearch(conn);
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
        this.getSField("Order Entry", "Order Entry - Line Items", this);
            
        //return quoteItem;
    }
    
    private List<Map<String, String>> orderItem(String account_id) throws SiebelException
    {
        Map<String, String> map = new HashMap();
        String text;
        Id = account_id;
        set = new SiebelPropertySet();
        SiebelPropertySet setProp;
        set.setProperty("Ship To Account", "1");
        set.setProperty("Ship To", "1");
        set.setProperty("Ship To City", "1");
        set.setProperty("Ship To State", "1");
        this.value = "";
        searchKey = "Order Id";
        ss.setSField(set);
        setProp = ss.getSField("Order Entry", "FS Shipment", this);
        text = setProp.getProperty("Ship To Account") + " " + setProp.getProperty("Ship To") + " " + setProp.getProperty("Ship To City") + ", " + setProp.getProperty("Ship To State") + ".";
        map.put("1", text);
        quoteItem.add(map);
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
