package Core;

import Model.FileExtension;
import Model.FolderItem;
import Model.OpenFile;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Tab;
import javafx.scene.control.TreeItem;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main extends Application {

    public static Stage primaryStage;

    public static String mLookingText;

    private static List<OpenFile> mOpenFiles;

    private static DirectoryChooser directoryChooser;

    private static ObservableList<FileExtension> mExtensionFile = FXCollections.observableArrayList();

    private static Alert mAlert;

    private static File mSelectedDirectory;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/View/MainForm.fxml"));

        this.primaryStage = primaryStage;

        mOpenFiles = new LinkedList<>();

        directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Choose Folder");

        mAlert = new Alert(Alert.AlertType.WARNING);

        InitExtensionList();

        primaryStage.setTitle("Search Logs");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void ShowAlert(String title, String message){
        mAlert.setTitle(title);
        mAlert.setContentText(message);

        mAlert.show();
    }

    private static void InitExtensionList(){
        mExtensionFile.add(new FileExtension(".log"));
    }

    public static void chooseDirectory(){
        mSelectedDirectory = directoryChooser.showDialog(primaryStage);
    }

    public static File getSelectedDirectory(){
        if(mSelectedDirectory == null){
            chooseDirectory();
        }

        return mSelectedDirectory;
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
                    } else if (IsExtension(file.getName()) && IsContainsText(file, lookingText)) {
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

    private static boolean IsExtension(String textExtension){
        return mExtensionFile.filtered(x -> x.IsContainExtension(textExtension)).size() != 0;
    }

    private static boolean IsContainsText(File source, String searchText) throws FileNotFoundException {
        boolean result = false;

        if(searchText != null && !searchText.isEmpty()) {
            Scanner scanner = new Scanner(source);

            while (scanner.hasNextLine()) {
                if (scanner.nextLine().contains(searchText)) {
                    result = true;
                    break;
                }
            }
        }
        else {
            result = true;
        }

        return result;
    }

    public static ObservableList<FileExtension> getExtensionFile(){
        if(mExtensionFile.size() == 0){
            InitExtensionList();
        }

        return mExtensionFile;
    }

    public static String getExtensionText(){
        StringBuilder extension = new StringBuilder();

        mExtensionFile.stream().forEach(x -> {
            extension.append(x.getmExtension());
            extension.append(";");
        });

        return extension.toString();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
