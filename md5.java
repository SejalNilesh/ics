import java.io.*;
import java.security.MessageDigest;
import java.util.*;

public class md5 {

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        System.out.println("1. MD5 for Text");
        System.out.println("2. MD5 for File");
        System.out.print("Enter choice: ");
        int ch = sc.nextInt();
        sc.nextLine(); // consume newline

        switch (ch) {
            case 1 -> md5Text(sc);
            case 2 -> md5File(sc);
            default -> System.out.println("Invalid choice");
        }

        sc.close();
    }

    // -------- MD5 of Text --------
    static void md5Text(Scanner sc) throws Exception {
        System.out.print("Enter text: ");
        String input = sc.nextLine();

        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] hash = md.digest(input.getBytes());

        System.out.println("MD5: " + toHex(hash));
    }

    // -------- MD5 of File --------
    static void md5File(Scanner sc) throws Exception {
        System.out.print("Enter file path: ");
        String path = sc.nextLine();

        FileInputStream fis = new FileInputStream(path);
        MessageDigest md = MessageDigest.getInstance("MD5");

        byte[] buffer = new byte[1024];
        int bytesRead;

        while ((bytesRead = fis.read(buffer)) != -1) {
            md.update(buffer, 0, bytesRead);
        }

        byte[] hash = md.digest();
        fis.close();

        System.out.println("MD5: " + toHex(hash));
    }

    // -------- Convert to Hex --------
    static String toHex(byte[] hash) {
        StringBuilder sb = new StringBuilder();
        for (byte b : hash) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
