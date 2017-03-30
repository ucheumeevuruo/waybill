/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SiebelApplication;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author Adeyemi
 */
public interface IProperties {

    /**
     *
     */
    public static final String NIX_INPUT_KEY = "nix_waybill_template";
    public static final String WIN_INPUT_KEY = "win_waybill_template";

    /**
     *
     */
    public static final String NIX_OUTPUT_KEY = "nix_generated_path";
    public static final String WIN_OUTPUT_KEY = "win_generated_path";
    
    /**
     *
     * @param nix
     * @param win
     * @return
     */
    public IProperties setProperties(String nix, String win);

    /**
     *
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public String getProperty() throws FileNotFoundException, IOException;
}
