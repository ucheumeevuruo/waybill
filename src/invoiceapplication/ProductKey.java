/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package invoiceapplication;

/**
 *
 * @author Adeyemi
 */
public class ProductKey implements IKey{
    @Override
    public Integer productKeyToInt(String value) throws Exception{
        int index = 0;
        value = value.toLowerCase();
        StringInt i = new StringInt();
        if(i.isStringInt(value)){
            index = Integer.valueOf(value);
        }else{
            if("sn".equals(value))
                index = 0;
            else if("product".equals(value))
                index = 2;
            else if("quantity".equals(value))
                index = 7;
            else if("inventory id".equals(value))
                index = 8;
            else if("item price".equals(value))
                index = 9;
            else
                throw new Exception("Invalid index value.");
        }
        return index;
    }
}
