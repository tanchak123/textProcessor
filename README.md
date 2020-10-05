## My HOMEWORK description
* Create application.json and refactor application.properties
* Refactor our PropertiesAppContext
* Let's create new class on service with name 'JsonApplicationContext' with impl AppContext
* Change on our Application.class one variable form PropertiesApplicationContext to:
*     private ApplicationContext applicationContext = new JsonApplicationContext();
* Import dependency json-simple for work with json format.
* Read some info about json objects how read it and write.
* I got troubles with constructorArgs, but I resolve it.
* RESULT :
*     I realzie method JsonApplicationContext and refactor old PropertiesApplicationContext for new properties file.

## Task with *
* For realize this tusk we need create package annotation and class-anno CustomBean.
* Create InjectorApplicationContext on ou appContext package which implements app context.
* Create a constructor, genBean methods and Map of our beans.
* Add anno CustomBean to class InMemoryTextProcessor
* Need method which check all classes of our pack with name and add classes with ano CustomBean to our beans storage.
* Let's name method addClasses. 
* For realize that method I should use recurse cause, file can be directory and file.
* After all classes found I should use this list to find anno CustomBean like this :
*      for (Class<?> c : classes) {
              if (c.isAnnotationPresent(CustomBean.class)) {
                  System.out.println("|||||" + c.getName());
                  String name = c.getName();
                  System.out.println(name.substring(name.lastIndexOf('.') + 1));
                  beans.put(name.substring(name.lastIndexOf('.') + 1), c);
              }
          }
* All tusks is over let's use our checkstyle plugin.
* RESULT:
*     Now i know how without any libraries get all classes on package.
      Understand that path of class must be separated with '.' and start with package 'java'.
      Understand how deal with AppContext, IOC container and BeanDefenition.
