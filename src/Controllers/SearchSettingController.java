package Controllers;

import Core.Main;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by a.gusev on 15.09.2017.
 */

class EditListCell extends TextFieldListCell<String>{

    private TextField mTextField;

    public EditListCell() {
        super();

        mTextField = new TextField();

        mTextField.setText(getItem());

        mTextField.setOnKeyPressed(event -> {
            if(event.getCode().equals(KeyCode.ENTER)){
                commitEdit(mTextField.getText());
            }
        });

        mTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue){
                commitEdit(mTextField.getText());
            }
        });

        updateItem(getItem(), false);
    }

    @Override
    public void startEdit() {
        super.startEdit();

        mTextField.setText(getItem());
        setText(null);
        setGraphic(mTextField);
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();

        setText(mTextField.getText());
        setGraphic(null);
    }

    @Override
    public void commitEdit(String newValue) {
        super.commitEdit(newValue);
    }

    @Override
    public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);

        if(empty){
            setGraphic(null);
            setText(null);
        }
        else
        {
            if(item == null){
                mTextField.setText(null);
                setGraphic(null);
            }
            else{
                mTextField.setText(item);
                setGraphic(null);
                setText(item);
            }
        }
    }


}

public class SearchSettingController implements Initializable {

    @FXML
    private ListView<String> listExtension;

    public void AddExtension(){
        listExtension.getItems().add("New Extension");
    }

    public void SaveExtensionSetting(){
        if(listExtension.getItems().size() == 0){
            Main.ShowAllert("Внимание!", "Не задано ни одно расширение для поиска!");
        }

        ((Stage)listExtension.getScene().getWindow()).close();
    }

    public void setExtension(ObservableList<String> extension){
        listExtension.setItems(extension);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listExtension.setCellFactory(cf -> new EditListCell());
    }
}
