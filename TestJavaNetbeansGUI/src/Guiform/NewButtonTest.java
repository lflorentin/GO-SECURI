/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Guiform;
import javax.swing.AbstractButton;
/**
 *
 * @author florentinlucas
 */
import java.beans.ConstructorProperties;
import javax.accessibility.Accessible;
import javax.swing.Icon;

public class NewButtonTest
extends AbstractButton
implements Accessible {
    
public NewButtonTest(){
    }

public NewButtonTest(Icon icon){}

@ConstructorProperties(value="text")
public NewButtonTest(String text){}


}
// Code adding the component to the parent container - not shown here
