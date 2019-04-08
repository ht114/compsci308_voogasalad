package ui.panes;

import engine.external.Entity;
import engine.external.component.NameComponent;
import engine.external.component.HeightComponent;
import engine.external.component.WidthComponent;
import engine.external.component.SpriteComponent;
import javafx.event.EventHandler;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import ui.AuthoringEntity;
import ui.DefaultTypesFactory;
import ui.manager.ObjectManager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Carrie Hunner
 * This is a page that will display all of the types created by the user
 */
public class UserCreatedTypesPane extends VBox {
    private EntityMenu myEntityMenu;
    private ResourceBundle myResources;
    private ObjectManager myObjectManager;
    private DefaultTypesFactory myDefaultTypesFactory;
    private DataFormat myDataFormat;
    private static final String RESOURCE = "default_entity_type";
    private static final String ASSET_IMAGE_FOLDER_PATH = "authoring/Assets/Images";

    /**
     *
     * @param dataFormat This must be the same dataformat passed into the Viewer
     *                   this allows these two panes to pass an entity
     */
    public UserCreatedTypesPane(DataFormat dataFormat, ObjectManager objectManager){
        myResources = ResourceBundle.getBundle(RESOURCE);
        myDataFormat = dataFormat;
        myObjectManager = objectManager;
        String title = myResources.getString("UserCreatedTitle");
        myEntityMenu = new EntityMenu(title);
        myDefaultTypesFactory = new DefaultTypesFactory();
        populateCategories();
        this.getChildren().add(myEntityMenu);
    }

    private void populateCategories() {
        for(String s : myDefaultTypesFactory.getCategories()){
            myEntityMenu.addDropDown(s);
        }
    }

    public void addUserDefinedType(String category, Entity entity){
        String label = (String) entity.getComponent(new NameComponent("").getClass()).getValue();
        String imageName = (String) entity.getComponent(new SpriteComponent("").getClass()).getValue();
        double width = (Double) entity.getComponent(new WidthComponent(0.0).getClass()).getValue();
        double height = (Double) entity.getComponent(new HeightComponent(0.0).getClass()).getValue();
        try {
            AuthoringEntity originalAuthoringEntity = new AuthoringEntity(entity, myObjectManager);
            ImageWithEntity imageWithEntity = new ImageWithEntity(new FileInputStream(ASSET_IMAGE_FOLDER_PATH + "/" + imageName), originalAuthoringEntity, width, height);
            UserDefinedTypeSubPane subPane = new UserDefinedTypeSubPane(imageWithEntity, label, entity);
            List<Pane> paneList = new ArrayList<>();
            paneList.add(subPane);
            myEntityMenu.addToDropDown(category, paneList);
            imageWithEntity.setOnDragDetected(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    System.out.println("Drag detected");
                    Dragboard db = imageWithEntity.startDragAndDrop(TransferMode.MOVE);
                    ClipboardContent content = new ClipboardContent();
                    //content.putImage(imageWithEntity);
                    content.put(myDataFormat, imageWithEntity.getAuthoringEntity());
                    db.setContent(content);
                    db.setDragView(imageWithEntity.getImage(), 0, 0);

                }
            });
        } catch (FileNotFoundException e) {
            //TODO: deal with this
            e.printStackTrace();
        }



    }
}
