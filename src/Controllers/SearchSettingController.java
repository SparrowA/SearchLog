package Controllers;

import Core.Main;
import Model.FileExtension;
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

class EditListCell extends TextFieldListCell<FileExtension>{

    private TextField mTextField;

    public EditListCell() {
        super();

        mTextField = new TextField();

        mTextField.setOnKeyPressed(event -> {
            if(event.getCode().equals(KeyCode.ENTER)){
                commitEdit(FileExtension.GetInstanceExtension(mTextField.getText()));
            }
        });

        mTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue){
                commitEdit(FileExtension.GetInstanceExtension(mTextField.getText()));
            }
        });

        updateItem(getItem(), false);
    }

    @Override
    public void startEdit() {
        super.startEdit();

        mTextField.setText(getItem().getmExtension());
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
    public void updateItem(FileExtension item, boolean empty) {
        super.updateItem(item, empty);

        if(empty){
            setGraphic(null);
            setText(null);
        }
        else
        {
            if(item == null || item.toString().isEmpty()){
                mTextField.setText(null);
                setGraphic(null);
                if(getListView() != null && getListView().getItems() != null){
                    getListView().getItems().remove(item);
                }
            }
            else{
                mTextField.setText(item.getmExtension());
                setGraphic(null);
                setText(item.getmExtension());
            }
        }
    }


}

public class SearchSettingController implements Initializable {

    @FXML
    private ListView<FileExtension> listExtension;

    public void AddExtension(){
        listExtension.getItems().add(new FileExtension(".NewExtension"));
    }

    public void SaveExtensionSetting(){
        if(listExtension.getItems().size() == 0){
            Main.ShowAlert("Внимание!", "Не задано ни одно расширение для поиска!");
        }

        ((Stage)listExtension.getScene().getWindow()).close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listExtension.setCellFactory(cf -> new EditListCell());
        listExtension.setItems(Main.getExtensionFile());
    }
}
