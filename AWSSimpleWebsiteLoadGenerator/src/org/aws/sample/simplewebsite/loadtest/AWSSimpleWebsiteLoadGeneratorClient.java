package org.aws.sample.simplewebsite.loadtest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Produce load (TPS) on AWSSimpleWebsite site (https://github.com/marcilio/AWSSimpleWebsite) to see your ElasticLoadBalancer and AutoScalingGroup in action!
 * 
 * ==> Update the 'host' variable's value below to point to your AWSSimpleWebsite's URL (eg, your ELB DNS name).
 * 
 * @author marcilio
 *
 */
public class AWSSimpleWebsiteLoadGeneratorClient {

	// replace with your website's URL 
	private static String host = "[replace with your Website's URL]";
	private static String address = "http://" + host + "/AWSSimpleWebsite/LoadGeneratorServlet";
	// the lower the value the higher the TPS
	private static final long intervalBetweenSequentialRequests = 100;
	// total number of requests
	private static final int totalRequests = 100000;
	// Thread pool used to submit concurrent request. Adjust accordingly.
	private static ThreadPoolExecutor executor = new ThreadPoolExecutor(200, 200, 0, TimeUnit.SECONDS,
			new ArrayBlockingQueue<>(200));
	// print log to System.out
	private static boolean verbose = true;

	public static void main(String[] args) throws IOException {
		for (int i = 0; i < totalRequests; i++) {
			executor.submit(new OpenURLWorker(address));
			sleep();
		}
	}

	private static void sleep() {
		try {
			Thread.sleep(intervalBetweenSequentialRequests);
		} catch (InterruptedException e) {
		}
	}

	static class OpenURLWorker implements Runnable {

		private String address;

		OpenURLWorker(String address) {
			this.address = address;
		}

		@Override
		public void run() {
			try {
				URL url = new URL(address);
				BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
				String inputLine;
				while ((inputLine = in.readLine()) != null) {
					if (verbose) {
						System.out.println("Thread: " + Thread.currentThread().getId() + ":\n" + inputLine);
					}
				}
				in.close();
			} catch (IOException e) {
				System.out.println("Error opening URL: " + address);
			}
		}

	}

}
