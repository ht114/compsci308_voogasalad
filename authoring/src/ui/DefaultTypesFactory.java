package ui;

import engine.external.Entity;
import engine.external.component.*;
import javafx.geometry.Point3D;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author Carrie Hunner
 * This class creates a list of all categories from the "default_types_factory" properties file
 * It also can return default entities that the author can base their types off of
 */
public class DefaultTypesFactory {
    private Map<String, Map<String, String>> myMap;
    private ResourceBundle myResources;
    private List<String> myCategoriesList;

    private static final String RESOURCES = "default_types_factory";

    public DefaultTypesFactory(){
        myResources = ResourceBundle.getBundle(RESOURCES);
        myCategoriesList = new ArrayList<>();
        myCategoriesList.addAll(myResources.keySet());
        myMap = new HashMap<>();
        fillMaps();
    }

    /**
     * Used by both DefaultTypesPane and UserCreatedTypesPane to get the headers
     * @return List of Strings of the type categories
     */
    public List<String> getCategories(){
        return Collections.unmodifiableList(myCategoriesList);
    }

    /**
     * Used by DefaultTypesPane to get the specific available default types
     * @param category String of the category
     * @return List of the specific default types
     */
    public List<String> getTypes(String category){
        List<String> list = new ArrayList<>();
        list.addAll(myMap.get(category).keySet());
        return Collections.unmodifiableList(list);
    }

    public Entity getDefaultEntity(String ofType, String basedOn){
        String methodName = myMap.get(ofType).get(basedOn);
        try {
            Method method = this.getClass().getDeclaredMethod(methodName);
            return (Entity) method.invoke(this);
        } catch (NoSuchMethodException e) {
            //TODO: get rid of this
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //TODO: this is bad
        return new Entity();

    }
    private void fillMaps(){
        for(String s1 : myResources.keySet()){
            HashMap<String, String> basedOnToMethod = new HashMap<>();
            String[] defaultTypes = myResources.getString(s1).split(",");
            System.out.println("Made it past split");
            for(String s2 : defaultTypes){
                System.out.println(s2);
                String[] nameToMethod = s2.split(" ");
                String typeName = nameToMethod[0];
                String method = nameToMethod[1];
                basedOnToMethod.put(typeName, method);
            }
            myMap.put(s1, basedOnToMethod);
        }
    }

    private Entity createCloud(){
        Entity cloud = new Entity();
        GravityComponent gravityComponent = new GravityComponent(0.0);
        NameComponent nameComponent = new NameComponent("Cloud");
        VelocityComponent velocityComponent = new VelocityComponent(new Point3D(10, 0, 0));
        VisibilityComponent visibilityComponent = new VisibilityComponent(true);
        SizeComponent sizeComponent = new SizeComponent(100.0);
        cloud.addComponent(gravityComponent);
        cloud.addComponent(nameComponent);
        cloud.addComponent(velocityComponent);
        cloud.addComponent(visibilityComponent);
        cloud.addComponent(sizeComponent);
        return cloud;
    }

    private Entity createBlock(){
        Entity block = new Entity();
        NameComponent nameComponent = new NameComponent("Block");
        VisibilityComponent visibilityComponent = new VisibilityComponent(true);
        SizeComponent sizeComponent = new SizeComponent(50.0);
        block.addComponent(nameComponent);
        block.addComponent(visibilityComponent);
        block.addComponent(sizeComponent);
        return block;
    }

    //TODO: Add key input and events - collisions
    private Entity createHero(){
        Entity hero = new Entity();
        NameComponent nameComponent = new NameComponent("Hero");
        GravityComponent gravityComponent = new GravityComponent(9.8);
        SizeComponent sizeComponent = new SizeComponent(75.0);
        hero.addComponent(nameComponent);
        hero.addComponent(gravityComponent);
        hero.addComponent(sizeComponent);
        return hero;
    }

    private Entity createGrow(){
        Entity grow = new Entity();
        VisibilityComponent visibilityComponent = new VisibilityComponent(true);
        SizeComponent sizeComponent = new SizeComponent(25.0);
        grow.addComponent(visibilityComponent);
        grow.addComponent(sizeComponent);
        return grow;
    }
}
