package com.example.command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

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
		order.setStatus(order.getOrderId()+":"+"Deleivered");
		LOGGER.exiting(CLASSNAME, methodName);
		return order;
	}
	
	public  void invokeFullfillmentService() {
		String methodName="invokeFullfillmentService";
		LOGGER.entering(CLASSNAME, methodName);
		  try {

			URL url = new URL(URL_PATH);
			long startTime=System.currentTimeMillis();
			/*HttpHost proxy = new HttpHost("proxy.cognizant.com");
			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("proxy.cognizant.com", 6050));*/
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			/*conn.addRequestProperty("http.proxyHost", "proxy.cognizant.com");
			conn.addRequestProperty("http.proxyPort", "6050");*/
			conn.addRequestProperty("java.net.useSystemProxies", "true");
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
			long endtime=System.currentTimeMillis();
			System.out.println("Time to call fulfillment service:"+(endtime-startTime));
			conn.disconnect();

		  } catch (MalformedURLException e) {

			e.printStackTrace();

		  } catch (IOException e) {

			e.printStackTrace();

		  }
		  LOGGER.exiting(CLASSNAME, methodName);
		}
	

}
