package Model;

import javafx.geometry.Insets;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.io.*;
import java.util.Scanner;

/**
 * Created by a.gusev on 12.09.2017.
 */
public class OpenFile {

    private Tab parentTab;

    private File sourceFile;

    private TextArea textArea;

    public OpenFile(File source) throws FileNotFoundException {
        if(source == null || !source.exists()){
            throw new FileNotFoundException("Source File Not Found");
        }

        sourceFile = source;

        CreateParentTab();
    }

    private void CreateParentTab() throws FileNotFoundException {
        parentTab = new Tab(sourceFile.getName());

        HBox hBox = new HBox();
        textArea = new TextArea();
        hBox.getChildren().add(textArea);

        HBox.setMargin(textArea, new Insets(5, 5, 5 ,5));
        hBox.setHgrow(textArea, Priority.ALWAYS);

        parentTab.setContent(hBox);

        UpdateContent();
    }

    private void UpdateContent() throws FileNotFoundException {
        Scanner fileReader = new Scanner(sourceFile);

        StringBuilder builder = new StringBuilder();

        while (fileReader.hasNextLine()){
            builder.append(fileReader.nextLine());
        }

        textArea.setText(builder.toString());
    }

    public Tab getParentTab(){
        return parentTab;
    }
}
