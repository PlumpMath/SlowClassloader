Slow Classloader
=================

This project is used as a drop in replacement for Java's classloader. It is used to simulate classloading over a slow resource
such as a remote filesystem. It is not intended for use in non-test systems (i.e. don't put in production!)

THERE IS NO WARRANTY WITH THIS CODE, NEITHER EXPLICIT OR IMPLIED. USE AT YOUR OWN RISK!

USAGE:
1. Add the jar to the classpath from where you want to load it. (For tomcat you need to add it to the tomcat classpath)

2. Add the classloader as a VMARG

	-Djava.system.class.loader=com.techtrip.labs.lang.SlowClassLoader 

3. Optional - Specify the delay in milliseconds for each class loaded via this classloader (Default is 10ms)

	-Dcom.techtrip.labs.lang.SlowClassLoader.delay=13
	
4. Optional - run in verbose or silent mode, specifying output on sysout (Default is true, i.e. silent)

	-Dcom.techtrip.labs.lang.SlowClassLoader.runsilent=true|false


When running agaisnt this classloader you should see a message similar to the following on sysout:

***** WARNING ***** THIS CLASSLOADER INTENTIONALLY LOADS CLASSES SLOWLY
IT IS INTENDED FOR DEBUGGING PURPOSES ONLY TO SIMULATE CLASSLOADING OVER A SLOW TRANSPORT MECHANISM!

This classloader will accept 2 system settings to affect it's behavior:
com.techtrip.labs.lang.SlowClassLoader.delay: The delay in MS used to load each class by this class loader.
com.techtrip.labs.lang.SlowClassLoader.runsilent: Determines whether the classloader echoes each class loaded through it on Sys Out (Default is QUIET/TRUE)

SlowClassLoader - Class loading Delay is set to: 13

SlowClassLoader - Silent Mode is set to: true

Questions: Please contact Terry Trippany, trip@techtrip.co

