/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nl.tjonahen.studentclient;

/**
 *
 * @author Philippe Tjon - A - Hen
 */
public class Student {
    
    private String field0;
    private String field1;
    private String field2;
    private String field3;
    private String field4;

    public Student(String field0, String field1, String field2, String field3, String field4) {
        this.field0 = field0;
        this.field1 = field1;
        this.field2 = field2;
        this.field3 = field3;
        this.field4 = field4;
    }

    
    public String getField0() {
        return field0;
    }

    public void setField0(String field0) {
        this.field0 = field0;
    }

    public String getField1() {
        return field1;
    }

    public void setField1(String field1) {
        this.field1 = field1;
    }

    public String getField2() {
        return field2;
    }

    public void setField2(String field2) {
        this.field2 = field2;
    }

    public String getField3() {
        return field3;
    }

    public void setField3(String field3) {
        this.field3 = field3;
    }

    public String getField4() {
        return field4;
    }

    public void setField4(String field4) {
        this.field4 = field4;
    }

    
}
