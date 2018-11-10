package com.scum.seg.ondemandhomerepairservices;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    User user = new User("Ali","Khanafer","akhan315","password","akhan310@gmail.com","6134136642","81 Gillespie Crescent","admin");
    Service service = new Service("Appliance Installation", 25);

    @Test
    public void serviceRateIsCorrect(){
        assertEquals("Checking service rate is correct ",25, service.getServiceRate(),0.5);
    }

    @Test
    public void serviceNameIsCorrect(){
        assertEquals("Checking service name is correct ","Appliance Installation", service.getServiceName());
    }

    @Test
    public void userFirstNameIsCorrect(){
        assertEquals("Checking if the user's first name is correct ", "Ali",user.getFirstName());
    }

    @Test
    public void userLastNameIsCorrect(){
        assertEquals("Checking if user's last name is correct ","Khanafer",user.getLastName());
    }

    @Test
    public void userUserNameCorrect(){
        assertEquals("Checking if user's username is correct ","akhan315",user.getUserName());
    }

}