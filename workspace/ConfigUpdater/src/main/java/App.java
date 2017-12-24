import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;

import org.apache.commons.configuration.AbstractFileConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.PropertiesConfigurationLayout;

public class App 
{
    public static void main( String[] args ) throws ConfigurationException
    {
    	String directory = "";
    	String fileType = "";
    	String[] exclusionList = {}; 
    	String propKey = "";
    	String propValue="";
    	try{
    	directory = args[0].trim();//System.getProperty("directoryName").trim();
    	fileType = args[1].trim();//System.getProperty("fileType").trim();
    	exclusionList = args[2].trim().split(",");//System.getProperty("exclusion").split(",");
    	propKey = args[3].trim();
    	propValue = args[4].trim();
    	}
    	catch (Exception e) {
    		System.out.println("usage directoryName <<Directory Path of Config>>  fileType <<eg: TSCenvironment or TSCEmail>>  exclusion << none if all props to update comma separated; else specify prod,prodstg>>");
    		return;
    	}
    	try {
    	File[] files = new File(directory).listFiles();
    	 for (File file : files) {
    	        if ( ! file.isDirectory() && file.getName().startsWith(fileType)) {
    	        		if(exclusionList.length == 0 ) {
    	        			update(file ,propKey , propValue);
    	        } else  {
    	        	for(String exc : exclusionList) {
    	        			if(! file.getName().equals(fileType+".targetable."+exc+".properties")) {
    	        				update(file,propKey , propValue);
    	        			}
    	        	}
    	        	
    	        }
    		 }
    	 }
    	} catch(Exception e) {
    		System.out.println("usage directoryName <<Directory Path of Config>>  fileType <<eg: TSCenvironment or TSCEmail>>  --exclusion <<comma separated. eg : --exclusion prod,prodstg>>");
    	}
    }
    
    
    public static void update (File file , String propKey , String propValue) throws ConfigurationException{
    	try{
    		AbstractFileConfiguration config = new PropertiesConfiguration();
    		config.setListDelimiter('^');
    		config.setFile(file);
    	    config.load(new InputStreamReader(new FileInputStream(file)));
    	    config.setDelimiterParsingDisabled(true);
    	    config.setProperty(propKey, propValue);
    	    config.save();
    	    System.out.println("Config Updated for "+ file.getName() );
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    
}
