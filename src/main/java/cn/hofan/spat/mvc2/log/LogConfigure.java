package cn.hofan.spat.mvc2.log;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;

import cn.hofan.spat.mvc2.MVC;

public class LogConfigure {
	
	public void configure() throws Exception {

        Properties properties = configureFile();

        if (properties == null)
            properties = defaultProperties();

        configure(properties);

    }

    protected Properties configureFile() throws Exception {
        File configFolder = new File(MVC.getNameSpaceFolderPath());
        if(!configFolder.exists()){
        	configFolder.mkdirs();
        }
        File configFile =  new File(configFolder, "log.properties");

        if (!configFile.exists())
            return null;

        Properties properties = new Properties();
        Reader reader = null;
        try {
            reader = new FileReader(configFile);
            properties.load(reader);
        } catch (Exception e) {
            throw new Exception("fail to init log config file.", e);
        } finally{
            if(reader!=null){
            	reader.close();
            }
        }

        if (!properties.containsKey("log4j.appender.file.File"))
            properties.put("log4j.appender.file.File", defaultLogFile());

        return properties;

    }

    protected Properties defaultProperties() {

        Properties properties = new Properties();

        properties.put("log4j.rootLogger", "INFO, file");
        properties.put("log4j.appender.file.File", defaultLogFile());

        properties.put("log4j.appender.file.DatePattern","'.'yyyy-MM-dd");
        properties.put("log4j.appender.stdout", "org.apache.log4j.ConsoleAppender");
        properties.put("log4j.appender.stdout.Target", "System.out");
        properties.put("log4j.appender.stdout.layout", "org.apache.log4j.PatternLayout");
        properties.put("log4j.appender.stdout.layout.ConversionPattern", "%m%n");
        properties.put("log4j.appender.file", "org.apache.log4j.DailyRollingFileAppender");
        properties.put("log4j.appender.file.Append", "true");
        properties.put("log4j.appender.file.Threshold", "INFO");
        properties.put("log4j.appender.file.layout", "org.apache.log4j.PatternLayout");
        properties.put("log4j.appender.file.layout.ConversionPattern", "%d{ABSOLUTE} %5p %c{1}:%L - %m%n");
        try {
        	File configFile =  new File(MVC.getNameSpaceFolderPath(), "log.properties");
            configFile.createNewFile();
            FileWriter fw = new FileWriter(configFile);
            properties.store(fw, "log4j");
            fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
        return properties;
    }

    private String defaultLogFile() {

        File logFolder = new File(MVC.getConfigFolderPath()+"/logs");
        if(!logFolder.exists()){
        	logFolder.mkdirs();
        }
        String projectId = MVC.getNameSpace();

        File logFile = new File(logFolder
                , projectId + ".log");

        return logFile.getAbsolutePath();
    }

    private void configure(Properties properties) {
        PropertyConfigurator.configure(properties);
    }
}
