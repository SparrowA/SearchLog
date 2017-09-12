package Core;

import Model.OpenFile;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Tab;
import javafx.scene.control.TreeItem;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Main extends Application {

    public static Stage primaryStage;

    private static List<OpenFile> mOpenFiles;

    private static FileChooser mFileChooser;

    private static DirectoryChooser directoryChooser;

    private static Alert mAlert;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/View/MainForm.fxml"));

        this.primaryStage = primaryStage;

        mOpenFiles = new LinkedList<>();
        mFileChooser = new FileChooser();
        directoryChooser = new DirectoryChooser();

        directoryChooser.setTitle("Choose Folder");


        mAlert = new Alert(Alert.AlertType.ERROR);

        mFileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Log file", "*.log"));

        primaryStage.setTitle("Search Logs");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void ShowError(String header, String text){
        mAlert.setHeaderText(header);
        mAlert.setContentText(text);
        mAlert.show();
    }

    public static File chooseDirectory(){
        return directoryChooser.showDialog(primaryStage);
    }

    public static File chooseFile(){
        return mFileChooser.showOpenDialog(primaryStage);
    }

    public static OpenFile AddNewFile(File file) throws FileNotFoundException {
        OpenFile openFile = new OpenFile(file);

        mOpenFiles.add(openFile);

        return openFile;
    }

    public static void DeleteOpenFileByTab(Tab tab){
        mOpenFiles.removeIf(x -> x.getParentTab() == tab);
    }


    public static TreeItem<String> FillChild(File root){
        TreeItem<String> temp = new TreeItem<>(root.getName());

        for (String file: root.list()) {
            File dir = new File(file);

            if(dir.isDirectory()){
                TreeItem<String> current = new TreeItem<>(dir.getName());
                current.getChildren().add(FillChild(dir));
                temp.getChildren().add(current);
            }
        }

        return temp;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
