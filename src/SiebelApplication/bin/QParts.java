/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SiebelApplication.bin;

import SiebelApplication.MyLogging;
import SiebelApplication.SiebelService;
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
public class QParts extends SiebelService implements IQuote{
    
    private static SiebelPropertySet set;
    private String orderId;
    private List<Map<String, String>> orderItem;
    private static final String BO = "Order Entry";
    private static final String BC = "Order Entry - Line Items";
    
    /**
     * 
     * @param conn 
     */
    public QParts(SiebelDataBean conn)
    {
        super(conn);
    }
    
    /**
     *
     * @param order_id
     * @return
     * @throws SiebelException
     */
    @Override
    public List<Map<String, String>> getOrderItems(String order_id) throws SiebelException
    {
        this.orderId = order_id;
        set = new SiebelPropertySet();
        set.setProperty("Outline Number", "1");
        set.setProperty("Part Number", "2");
        set.setProperty("Product", "3");
        set.setProperty("Product Description", "4");
        set.setProperty("Quantity Requested", "5");
        set.setProperty("Account", "6");
        set.setProperty("Order Number", "7");
        this.setSField(set);
        orderItem = this.getSField(BO, BC, this);
        MyLogging.log(Level.INFO, "Creating siebel objects Parts: " + orderItem);
        return orderItem;
    }

    /**
     * 
     * @param sbBC
     * @throws SiebelException 
     */
    @Override
    public void searchSpec(SiebelBusComp sbBC) throws SiebelException 
    {
        sbBC.setSearchSpec("Order Number", orderId); 
        sbBC.setSearchSpec("Product Type", "Equipment");
    }
    
    /**
     *
     * @param sbBC
     */
    @Override
    public void getExtraParam(SiebelBusComp sbBC){};
}
