/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SiebelApplication.bin;

import com.siebel.data.SiebelBusComp;
import com.siebel.data.SiebelException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Adeyemi
 */
public interface IQuote {
    public List<Map<String, String>> getOrderItems(String quote_id) throws SiebelException, IOException;
    public void searchSpec(SiebelBusComp sbBC) throws SiebelException;

    public void getExtraParam(SiebelBusComp sbBC);
}
