package com.ithillel.service.appcontext;

import com.ithillel.service.annotation.CustomBean;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InjectorApplicationContext implements ApplicationContext {

    private static Map<String, Object> beans = new HashMap<>();

    public InjectorApplicationContext() throws ClassNotFoundException {
        List<Class<?>> classes = addClasses("src\\main\\java\\com\\ithillel");
        for (Class<?> c : classes) {
            if (c.isAnnotationPresent(CustomBean.class)) {
                String name = c.getName();
                System.out.println(name.substring(name.lastIndexOf('.') + 1));
                beans.put(name.substring(name.lastIndexOf('.') + 1), c);
            }
        }
    }

    @Override
    public Object getBean(String name) {
        return beans.get(name);
    }

    private List<Class<?>> addClasses(String path) throws ClassNotFoundException {
        File directory = new File(path);
        List<Class<?>> classes = new ArrayList<>();
        if (!directory.exists()) {
            System.out.println("Directory not exist");
            return new ArrayList<>();
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                classes.addAll(addClasses(file.getPath()));
            } else if (file.getName().endsWith(".java")) {
                classes.add(Class.forName(
                        (path + "." + file.getName().substring(0, file.getName().length() - 5))
                                .replace("src\\main\\java\\", "")
                                .replaceAll("\\\\", ".")
                ));
            }
        }
        return classes;
    }

    public static void main(String[] args) throws ClassNotFoundException {
        InjectorApplicationContext applicationContext = new InjectorApplicationContext();
        applicationContext.getBean("InMemoryTextProcessor");
    }
}
