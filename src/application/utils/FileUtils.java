package application.utils;

import javafx.scene.control.Alert;

import java.io.*;

public abstract class FileUtils {

    public final static String accountFilePath = "src/application/Account.txt";

    public static Object readObject(String filePath) {
        File file = new File(filePath);
        if(file.canExecute()) {
            try (ObjectInputStream oIn = new ObjectInputStream(new FileInputStream(file))) {
                return oIn.readObject();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("File can not be read!");
            alert.showAndWait();
        }
        return null;
    }

    public static void writeObject(String filePath, Object serializableObject) {
        File file = new File(filePath);
        try (ObjectOutputStream oOut = new ObjectOutputStream(new FileOutputStream(file))) {
            oOut.writeObject(serializableObject);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
