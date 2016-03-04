# AWSSimpleWebsiteLoadGenerator

Produce load (TPS) on AWSSimpleWebsite site (https://github.com/marcilio/AWSSimpleWebsite) to see your ElasticLoadBalancer and AutoScalingGroup in action!

##Command-line options
-a STRING: Full URL to generate the load against (eg, http://www.mywebsite.ca/AWSSimpleWebsite/LoadGeneratorServlet"

-t INT: time to wait in milliseconds between requests

-w INT: number of request to send to the website

-v verbose mode

##Sample call:
java AWSSimpleWebsiteLoadGenerator -a http://localhost:8080/AWSSimpleWebsite/LoadGeneratorServlet -w 10 -t 1000 -v
