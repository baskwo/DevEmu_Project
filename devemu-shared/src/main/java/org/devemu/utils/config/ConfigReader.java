package org.devemu.utils.config;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

public class ConfigReader {
	private XMLConfiguration conf = new XMLConfiguration();

	public void init(String arg1) {
		try {
			conf.setFileName(arg1);
			conf.load();
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
	}
	
	public Object get(ConfigEnum arg1) {
		return conf.getProperty(arg1.name());
	}

	public XMLConfiguration getConf() {
		return conf;
	}

	public void setConf(XMLConfiguration conf) {
		this.conf = conf;
	}
}
