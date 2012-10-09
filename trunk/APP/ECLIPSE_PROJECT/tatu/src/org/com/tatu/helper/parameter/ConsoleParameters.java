package org.com.tatu.helper.parameter;

import java.util.Hashtable;

import org.com.tatu.helper.GeneralsHelper;

public class ConsoleParameters {
	
	private Hashtable<String, String>	hashTable = new Hashtable<String, String>();
	private static ConsoleParameters 	consoleParameters = new ConsoleParameters();
	
	private ConsoleParameters() {}
	
	public static final ConsoleParameters getInstance(String[] args) {
		if (consoleParameters == null) {
			consoleParameters = new ConsoleParameters();
		}
		consoleParameters.build(args);
		return consoleParameters;
	}

	private void build(String[] args) {
		this.hashTable.clear();
		for (int i=0 ; i < args.length ; i++) {
			String arg = args[i];
			if (arg.startsWith("-")) {
				if (i+1 < args.length && !args[i+1].startsWith("-")) {
					this.hashTable.put(arg, args[++i]);
				} else {
					this.hashTable.put(arg, arg);
				}
			} else {
				this.hashTable.put(arg, arg);
			}
		}
	}
	
	public String getValue(String key) {
		return this.hashTable.get(key);
	}
	
	public String getValue(String key, boolean mandatory) {
		String value = this.hashTable.get(key);
		if (mandatory && !GeneralsHelper.isStringOk(value)) {
			throw new IllegalArgumentException("Missing parameter. There's no valid value to parameter: " + key);
		}
		return value;
	}
}
