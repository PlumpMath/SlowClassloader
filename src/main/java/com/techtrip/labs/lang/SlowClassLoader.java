package com.techtrip.labs.lang;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class SlowClassLoader extends ClassLoader {

	private static long DELAY_MS = 10L;
	private static final String DELAY_SYS_PROP = "com.techtrip.labs.lang.SlowClassLoader.delay";
	private static String DELAY_SYS_MESSAGE = String.format("%s: The delay in MS used to load each class by this class loader.", DELAY_SYS_PROP);

	private static boolean SILENT_MODE = true;
	private static final String SILENT_MODE_SYS_PROP = "com.techtrip.labs.lang.SlowClassLoader.runsilent";
	private static String SILENT_MODE_SYS_MESSAGE = String.format("%s: Determines whether the classloader echoes each class loaded through it on Sys Out (Default is QUIET/TRUE)", SILENT_MODE_SYS_PROP);

	static {
		System.out
		.println("***** WARNING ***** THIS CLASSLOADER INTENTIONALLY LOADS CLASSES SLOWLY\nIT IS INTENDED FOR DEBUGGING PURPOSES ONLY TO SIMULATE CLASSLOADING OVER A SLOW TRANSPORT MECHANISM!");

		System.out
		.println(String
				.format("\nThis classloader will accept 2 system settings to affect it's behavior:\n%s\n%s",
						DELAY_SYS_MESSAGE, SILENT_MODE_SYS_MESSAGE));

		String delay = System.getProperty(DELAY_SYS_PROP, "10");
		String silent = System.getProperty(SILENT_MODE_SYS_PROP, "true");

		try {
			DELAY_MS = Long.parseLong(delay);
			SILENT_MODE = Boolean.parseBoolean(silent);
		} catch (Exception ex) {

		}

		System.out.println(String
				.format("\nSlowClassLoader - Class loading Delay is set to: %d",
						DELAY_MS));
		System.out.println(String.format(
				"\nSlowClassLoader - Silent Mode is set to: %s", SILENT_MODE));
	}

	/**
	 * Parent ClassLoader passed to this constructor will be used if this
	 * ClassLoader can not resolve a particular class.
	 *
	 * @param parent
	 *            Parent ClassLoader (may be from getClass().getClassLoader())
	 */
	public SlowClassLoader(ClassLoader parent) {
		super(parent);
	}

	/**
	 * Every request for a class passes through this method. If the requested
	 * class is in "javablogging" package, it will load it using the
	 * {@link CustomClassLoader#getClass()} method. If not, it will use the
	 * super.loadClass() method which in turn will pass the request to the
	 * parent.
	 *
	 * @param name
	 *            Full class name
	 */
	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException {

		if (!SILENT_MODE) {
			System.out.println("SlowClassLoader: loading class '" + name + "'");
		}

		/* THIS IS WHERE I WANT TO ADD MY DELAY TO SIMULATE A SLOW CLASSLOAD */
		try {
			Thread.sleep(DELAY_MS);
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
			System.exit(-1);
		}
		Class<?> classRet =  super.loadClass(name);

		if (!SILENT_MODE) {
			System.out.println("SlowClassLoader: The Class '" + name + " has been loaded'");
		}

		return classRet;
	}

	/**
	 * Loads a given file (presumably .class) into a byte array. The file should
	 * be accessible as a resource, for example it could be located on the
	 * classpath.
	 *
	 * @param name
	 *            File name to load
	 * @return Byte array read from the file
	 * @throws IOException
	 *             Is thrown when there was some problem reading the file
	 */
	private byte[] loadClassData(String name) throws IOException {
		// Opening the file
		InputStream stream = getClass().getClassLoader().getResourceAsStream(
				name);
		int size = stream.available();
		byte buff[] = new byte[size];
		DataInputStream in = new DataInputStream(stream);
		// Reading the binary data
		in.readFully(buff);
		in.close();
		return buff;
	}

}
