package com.ithillel;

import com.ithillel.service.appcontext.ApplicationContext;
import com.ithillel.service.appcontext.JsonApplicationContext;
import com.ithillel.service.textprocessor.TextProcessor;

public class Application {

    private final ApplicationContext applicationContext = new JsonApplicationContext();
    private final TextProcessor textProcessor;

    public Application() {
        textProcessor = (TextProcessor) applicationContext.getBean("textProcessor");
    }

    public void save(String key, final String text) {
        textProcessor.save(key, text);
    }

    public String getByKey(String key) {
        return textProcessor.getByKey(key);
    }
  
    public static void main(String[] args) throws InterruptedException {
        Application application = new Application();
        application.save("1.txt", "ASD");
        application.save("vasd", "123dsa");
        System.out.println(application.getByKey("1.txt"));
        System.out.println(application.getByKey("vasd"));
        int count = 0;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }
}
