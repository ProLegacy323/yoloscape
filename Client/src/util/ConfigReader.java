package util;

import java.util.*;
import java.util.Properties;
import java.io.FileInputStream;

/**
* @author Figglewatts
* Config File Reader
*/
public class ConfigReader
{
	Properties configFile;
	
	//constructor
	public ConfigReader(String path)
	{
		configFile = new java.util.Properties();
		try {
			configFile.load(new FileInputStream(path));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// returns the property associated with key "key"
	public String getProperty(String key)
	{
		System.out.println("Getting property from key: " + key + ".");
		String value = this.configFile.getProperty(key);
		System.out.println("Key is equal to: " + value + ".");
		return value;
	}
}