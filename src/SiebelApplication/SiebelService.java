package SiebelApplication;


import SiebelApplication.bin.IQuote;
import com.siebel.data.SiebelBusComp;
import com.siebel.data.SiebelBusObject;
import com.siebel.data.SiebelDataBean;
import com.siebel.data.SiebelException;
import com.siebel.data.SiebelPropertySet;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Adeyemi
 */
public class SiebelService {  

    /**
     *
     */
    protected static SiebelDataBean sdb;
    private StringWriter errors;
    protected static SiebelPropertySet properties, values;
    protected Integer beginCount = 1;
    
    public SiebelService(SiebelDataBean conn)
    {
        sdb = conn;
    }
    
    public static SiebelDataBean getService() throws IOException
    {
        return sdb;
    }
    
    public void serviceLogOff() throws SiebelException
    {
        sdb.logoff();
    }
    
    public void setSField(SiebelPropertySet property)
    {
        properties = property;
    }
    
    public List<Map<String, String>> getSField(String bO, String bC, IQuote qM) throws SiebelException
    {

        SiebelBusObject sbBO = sdb.getBusObject(bO); 
        SiebelBusComp sbBC = sbBO.getBusComp(bC);
        List<Map<String, String>> List;
        values = sdb.newPropertySet();
        sbBC.setViewMode(3);
        sbBC.clearToQuery();
        // Activate all the fields
        sbBC.activateMultipleFields(properties);
        //Get search specification
        qM.searchSpec(sbBC);
        sbBC.executeQuery2(true, true);
        List = doTrigger(sbBC);
        qM.getExtraParam(sbBC);
        sbBC.release();
        sbBC.release();

        return List;
    }
    
    protected List<Map<String, String>> doTrigger(SiebelBusComp sbBC) throws SiebelException
    {
        
        List<Map<String, String>> list = new ArrayList();
        boolean isRecord = sbBC.firstRecord();
        while (isRecord)
        {
            sbBC.getMultipleFieldValues(properties, values);
            list.add(Service_PreInvokeMethod(properties, values));
            isRecord = sbBC.nextRecord();
        }
        return list;
    }
    
    private Map<String, String> Service_PreInvokeMethod (SiebelPropertySet Inputs, SiebelPropertySet Outputs)
    {
       String propName = Inputs.getFirstProperty(), propVal;
       Map<String, String> mapProperty = new HashMap();
       // stay in loop if the property name is not an empty string
       while (!"".equals(propName)) 
       {
          propVal = Outputs.getProperty(propName);
          // if a property with the same name does not exist
          // add the name value pair to the output
          if (Inputs.propertyExists(propName)) 
          {
             if("Outline Number".equals(propName))
             {
                 propVal = String.valueOf(beginCount++);
             }
             mapProperty.put(Inputs.getProperty(propName), propVal);
          }
          propName = Inputs.getNextProperty();

       }
       return mapProperty;
    }
}
