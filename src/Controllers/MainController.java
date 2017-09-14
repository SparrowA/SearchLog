package Controllers;

import Core.Main;
import Model.FolderItem;
import Model.OpenFile;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by a.gusev on 12.09.2017.
 */
public class MainController {

    @FXML
    TreeView fileTree;

    @FXML
    TabPane tabPane;

    public void OnMenuAddTab() {
        Tab tab = new Tab("New tab " + (tabPane.getTabs().size() + 1));
        tabPane.getTabs().add(tab);
    }

    public void onMenuCloseTab(){
        Main.DeleteOpenFileByTab(tabPane.getSelectionModel().getSelectedItem());
        tabPane.getTabs().remove(tabPane.getSelectionModel().getSelectedItem());
    }

    public void onStartNewSearch() {
        File file = Main.chooseDirectory();

        if (file != null) {
            try {
                TreeItem<FolderItem> root = Main.FillChild(file, "looking");
                fileTree.setRoot(root);

                fileTree.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                    if (((FolderItem) ((TreeItem) newValue).getValue()).isFile()){
                        String filePath = ((FolderItem) ((TreeItem) newValue).getValue()).getPathFile();

                        if (!tabPane.getTabs().stream().anyMatch(y -> ((OpenFile) ((y).getUserData())).getFilePath().equals(filePath))) {
                            try {
                                OpenFile openFile = new OpenFile(((FolderItem) ((TreeItem) newValue).getValue()).getSourceFile());
                                tabPane.getTabs().addAll(openFile.getParentTab());
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                        } else {
                            tabPane.getSelectionModel().select(
                                    tabPane.getTabs().stream()
                                            .filter(x -> ((OpenFile) x.getUserData()).getFilePath().equals(filePath))
                                            .findAny()
                                            .get()
                            );
                        }
                    }
                });
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
