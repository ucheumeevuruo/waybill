/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helper;

import SiebelApplication.ApplicationProperties;
import SiebelApplication.IProperties;
import SiebelApplication.MyLogging;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Level;

/**
 *
 * @author Adeyemi
 */
public class HelperAP
{
 //   public static final String INVOICE_TEMPLATE = getInvoiceTemplate();

    //public static final String GENERATED_PATH = getGeneratedPath();

    private static final ApplicationProperties AP = new ApplicationProperties();
    
    private static String output = new String();
    
    private static final StringWriter ERROR_TXT = new StringWriter();

    public static String getInvoiceTemplate() throws IOException
    {   
        AP.setProperties(IProperties.NIX_INPUT_KEY, IProperties.WIN_INPUT_KEY);
        return AP.getProperty();
    }

    public static String getGeneratedPath() throws IOException
    {
        AP.setProperties(IProperties.NIX_OUTPUT_KEY, IProperties.WIN_OUTPUT_KEY);
        return AP.getProperty();
    }
}
