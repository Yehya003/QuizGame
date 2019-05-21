package application.utils;

import java.io.*;

public abstract class FileUtils {

    public final static String accountFilePath = "src/application/Account.txt";

    public static Object readObject(String filePath) {
        File file = new File(filePath);
        try (ObjectInputStream oIn = new ObjectInputStream(new FileInputStream(file))) {
            return oIn.readObject();
        } catch (Exception ex) {
            ex.printStackTrace();
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
