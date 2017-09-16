package Controllers;

import Core.Main;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by a.gusev on 15.09.2017.
 */
public class SearchSettingController {

    @FXML
    private ListView<String> listExtension;

    public void AddExtension(){
        listExtension.getItems().add("text");

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
}
