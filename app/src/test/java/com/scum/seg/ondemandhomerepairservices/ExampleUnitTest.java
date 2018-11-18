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

    User user = new User("Ali","Khanafer","akhan315","password",
            "akhan310@gmail.com","6134136642","81 Gillespie Crescent","admin");

    Service service = new Service("Appliance Installation", 25);

    ServiceProvider provider = new ServiceProvider();

    @Test
    public void serviceRateIsCorrect(){
        assertEquals("Checking service rate is correct ",25, service.getServiceRate(),0.5);
    }

    @Test
    public void serviceNameIsCorrect(){
        assertEquals("Checking service name is correct ","Appliance Installation", service.getServiceName());
    }

    // Testing User class
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

    @Test
    public void userPasswordIsCorrect(){
        assertEquals("Checking if user's password is correct ", "password", user.getPassword());
    }

    @Test
    public void userEmailIsCorrect(){
        assertEquals("Checking if user's email is correct ", "akhan310@gmail.com", user.getEmail());
    }

    @Test
    public void userPhoneNumberIsCorrect(){
        assertEquals("Checking if user's phone number is correct ","6134136642", user.getPhonenumber());
    }

    @Test
    public void userAddressIsCorrect(){
        assertEquals("Checking if user's address is correct ","81 Gillespie Crescent", user.getAddress());
    }

    @Test
    public void userTypeIsCorrect(){
        assertEquals("Checking if user's type is correct ", "admin", user.getType());
    }

    @Test
    public void testServiceAddedToServiceProvider(){
        provider.addService(service);
        boolean result = provider.getServices().contains(service);
        assertEquals("Checking if provider has \"service\" included in his list",true, result);

    }

    @Test
    public void testServiceRemovedToServiceProvider(){
        provider.removeService(service);
        boolean result = provider.getServices().contains(service);
        assertEquals("Checking if provider has \"service\" included in his list",false, result);

    }


}