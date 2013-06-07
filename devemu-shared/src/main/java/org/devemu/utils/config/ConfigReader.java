package org.devemu.utils.config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class ConfigReader {
    private Config conf = null;

    public void init(String arg1) {
        conf = ConfigFactory.load(arg1);
    }

    public Object get(ConfigEnum arg1) {
        return conf.getAnyRef("devemu." + arg1.name());
    }

    public Config getConf() {
        return conf;
    }

    public void setConf(Config conf) {
        this.conf = conf;
    }
}