package org.tesco.file;

import java.io.IOException;
import java.util.Properties;

import org.apache.camel.builder.RouteBuilder;

public class FileRoute extends RouteBuilder {

    private static final Properties properties;
    static {
	properties = new Properties();
	try {
	    properties.load(FileRoute.class.getClassLoader().getResourceAsStream("application.properties"));
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    @Override
    public void configure() throws Exception {
	from(getInputLocation()).to(getOutputLocation());
    }

    private String getInputLocation() {
	return properties.getProperty("file.input");
    }
    
    private String getOutputLocation(){
	return properties.getProperty("file.output");
    }
}
