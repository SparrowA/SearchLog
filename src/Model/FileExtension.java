package Model;

import java.util.regex.Pattern;

public class FileExtension {

    private String mExtension;

    public static FileExtension GetInstanceExtension(String extension) throws IllegalArgumentException{
        FileExtension newExtension = null;

        if(!extension.isEmpty())
            newExtension = new FileExtension(extension);

        return newExtension;
    }

    public FileExtension(String extension) throws IllegalArgumentException{
        if(!IsCorrect(extension)){
            throw new IllegalArgumentException("Not correct extension");
        }
        mExtension = extension;
    }

    public boolean IsContainExtension(String fileName){
        return fileName.contains(mExtension);
    }

    private boolean IsCorrect(String testExtension){
        return Pattern.matches("\\..+$", testExtension);
    }

    public String getmExtension() {
        return mExtension;
    }

    @Override
    public String toString() {
        return mExtension;
    }
}
