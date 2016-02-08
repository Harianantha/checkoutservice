package com.example.command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.example.CheckoutController;
import com.example.data.Order;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

public class FulfillmentCommand extends HystrixCommand<Order> {
	private final static Logger LOGGER = Logger.getLogger(FulfillmentCommand.class.getName());
	private final static String CLASSNAME="FulfillmentCommand";
	private  Order order=null;
	private String URL_PATH="http://fulfillmentservice.au-syd.mybluemix.net/order/fulfill";
	public FulfillmentCommand(Order order){
		super(HystrixCommandGroupKey.Factory.asKey("FulfillmentGroup"));
		this.order=order;
	}

	@Override
	protected Order run() throws Exception {
		String methodName="run";
		// TODO Auto-generated method stub
		LOGGER.entering(CLASSNAME, methodName);
		invokeFullfillmentService();
		LOGGER.exiting(CLASSNAME, methodName);
		return order;
	}
	
	public  void invokeFullfillmentService() {
		String methodName="invokeFullfillmentService";
		LOGGER.entering(CLASSNAME, methodName);
		  try {

			URL url = new URL("URL_PATH");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));
			LOGGER.logp(Level.INFO, CLASSNAME, methodName, "After getting response from fulfillemnt service");
			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}

			conn.disconnect();

		  } catch (MalformedURLException e) {

			e.printStackTrace();

		  } catch (IOException e) {

			e.printStackTrace();

		  }
		  LOGGER.exiting(CLASSNAME, methodName);
		}
	

}
