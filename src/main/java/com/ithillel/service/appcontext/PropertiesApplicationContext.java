package com.ithillel.service.appcontext;

import com.ithillel.service.storage.Storage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertiesApplicationContext implements ApplicationContext {

      private static Map<String, Object> beans = new HashMap<>();

    public PropertiesApplicationContext() {
        Properties applicationProperties = new Properties();
        try {
            applicationProperties.load(getClass().getClassLoader()
                    .getResourceAsStream("application.properties"));
        for (int i = 0; i < 2; i++) {
            String name = applicationProperties.getProperty("beans[" + i + "].name");
            System.out.println(name);
            String type = applicationProperties.getProperty("beans[" + i + "].type");
            if (name.equals("textStorage")) {
                    Object bean = getBean(applicationProperties.getProperty("beans[1].args"));
                    System.out.println(bean);
                    beans.put(name, Class.forName(type).getDeclaredConstructor(Storage.class)
                            .newInstance(bean));
                } else {
                    beans.put(name, Class.forName(type).getDeclaredConstructor().newInstance());
                }
        }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object getBean(String name) {
        return beans.get(name);
    }

    public static void main(String[] args) {
        PropertiesApplicationContext p = new PropertiesApplicationContext();

    }
}
