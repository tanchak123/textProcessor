package com.ithillel;

import com.ithillel.service.appcontext.ApplicationContext;
import com.ithillel.service.appcontext.PropertiesApplicationContext;
import com.ithillel.service.textprocessor.TextProcessor;
import java.io.IOException;

public class Application {

    private ApplicationContext applicationContext = new PropertiesApplicationContext();
    private TextProcessor textProcessor;

    public Application() {
        textProcessor = (TextProcessor) applicationContext.getBean("textStorage");
    }

    public void save(String key, final String text) {
        textProcessor.save(key, text);
    }

    public String getByKey(String key) {
        return textProcessor.getByKey(key);
    }

    public static void main(String[] args) {
        Application application = new Application();
        application.save("1.txt", "ASD");
        application.save("vasd", "123dsa");
        System.out.println(application.getByKey("1.txt"));
        System.out.println(application.getByKey("vasd"));
    }

}
