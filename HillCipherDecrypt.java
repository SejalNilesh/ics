class HillCipherDecrypt {

    static int mod = 26;

    // mod inverse of a number
    static int modInv(int a) {
        a = a % mod;
        for (int x = 1; x < mod; x++) {
            if ((a * x) % mod == 1)
                return x;
        }
        throw new ArithmeticException("Mod inverse does not exist");
    }

    // calculate determinant of 3x3 matrix
    static int determinant(int[][] matrix) {
        int det = 0;
        det = matrix[0][0] * (matrix[1][1] * matrix[2][2] - matrix[1][2] * matrix[2][1])
            - matrix[0][1] * (matrix[1][0] * matrix[2][2] - matrix[1][2] * matrix[2][0])
            + matrix[0][2] * (matrix[1][0] * matrix[2][1] - matrix[1][1] * matrix[2][0]);
        return det % mod;
    }

    // calculate adjoint matrix of 3x3
    static int[][] adjoint(int[][] matrix) {
        int[][] adj = new int[3][3];

        adj[0][0] = (matrix[1][1] * matrix[2][2] - matrix[1][2] * matrix[2][1]);
        adj[0][1] = -(matrix[0][1] * matrix[2][2] - matrix[0][2] * matrix[2][1]);
        adj[0][2] = (matrix[0][1] * matrix[1][2] - matrix[0][2] * matrix[1][1]);

        adj[1][0] = -(matrix[1][0] * matrix[2][2] - matrix[1][2] * matrix[2][0]);
        adj[1][1] = (matrix[0][0] * matrix[2][2] - matrix[0][2] * matrix[2][0]);
        adj[1][2] = -(matrix[0][0] * matrix[1][2] - matrix[0][2] * matrix[1][0]);

        adj[2][0] = (matrix[1][0] * matrix[2][1] - matrix[1][1] * matrix[2][0]);
        adj[2][1] = -(matrix[0][0] * matrix[2][1] - matrix[0][1] * matrix[2][0]);
        adj[2][2] = (matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0]);

        return adj;
    }

    // decrypt function
    static String decrypt(String ct, int[][] k) {

        // validations
        if (ct == null || ct.length() == 0)
            throw new IllegalArgumentException("Cipher text empty");

        if (ct.length() % 3 != 0)
            throw new IllegalArgumentException("Cipher text length must be divisible by 3");

        // determinant
        int det = determinant(k);
        if (det < 0) det = (det % mod + mod) % mod;

        int invDet = modInv(det);

        // adjoint matrix
        int[][] adj = adjoint(k);

        // inverse key matrix
        int[][] invK = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                invK[i][j] = (adj[i][j] * invDet) % mod;
                if (invK[i][j] < 0) invK[i][j] += mod;
            }
        }

        StringBuilder pt = new StringBuilder();

        // decrypt blocks of 3 characters
        for (int i = 0; i < ct.length(); i += 3) {
            int c1 = ct.charAt(i) - 'A';
            int c2 = ct.charAt(i + 1) - 'A';
            int c3 = ct.charAt(i + 2) - 'A';

            int p1 = (invK[0][0] * c1 + invK[0][1] * c2 + invK[0][2] * c3) % mod;
            int p2 = (invK[1][0] * c1 + invK[1][1] * c2 + invK[1][2] * c3) % mod;
            int p3 = (invK[2][0] * c1 + invK[2][1] * c2 + invK[2][2] * c3) % mod;

            if (p1 < 0) p1 += mod;
            if (p2 < 0) p2 += mod;
            if (p3 < 0) p3 += mod;

            pt.append((char) (p1 + 'A'));
            pt.append((char) (p2 + 'A'));
            pt.append((char) (p3 + 'A'));
        }

        return pt.toString();
    }

    // driver
    public static void main(String[] args) {

        int[][] key = {
            {3, 3, 2},
            {2, 5, 3},
            {3, 4, 2}
        };

        String cipher = "TCHQRS";

        System.out.println("Decrypted Text: " + decrypt(cipher, key));
    }
}
