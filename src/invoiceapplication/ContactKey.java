/*
 * To changTo change this template file, choose Tools | Templates
 * and open the template in the editor.e this license header, choose License Headers in Project Properties.
 * 
 */
package invoiceapplication;

/**
 *
 * @author Adeyemi
 */
public class ContactKey implements IKey{
    @Override
    public Integer productKeyToInt(String value) throws Exception{
        int index = 0;
        value = value.toLowerCase();
        StringInt i = new StringInt();
        if(i.isStringInt(value)){
            index = Integer.valueOf(value);
        }else{
            if("name".equals(value))
                index = 2;
            else if("street address".equals(value))
                index = 2;
            else if("city".equals(value))
                index = 2;
            else if("phone number".equals(value))
                index = 2;
            else if("item price".equals(value))
                index = 9;
            else
                throw new Exception("Invalid index value.");
        }
        return index;
    }
}
