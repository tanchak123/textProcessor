package com.ithillel.service.appcontext;

import com.ithillel.service.storage.Storage;

import java.io.*;
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
    private static final String FILE_NAME = "src\\main\\resources\\application.json";

    public JsonApplicationContext() {
        createBeans();
    }

    @Override
    public Object getBean(String name)  {
        try (FileReader fileReader = new FileReader(FILE_NAME)) {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(fileReader);
            JSONArray jsonArray = (JSONArray) jsonObject.get("beans");
            for (Object obj : jsonArray) {
                JSONObject js = (JSONObject) obj;
                String objName = (String) js.get("name");
                if (objName.equals("textProcessor") && name.equals("textProcessor")) {
                    String oldType = beans.get(objName).getClass().getName();
                    String jsonType = (String) js.get("type");
                    System.out.println(oldType + "|||||||||||" + jsonType);
                    if (!oldType.equals(jsonType)) {
                        changeBean(objName, jsonType);
                    }
                }
            }
            fileReader.getEncoding();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return beans.get(name);
    }

    private void changeBean(String name, String jsonType) throws ClassNotFoundException, NoSuchMethodException,
            IllegalAccessException, InvocationTargetException, InstantiationException {
        if (jsonType.equals("com.ithillel.service.textprocessor.FileTextProcessor")) {
            beans.put(name, Class.forName(jsonType).getDeclaredConstructor().newInstance());
        } else {
            beans.put(name,
                    Class.forName(jsonType).getDeclaredConstructor(
                            Storage.class).newInstance(getBean("storage")));
        }
        System.out.println("Bean changed to " + jsonType);
    }

    private void createBeans() {
        try (FileReader fileReader = new FileReader(FILE_NAME)) {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(fileReader);
            JSONArray jsonArray = (JSONArray) jsonObject.get("beans");
            for (Object obj : jsonArray) {
                JSONObject js = (JSONObject) obj;
                String name = (String) js.get("name");
                if (name.equals("storage")) {
                    beans.put(name,
                            Class.forName((String) js.get("type"))
                                    .getDeclaredConstructor().newInstance()
                    );
                } else {
                    String jsonType = (String) js.get("type");
                    System.out.println(jsonType);
                    if (jsonType.substring(jsonType.lastIndexOf(".") + 1).equals("FileTextProcessor")) {
                        beans.put(name, Class.forName(jsonType).getDeclaredConstructor().newInstance());
                    } else {
                        String s = (String) ((JSONArray) js.get("constructorArgs")).get(0);
                        System.out.println(s);
                        beans.put(name,
                                Class.forName(jsonType).getDeclaredConstructor(
                                        Storage.class).newInstance(getBean("storage"))
                        );
                    }
                }
            }

        } catch (IOException | ParseException | ClassNotFoundException | NoSuchMethodException
                | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

        public static void main(String[] args) throws FileNotFoundException, InterruptedException {
            JsonApplicationContext applicationContext = new JsonApplicationContext();
            int count = 0;
        while (true) {
            System.out.println("STEP : " + count++);
            System.out.println(applicationContext.getBean("storage"));
            System.out.println(applicationContext.getBean("textProcessor"));
            Thread.sleep(1000);
        }
    }

}
