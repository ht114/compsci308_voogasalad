package ui.panes;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ui.Utility;

import java.lang.reflect.Method;
import java.util.ResourceBundle;


public class CreateNewObjectTypePane extends Stage {
    private GridPane myGridPane;
    private ResourceBundle myWindowResources;
    private ResourceBundle myTypeResources;
    private String[] myTypes;
    private ComboBox myTypeOfComboBox;
    private ComboBox myBasedOnComboBox;
    private TextField myTextField;
    private HBox myButtonPane;

    private static final Insets INSETS = new Insets(10,10,10,10);
    private static final int STAGE_HEIGHT = 300;
    private static final int STAGE_WIDTH = 400;
    private static final int GRIDPANE_GAP = 10;
    private static final int INPUT_WIDTH = 100;
    private static final String STYLE_SHEET = "default.css";
    private static final String WINDOW_RESOURCES = "new_object_window";
    private static final String TYPE_RESOURCES = "default_entity_type";

    public CreateNewObjectTypePane(){
        initializeGridPane();
        initializeVariables();
        myWindowResources = ResourceBundle.getBundle(WINDOW_RESOURCES);
        myTypeResources = ResourceBundle.getBundle(TYPE_RESOURCES);
        createContent();
        initializeAndDisplayStage();
    }

    private void initializeVariables() {
        myBasedOnComboBox = new ComboBox();
        myTypeOfComboBox = new ComboBox();
        myTextField = new TextField();
        myButtonPane = new HBox();
    }

    private void initializeGridPane(){
        myGridPane = new GridPane();
        myGridPane.setAlignment(Pos.TOP_CENTER);
        myGridPane.setHgap(GRIDPANE_GAP);
        myGridPane.setVgap(GRIDPANE_GAP);
    }

    private void createContent() {
        createAndAddLabel(myWindowResources.getString("Label1"));
        createAndAddTextField();
        createAndAddLabel(myWindowResources.getString("Label2"));
        createAndAddTypeOfOnDropDown();
        createAndAddLabel(myWindowResources.getString("Label3"));
        createAndAddBasedOnDropDown();
        createButtonPane();
    }

    private void createButtonPane() {
        String[] buttons = myWindowResources.getString("Buttons").split(",");
        for(String s : buttons){
            String[] info = s.split(" ");
            myButtonPane.getChildren().add(Utility.makeButon(this, info[1], info[0]));
        }
        myButtonPane.setPadding(INSETS);
        myButtonPane.setAlignment(Pos.CENTER);
        myButtonPane.setSpacing(GRIDPANE_GAP);
    }

    private void handleCloseButton(){
        System.out.println("Handle close button method called");
        this.close();
    }

    private Button makeButton(String text, String methodName){
        Button button = new Button();
        button.setOnMouseClicked(e -> {
            try {
                Method buttonMethod = this.getClass().getDeclaredMethod(methodName);
                buttonMethod.invoke(this);
            } catch (Exception e1) {
                button.setText(myWindowResources.getString("ButtonFail"));
            }});
        button.setText(text);
        return button;
    }

    private void createAndAddBasedOnDropDown() {
        myBasedOnComboBox.setPrefWidth(INPUT_WIDTH);
        myGridPane.add(myBasedOnComboBox, 1, myGridPane.getRowCount()-1);
    }

    private void populateBasedOnDropDown(String s) {
        String[] dropDownContent = myTypeResources.getString(s).split(",");
        myBasedOnComboBox.getItems().clear();
        myBasedOnComboBox.getItems().addAll(dropDownContent);
    }

    private void createAndAddLabel(String s) {
        Label label = new Label(s);
        label.setAlignment(Pos.CENTER);
        int numRows = myGridPane.getRowCount();
        myGridPane.add(label, 0, numRows+1);
    }

    private void createAndAddTextField(){
        myTextField.setPrefWidth(INPUT_WIDTH);
        myGridPane.add(myTextField, 1, myGridPane.getRowCount()-1);
    }

    private void createAndAddTypeOfOnDropDown(){
        String[] types = myTypeResources.getString("Tabs").split(",");
        myTypeOfComboBox.getItems().addAll(types);
        myTypeOfComboBox.setPrefWidth(INPUT_WIDTH);
        myGridPane.add(myTypeOfComboBox, 1, myGridPane.getRowCount()-1);
        myTypeOfComboBox.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                System.out.println("Registered change");
                populateBasedOnDropDown((String) t1);
            }
        });
    }
    private void initializeAndDisplayStage() {
        this.setWidth(STAGE_WIDTH);
        this.setHeight(STAGE_HEIGHT);
        TitledPane titledPane = new TitledPane();
        titledPane.setText(myWindowResources.getString("Title"));
        VBox contents = new VBox();
        contents.getChildren().addAll(myGridPane, myButtonPane);
        titledPane.setContent(contents);
        titledPane.setCollapsible(false);
        Scene scene = new Scene(titledPane);
        scene.getStylesheets().add(STYLE_SHEET);
        this.setScene(scene);
        this.showAndWait();
    }
}
