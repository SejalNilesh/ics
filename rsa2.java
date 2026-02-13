import java.util.*;

public class rsa2 {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Random rand = new Random();

        // -------- Phase 1 : Key Generation --------

        // 1. Generate two large primes p and q such that p ≠ q
        int p = generatePrime(rand);
        int q;
        do {
            q = generatePrime(rand);
        } while (q == p);

        // 2. Calculate
        int n = p * q;
        int m = (p - 1) * (q - 1);

        // 3. Select e such that gcd(e, m) = 1

        //  e = 1 → encryption does nothing

        //  e = m → invalid mathematically

        //  gcd(e, m) ≠ 1 → no modular inverse → RSA breaks
        int e;
        do {
            e = rand.nextInt(m - 2) + 2; // 2 to m-1
        } while (!gcd(e, m));

        // 4. Calculate d = e^-1 mod m
        int d = modInverse(e, m);

        // 5. Public key
        System.out.println("Public Key (e,n): (" + e + ", " + n + ")");

        // 6. Private key
        System.out.println("Private Key (d,n): (" + d + ", " + n + ")");

        // -------- Phase 2 : Encryption  --------
        System.out.print("\nEnter plainkey (number): ");
        int P = sc.nextInt();

        int C = fastExp(P, e, n);
        System.out.println("Cipher key: " + C);

        // -------- Phase 3 : Decryption  --------
        int decrypted = fastExp(C, d, n);
        System.out.println("Decrypted Plain Key: " + decrypted);
        sc.close();
    }

    // -------- Prime Generator --------
    static int generatePrime(Random rand) {
        while (true) {
            int num = rand.nextInt(100) + 50; // small primes for demo
            if (isPrime(num)) return num;
        }
    }

    static boolean isPrime(int n) {
        if (n < 2) return false;
        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0) return false;
        }
        return true;
    }

    // -------- Helper Functions  --------

    public static boolean gcd(int a, int b) {
        a = Math.abs(a);
        b = Math.abs(b);

        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a == 1;
    }

    public static int mod(int n, int mod) {
        int r = n % mod;
        if (n < 0) return r + mod;
        return r;
    }

    public static List<Integer> toBits(int n) {
        List<Integer> bits = new ArrayList<>();
        while (n > 0) {
            bits.add(n % 2);
            n = n / 2;
        }
        Collections.reverse(bits);
        return bits;
    }

    // overflow-safe fast exponentiation
    public static int fastExp(int n, int exp, int mode) {
        if (mode == 1) return 0;

        List<Integer> bits = toBits(exp);
        long c = 1;
        long base = mod(n, mode);

        for (int bit : bits) {
            c = (c * c) % mode;
            if (bit == 1) {
                c = (c * base) % mode;
            }
        }
        return (int) c;
    }

    static int modInverse(int e, int phi) {
        int t = 0, newT = 1;
        int r = phi, newR = e;

        while (newR != 0) {
            int quotient = r / newR;

            int temp = t;
            t = newT;
            newT = temp - quotient * newT;

            temp = r;
            r = newR;
            newR = temp - quotient * newR;
        }

        if (r > 1) {
            throw new ArithmeticException("e is not invertible");
        }

        if (t < 0) t += phi;

        return t;
    }
}
