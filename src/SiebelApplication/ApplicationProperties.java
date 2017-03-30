package SiebelApplication;


import com.siebel.eai.SiebelBusinessServiceException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author SAP Training
 */
public class ApplicationProperties implements IProperties{
       
    private static String propfilepath = "";
    private static String vlogFile = "";
    private static String templateFile = "";    
    private static final String OS = System.getProperty("os.name").toLowerCase();
    
    @Override
    public IProperties setProperties(String nix, String win)
    {
        if (OS.contains("nix") || OS.contains("nux")) 
        {                
            propfilepath = "";
            vlogFile = "nix_logfile";
            templateFile = nix;
        } 
        else if (OS.contains("win")) 
        {
            propfilepath = "C:\\temp\\intg\\intg.properties";
            vlogFile = "win_logfile";
            templateFile = win;
        }
        else
        {
            throw new NullPointerException("Null pointer exception");
        }
        return this;
    }
    
    @Override
    public String getProperty() throws FileNotFoundException, IOException{
        Properties prop = new Properties();
        FileInputStream input = new FileInputStream(propfilepath);
        prop.load(input);
        return prop.getProperty(templateFile);
    }
    
    public static void main(String[] args) throws SiebelBusinessServiceException 
    {
        try {
            ApplicationProperties AP = new ApplicationProperties();
            AP.setProperties(IProperties.NIX_INPUT_KEY, IProperties.WIN_INPUT_KEY);
            System.out.println(AP.getProperty());
        } catch (IOException ex) {
            Logger.getLogger(ApplicationProperties.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
