package org.aws.sample.simplewebsite.loadtest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Optional;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

/**
 * Produce load (TPS) on AWSSimpleWebsite site
 * (https://github.com/marcilio/AWSSimpleWebsite) to see your
 * ElasticLoadBalancer and AutoScalingGroup in action!
 * 
 * ==> Update the 'host' variable's value below to point to your
 * AWSSimpleWebsite's URL (eg, your ELB DNS name).
 * 
 * @author marcilio
 *
 */
public class AWSSimpleWebsiteLoadGeneratorClient {

	// Thread pool used to submit concurrent request.
	private static ThreadPoolExecutor executor = new ThreadPoolExecutor(200, 200, 0, TimeUnit.SECONDS,
			new ArrayBlockingQueue<>(200));

	public static void main(String[] args) throws IOException {

		Optional<CmdLineParameters> parameters = parseCmdLineParameters(args);
		if (parameters.isPresent()) {
			CmdLineParameters cmdLineParams = parameters.get();
			for (int i = 0; i < cmdLineParams.getNumRequests(); i++) {
				executor.submit(new OpenURLWorker(cmdLineParams.getWebsiteAddress(), cmdLineParams.isVerbose()));
				sleep(cmdLineParams.getWaitTime());
			}
		}
	}

	private static void sleep(int sleepTime) {
		try {
			Thread.sleep(sleepTime);
		} catch (InterruptedException e) {
		}
	}

	static class OpenURLWorker implements Runnable {

		private String address;
		private boolean verbose;

		OpenURLWorker(String address, boolean verbose) {
			this.address = address;
			this.verbose = verbose;
		}

		@Override
		public void run() {
			try {
				URL url = new URL(address);
				BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
				String inputLine;
				if (verbose) {
					while ((inputLine = in.readLine()) != null) {
						System.out.println("Thread: " + Thread.currentThread().getId() + ":\n" + inputLine);
					}
				}
				in.close();
			} catch (IOException e) {
				System.out.println("Error opening URL: " + address);
			}
		}

	}

	private static Optional<CmdLineParameters> parseCmdLineParameters(String args[]) {
		CmdLineParameters parameters = new CmdLineParameters();
		CmdLineParser cmdLineParser = new CmdLineParser(parameters);
		try {
			cmdLineParser.parseArgument(args);
			return Optional.of(parameters);
		} catch (CmdLineException e) {
			System.out.println("Invalid Parameters!");
			System.out.println("Mandatory parameters: ");
			cmdLineParser.printUsage(System.out);
		}
		return Optional.empty();
	}

}
