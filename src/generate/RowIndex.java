package generate;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hp
 */

public class RowIndex {
    private int value;
    public static Integer row(Integer value) {
        if (value <= 0)
            throw new IllegalArgumentException("row indices start at 1");
        return (value - 1);
    }

    int value() {
        return value; //To change body of generated methods, choose Tools | Templates.
    }
}
