package Core;

import Model.FolderItem;
import Model.OpenFile;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Tab;
import javafx.scene.control.TreeItem;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Stream;

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

    public static void DeleteOpenFileByTab(Tab tab){
        mOpenFiles.removeIf(x -> x.getParentTab() == tab);
    }

    public static TreeItem<FolderItem> FillChild(File root, String lookingText) throws FileNotFoundException {
        TreeItem<FolderItem> temp = new TreeItem<>(new FolderItem(root));

        for (File file: root.listFiles()) {
            if (file.isDirectory()) {
                TreeItem<FolderItem> node = FillChild(file, lookingText);
                if(node != null)
                    temp.getChildren().add(node);
            }
            else if (file.getName().contains(".log") && IsContainsText(file, lookingText)) {
                temp.getChildren().add(new TreeItem<>(new FolderItem(file)));
            }
        }

        return temp.getChildren().size() == 0 ? null : temp;
    }

    private static boolean IsContainsText(File source, String searchText) throws FileNotFoundException {
        Scanner scanner = new Scanner(source);

        boolean result = false;

        while (scanner.hasNextLine()){
            if(scanner.nextLine().contains(searchText)){
                result = true;
                break;
            }
        }

        return result;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
