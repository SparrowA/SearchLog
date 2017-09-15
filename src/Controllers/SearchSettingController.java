package Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;

/**
 * Created by a.gusev on 15.09.2017.
 */
public class SearchSettingController {

    @FXML
    private TableView<String> tableExtension;

    public void AddExtension(){
        tableExtension.getItems().add("text");

    }
}
