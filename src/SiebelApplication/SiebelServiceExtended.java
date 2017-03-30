/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SiebelApplication;

import com.siebel.data.SiebelBusComp;
import com.siebel.data.SiebelDataBean;
import com.siebel.data.SiebelException;
import com.siebel.data.SiebelPropertySet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Adeyemi
 */

public class SiebelServiceExtended extends SiebelService
{
    //<editor-fold defaultstate="collapsed" desc="/*comment*/">
    /**
     * 
     * @param service 
     */
    public SiebelServiceExtended(SiebelDataBean service)
    {
        super(service);
    }
    
    /**
     * 
     * @param sbBC
     * @return
     * @throws SiebelException 
     */
    @Override
    protected List<Map<String, String>> doTrigger(SiebelBusComp sbBC) throws SiebelException
    {
        List<Map<String, String>> list = new ArrayList();
        boolean isRecord = sbBC.firstRecord();
        if (isRecord)
        {
            sbBC.getMultipleFieldValues(properties, values);
            list = Service_PreInvokeMethod(properties, values);
        }
        return list;
    }
    
    /**
     * 
     * @param Inputs
     * @param Outputs
     * @return 
     */
    private List<Map<String, String>> Service_PreInvokeMethod (SiebelPropertySet Inputs, SiebelPropertySet Outputs)
    {
       String propName = Inputs.getFirstProperty(), propVal;
       List<Map<String, String>> setList = new ArrayList();
       // stay in loop if the property name is not an empty string
       while (!"".equals(propName)) {
          propVal = Outputs.getProperty(propName);
          Map<String, String> mapProperty = new HashMap();
          // if a property with the same name does not exist
          // add the name value pair to the output
          if (Inputs.propertyExists(propName)) {
            mapProperty.put(Inputs.getProperty(propName), propVal);
            setList.add(mapProperty);
          }
          propName = Inputs.getNextProperty();

       }
       return setList;
    }
}