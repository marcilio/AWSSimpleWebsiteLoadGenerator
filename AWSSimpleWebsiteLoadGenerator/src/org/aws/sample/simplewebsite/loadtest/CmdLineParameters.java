package org.aws.sample.simplewebsite.loadtest;

import org.kohsuke.args4j.Option;

public class CmdLineParameters {
	@Option(name = "-a", required = true, usage = "Full URL to generate the load against (eg, http://www.mywebsite.ca/AWSSimpleWebsite/LoadGeneratorServlet", metaVar = "String")
	String websiteAddress;

	@Option(name = "-t", required = true, usage = "time to wait in milliseconds between requests (eg, 100)", metaVar = "INT")
	int waitTime;

	@Option(name = "-w", required = true, usage = "number of request to send to the website (eg, 1000)", metaVar = "INT")
	int numRequests;

	@Option(name = "-v", required = false, usage = "verbose mode")
	boolean verbose = false;

	public boolean isVerbose() {
		return verbose;
	}

	public String getWebsiteAddress() {
		return websiteAddress;
	}

	public int getWaitTime() {
		return waitTime;
	}

	public int getNumRequests() {
		return numRequests;
	}

}