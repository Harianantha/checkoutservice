package com.example;

import java.util.logging.Logger;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/order/checkout")
public class CheckoutController {
	private final static Logger LOGGER = Logger.getLogger(CheckoutController.class.getName());
	 private final static String CLASSNAME="CheckoutController";
   

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public String getStatus(@PathVariable("id") long id) {
    	
    	return "DELIVERED";
        
    }
    @RequestMapping(value = "{id}", method = RequestMethod.POST)
    public void placeOrder() {
    	String methodName="placeOrder";
    	LOGGER.entering(CLASSNAME, methodName);
    	
    	
    	LOGGER.exiting(CLASSNAME, methodName);
    	
    	
    	//return "DELIVERED";
        
    }
}
