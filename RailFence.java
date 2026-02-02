import java.util.Scanner;

class RailFence {

    // Encryption
    static String encrypt(String s, int k) {
        // validations
        if (s == null)
            throw new IllegalArgumentException("Text cannot be null");

        if (s.length() == 0)
            throw new IllegalArgumentException("Text cannot be empty");

        if (k <= 1)
            throw new IllegalArgumentException("Key must be greater than 1");

        if (k > s.length())
            throw new IllegalArgumentException("Key cannot be greater than text length");

        StringBuilder[] r = new StringBuilder[k];
        for (int i = 0; i < k; i++) r[i] = new StringBuilder();

        int d = 1, row = 0;

        for (char c : s.toCharArray()) {
            r[row].append(c);
            if (row == 0) d = 1;
            else if (row == k - 1) d = -1;
            row += d;
        }

        StringBuilder res = new StringBuilder();
        for (StringBuilder sb : r) res.append(sb);
        return res.toString();
    }

    // Decryption
    static String decrypt(String s, int k) {
        // validations
        if (s == null)
            throw new IllegalArgumentException("Text cannot be null");

        if (s.length() == 0)
            throw new IllegalArgumentException("Text cannot be empty");

        if (k <= 1)
            throw new IllegalArgumentException("Key must be greater than 1");

        if (k > s.length())
            throw new IllegalArgumentException("Key cannot be greater than text length");

        int n = s.length();
        char[][] m = new char[k][n];

        int d = 1, row = 0;
        for (int i = 0; i < n; i++) {
            m[row][i] = '*';
            if (row == 0) d = 1;
            else if (row == k - 1) d = -1;
            row += d;
        }

        int idx = 0;
        for (int i = 0; i < k; i++) {
            for (int j = 0; j < n; j++) {
                if (m[i][j] == '*' && idx < n)
                    m[i][j] = s.charAt(idx++);
            }
        }

        StringBuilder res = new StringBuilder();
        d = 1;
        row = 0;
        for (int i = 0; i < n; i++) {
            res.append(m[row][i]);
            if (row == 0) d = 1;
            else if (row == k - 1) d = -1;
            row += d;
        }

        return res.toString();
    }

    // Driver
    public static void main(String[] args) {

        Scanner sc= new Scanner(System.in);
        System.out.print("Enter the text to encrypt: ");
        String text = sc.nextLine();
        System.out.print("Enter the key: ");
        int key = sc.nextInt();

        String enc = encrypt(text, key);
        System.out.println("Encrypted: " + enc);

        String dec = decrypt(enc, key);
        System.out.println("Decrypted: " + dec);
        sc.close();
    }
}
