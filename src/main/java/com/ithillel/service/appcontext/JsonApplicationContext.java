package com.ithillel.service.appcontext;

import com.ithillel.service.storage.Storage;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonApplicationContext implements ApplicationContext {

    private static final Map<String, Object> beans = new HashMap<>();
    private static final JSONParser jsonParser = new JSONParser();

    public JsonApplicationContext() {
        try (FileReader fileReader = new FileReader("src\\main\\resources\\application.json")) {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(fileReader);
            JSONArray jsonArray = (JSONArray) jsonObject.get("beans");
            for (Object obj : jsonArray) {
                JSONObject js = (JSONObject) obj;
                String name = (String) js.get("name");
                System.out.println(name);
                System.out.println(js.get("type"));
                if (name.equals("storage")) {
                    beans.put(name,
                            Class.forName((String) js.get("type"))
                                    .getDeclaredConstructor().newInstance()
                    );
                } else {
                    String s = (String) ((JSONArray) js.get("constructorArgs")).get(0);
                    beans.put(name,
                            Class.forName((String) js.get("type")).getDeclaredConstructor(
                                    Storage.class).newInstance(getBean(s))
                    );
                }
            }

        } catch (IOException | ParseException | ClassNotFoundException | NoSuchMethodException
                | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object getBean(String name) {
        return beans.get(name);
    }

    public static void main(String[] args) {
        ApplicationContext applicationContext = new JsonApplicationContext();
        System.out.println(applicationContext.getBean("textProcessor"));
        System.out.println(applicationContext.getBean("storage"));
    }
}
