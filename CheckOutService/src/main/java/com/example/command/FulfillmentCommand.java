package com.example.command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.example.data.Order;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

public class FulfillmentCommand extends HystrixCommand<Order> {
	
	private  Order order=null;
	private String URL_PATH="http://fulfillmentservice.au-syd.mybluemix.net/order/fulfill";
	public FulfillmentCommand(Order order){
		super(HystrixCommandGroupKey.Factory.asKey("FulfillmentGroup"));
		this.order=order;
	}

	@Override
	protected Order run() throws Exception {
		// TODO Auto-generated method stub
		invokeFullfillmentService();
		return order;
	}
	
	public  void invokeFullfillmentService() {

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

		}

}
