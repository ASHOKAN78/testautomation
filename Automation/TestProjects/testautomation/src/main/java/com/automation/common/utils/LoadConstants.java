package com.automation.common.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;


public class LoadConstants {
	public static String getPropertyValue(String propertyName) 
	{
		Properties conf = new Properties();
		FileInputStream confFile = null;
		try
		{
			confFile = new FileInputStream(Constants.CONFIG_FILE_LOCATION);
			// load a properties file from class path, inside static method
			conf.load(confFile);
			
			// get the property value and print it out
			String value = conf.getProperty(propertyName);
			return value;
		} 
		catch (FileNotFoundException ex)
		{
			System.out.println("Property File Not Found");
			return null;
		}
		catch (IOException ex)
		{
			System.out.println("Value corrosponding to the key is not present in the properties file");
			return null;
		}
		
		finally
		{
			try
			{
				if(confFile != null)
				{
				confFile.close();
				//System.out.println("Property File Closed");
				}
			} catch (IOException e)
			{
				e.printStackTrace();
				System.out.println("Property File not Closed");
			}
			
		}
	}
	public static String getPropertyValue(String propertyName,String PROPERTY_FILE_LOCATION) 
	{
		Properties conf = new Properties();
		FileInputStream confFile = null;
		try
		{
			confFile = new FileInputStream(PROPERTY_FILE_LOCATION+".properties");
			// load a properties file from class path, inside static method
			conf.load(confFile);
			
			// get the property value and print it out
			String value = conf.getProperty(propertyName);
			return value;
		} 
		catch (FileNotFoundException ex)
		{
			System.out.println("Property File Not Found");
			return null;
		}
		catch (IOException ex)
		{
			System.out.println("Value corrosponding to the key is not present in the properties file");
			return null;
		}
		
		finally
		{
			try
			{
				if(confFile != null)
				{
				confFile.close();
				//System.out.println("Property File Closed");
				}
			} catch (IOException e)
			{
				e.printStackTrace();
				//System.out.println("Property File not Closed");
			}
			
		}
	}

}

