package Model;

import javafx.scene.control.TreeItem;

import java.io.File;
import java.io.FileNotFoundException;

public class FolderItem {

    private File mSourceFolder;

    public FolderItem(File source) throws FileNotFoundException {
        if(source == null || !source.exists()){
          throw new FileNotFoundException("Directory not found");
        }

        mSourceFolder = source;
    }

    public String toString(){
        return mSourceFolder.getName();
    }

    public String getPathFile(){
        return mSourceFolder.getAbsolutePath();
    }

    public File getSourceFile(){
        return mSourceFolder;
    }

    public boolean isFile(){
        return mSourceFolder.isFile();
    }
}
