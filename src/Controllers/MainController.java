package Controllers;

import Core.Main;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;

/**
 * Created by a.gusev on 12.09.2017.
 */
public class MainController {

    @FXML
    TreeView fileTree;

    @FXML
    TabPane tabPane;


    public void OnMenuAddTab(){
        Tab tab = new Tab("New tab " + (tabPane.getTabs().size() + 1) );
        tabPane.getTabs().add(tab);
    }

    public void onMenuCloseTab(){
        Main.DeleteOpenFileByTab(tabPane.getSelectionModel().getSelectedItem());
        tabPane.getTabs().remove(tabPane.getSelectionModel().getSelectedItem());
    }

    public void onStartNewSearch(){
        File file = null;

        try {
            file = Main.chooseDirectory();

            if (file != null) {
                TreeItem<String> root = new TreeItem<>(file.getName());
                String[] listFile = file.list(new FilenameFilter() {
                    @Override
                    public boolean accept(File dir, String name) {
                        return name.contains(".log");
                    }
                });

                for (String name : listFile) {

                }
            }
            if (file != null)
              tabPane.getTabs().add(Main.AddNewFile(file).getParentTab());
        } catch (FileNotFoundException e) {
            Main.ShowError("File not found!", "File called " + file.getName() + " not found!");
        }
    }
}
