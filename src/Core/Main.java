package Core;

import Model.FolderItem;
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
import java.util.*;

public class Main extends Application {

    public static Stage primaryStage;

    private static List<OpenFile> mOpenFiles;

    private static DirectoryChooser directoryChooser;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/View/MainForm.fxml"));

        this.primaryStage = primaryStage;

        mOpenFiles = new LinkedList<>();
        directoryChooser = new DirectoryChooser();

        directoryChooser.setTitle("Choose Folder");

        primaryStage.setTitle("Search Logs");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static File chooseDirectory(){
        return directoryChooser.showDialog(primaryStage);
    }

    public static void DeleteOpenFileByTab(Tab tab){
        mOpenFiles.removeIf(x -> x.getParentTab() == tab);
    }

    public static TreeItem<FolderItem> FillChild(File root, String lookingText) {
        TreeItem<FolderItem> temp = null;

        if(root != null) {
            try {
                temp = new TreeItem<>(new FolderItem(root));

                for (File file : root.listFiles()) {
                    if (file.isDirectory()) {
                        TreeItem<FolderItem> node = FillChild(file, lookingText);
                        if (node != null)
                            temp.getChildren().add(node);
                    } else if (file.getName().contains(".log") && IsContainsText(file, lookingText)) {
                        temp.getChildren().add(new TreeItem<>(new FolderItem(file)));
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
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
