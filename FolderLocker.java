import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
//import javax.crypto.CipherOutputStream;
import javax.crypto.spec.SecretKeySpec;

public class FolderLocker {
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the path of the folder to lock/unlock:");
        String folderPath = sc.nextLine();

        System.out.println("Enter a password:");
        String password = sc.nextLine();

        System.out.println("Do you want to lock or unlock the folder? (lock/unlock)");
        String operation = sc.nextLine();

        if (operation.equalsIgnoreCase("lock")) {
            lockFolder(folderPath, password);
            System.out.println("Folder locked successfully.");
        } else if (operation.equalsIgnoreCase("unlock")) {
            unlockFolder(folderPath, password);
            System.out.println("Folder unlocked successfully.");
        } else {
            System.out.println("Invalid operation.");
        }

        sc.close();
    }

    public static void lockFolder(String folderPath, String password) {
        try {
            File folder = new File(folderPath);
            if (!folder.isDirectory()) {
                System.out.println("Invalid folder path.");
                return;
            }

            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        encryptFile(file, password);
                        file.delete();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void unlockFolder(String folderPath, String password) {
        try {
            File folder = new File(folderPath);
            if (!folder.isDirectory()) {
                System.out.println("Invalid folder path.");
                return;
            }

            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        decryptFile(file, password);
                        file.delete();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void encryptFile(File inputFile, String password) throws IOException {
        FileInputStream inputStream = new FileInputStream(inputFile);
        FileOutputStream outputStream = new FileOutputStream(inputFile.getPath() + ".encrypted");
        encryptDecrypt(password, Cipher.ENCRYPT_MODE, inputStream, outputStream);
        inputStream.close();
        outputStream.flush();
        outputStream.close();
    }

    private static void decryptFile(File inputFile, String password) throws IOException {
        FileInputStream inputStream = new FileInputStream(inputFile);
        FileOutputStream outputStream = new FileOutputStream(inputFile.getPath().replace(".encrypted", ""));
        encryptDecrypt(password, Cipher.DECRYPT_MODE, inputStream, outputStream);
        inputStream.close();
        outputStream.flush();
        outputStream.close();
    }

    private static void encryptDecrypt(String password, int cipherMode, FileInputStream inputStream,
                                       FileOutputStream outputStream) throws IOException {
        try {
            byte[] keyBytes = password.getBytes();
            SecretKeySpec key = new SecretKeySpec(keyBytes, ALGORITHM);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(cipherMode, key);

            CipherInputStream cipherInputStream = new CipherInputStream(inputStream, cipher);
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = cipherInputStream.read(buffer)) >= 0) {
                outputStream.write(buffer, 0, bytesRead);
            }
            cipherInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
