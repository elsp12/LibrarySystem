package util;

import java.io.*;

public class FileUtil {

    public static void save(Object obj, String file) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
            out.writeObject(obj);
        } catch (Exception ignored) {}
    }

    public static Object load(String file) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            return in.readObject();
        } catch (Exception e) {
            return null;
        }
    }
}
