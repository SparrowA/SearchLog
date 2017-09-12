package Model;

import javafx.scene.control.TreeItem;

import java.io.File;
import java.io.FileNotFoundException;

public class FolderItem {

    private File mSourceFolder;

    private TreeItem<String> mItem;

    public FolderItem(File source) throws FileNotFoundException {
        if(source == null || !source.exists()){
          throw new FileNotFoundException("Directory not found");
        }

        mSourceFolder = source;

        CreateTreeItem();
    }

    private void CreateTreeItem(){
        mItem = new TreeItem<>();
        mItem.setValue(mSourceFolder.getName());
    }

    public TreeItem<String> getTreeItem(){
        return mItem;
    }
}
