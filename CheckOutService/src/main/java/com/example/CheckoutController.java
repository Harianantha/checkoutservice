package com.example;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Logger;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.command.FulfillmentCommand;
import com.example.data.Order;
import com.netflix.hystrix.HystrixCommandProperties;


@RestController

public class CheckoutController {
	private final static Logger LOGGER = Logger.getLogger(CheckoutController.class.getName());
	 private final static String CLASSNAME="CheckoutController";
   
	
    @RequestMapping(path="/order/checkout",method = RequestMethod.GET)
    public String getStatus() {
    	
    	return "DELIVERED";
        
    }
    @RequestMapping(value = "{id}", method = RequestMethod.POST)
    public void placeOrder() {
    	String methodName="placeOrder";
    	LOGGER.entering(CLASSNAME, methodName);
    	Order order=new Order();
    	FulfillmentCommand command=new FulfillmentCommand(order);
    	Order returnVal=(Order)command.execute();
    	
    	LOGGER.exiting(CLASSNAME, methodName);
    	
    	
    	//return "DELIVERED";
        
    }
    @RequestMapping(path="/status/{id}", method = RequestMethod.GET)
    //@RequestMapping("/status",value = "{id}", method = RequestMethod.GET)
    public String placeOrderTest(@PathVariable("id") long id) {
    	String methodName="placeOrderTest";
    	LOGGER.entering(CLASSNAME, methodName);
    	Order order=new Order();
    	order.setOrderId(Long.toString(id));
    	FulfillmentCommand command=new FulfillmentCommand(order);
    	HystrixCommandProperties.Setter().withExecutionTimeoutInMilliseconds(10000);
    	//Order returnVal=(Order)command.
    	Future<Order> future=command.queue();
    	Order returnVal=null;
		try {
			returnVal = future.get();
		} catch (InterruptedException e)  {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(ExecutionException e){
			e.printStackTrace();
		}
    	LOGGER.exiting(CLASSNAME, methodName);
    	
    	if(returnVal!=null)
    	return returnVal.getStatus();
    	else
    		return "Fulfillment Service Invocation failed";
    	//return "Order Placed Successfully"+System.currentTimeMillis();
    	//return "DELIVERED";
        
    }
}
