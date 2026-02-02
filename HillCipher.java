import java.util.*;

class HillCipher {

    static int MOD = 26;

    // mod function (handles negative)
    static int mod(int x) {
        x %= MOD;
        return x < 0 ? x + MOD : x;
    }

    // GCD
    static int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    // modular inverse of a number
    static int modInv(int a) {
        a = mod(a);
        for (int i = 1; i < MOD; i++)
            if ((a * i) % MOD == 1)
                return i;
        throw new ArithmeticException("Inverse does not exist");
    }

    // determinant (recursive)
    static int det(int[][] m, int n) {
        if (n == 1) return m[0][0];

        int d = 0;
        for (int c = 0; c < n; c++) {
            int[][] sub = new int[n - 1][n - 1];
            for (int i = 1; i < n; i++) {
                int cj = 0;
                for (int j = 0; j < n; j++) {
                    if (j == c) continue;
                    sub[i - 1][cj++] = m[i][j];
                }
            }
            d += Math.pow(-1, c) * m[0][c] * det(sub, n - 1);
        }
        return mod(d);
    }

    // adjoint matrix
    static int[][] adjoint(int[][] m, int n) {
        int[][] adj = new int[n][n];
        if (n == 1) {
            adj[0][0] = 1;
            return adj;
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int[][] sub = new int[n - 1][n - 1];
                int r = 0;
                for (int x = 0; x < n; x++) {
                    if (x == i) continue;
                    int c = 0;
                    for (int y = 0; y < n; y++) {
                        if (y == j) continue;
                        sub[r][c++] = m[x][y];
                    }
                    r++;
                }
                adj[j][i] = mod((int)Math.pow(-1, i + j) * det(sub, n - 1));
            }
        }
        return adj;
    }

    // inverse matrix
    static int[][] inverse(int[][] k, int n) {
        int d = det(k, n);
        if (gcd(d, MOD) != 1)
            throw new ArithmeticException("Key matrix not invertible");

        int invDet = modInv(d);
        int[][] adj = adjoint(k, n);
        int[][] inv = new int[n][n];

        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                inv[i][j] = mod(adj[i][j] * invDet);

        return inv;
    }

    // encrypt or decrypt
    static String process(String text, int[][] k, int n) {
        text = text.toUpperCase().replaceAll("[^A-Z]", "");

        while (text.length() % n != 0)
            text += "X";

        StringBuilder res = new StringBuilder();

        for (int i = 0; i < text.length(); i += n) {
            for (int r = 0; r < n; r++) {
                int sum = 0;
                for (int c = 0; c < n; c++) {
                    sum += k[r][c] * (text.charAt(i + c) - 'A');
                }
                res.append((char)(mod(sum) + 'A'));
            }
        }
        return res.toString();
    }

    // main
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter matrix size n: ");
        int n = sc.nextInt();

        int[][] key = new int[n][n];
        System.out.println("Enter key matrix:");
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                key[i][j] = sc.nextInt();

        sc.nextLine();
        System.out.print("Enter text: ");
        String text = sc.nextLine();

        System.out.println("1. Encrypt\n2. Decrypt");
        int ch = sc.nextInt();

        if (ch == 1) {
            System.out.println("Encrypted: " + process(text, key, n));
        } else {
            int[][] invKey = inverse(key, n);
            System.out.println("Decrypted: " + process(text, invKey, n));
        }
    }
}

