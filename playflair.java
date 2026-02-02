package playfair;







import java.util.*;



public class play {



    private char[][] table = new char[5][5];

    private Map<Character, int[]> position = new HashMap<>();



    // Constructor: builds the key table

    public play(String key) {

        buildTable(key);

    }



    private void buildTable(String key) {

        boolean[] used = new boolean[26];

        key = key.toUpperCase().replaceAll("[^A-Z]", "").replace("J", "I");



        StringBuilder sb = new StringBuilder();



        // Add key characters

        for (char c : key.toCharArray()) {

            if (!used[c - 'A']) {

                sb.append(c);

                used[c - 'A'] = true;

            }

        }



        // Add remaining letters

        for (char c = 'A'; c <= 'Z'; c++) {

            if (c == 'J') continue;

            if (!used[c - 'A']) {

                sb.append(c);

                used[c - 'A'] = true;

            }

        }



        // Fill 5x5 table

        int index = 0;

        for (int i = 0; i < 5; i++) {

            for (int j = 0; j < 5; j++) {

                table[i][j] = sb.charAt(index);

                position.put(table[i][j], new int[]{i, j});

                index++;

            }

        }

    }



    private String prepareText(String text) {

        text = text.toUpperCase().replaceAll("[^A-Z]", "").replace("J", "I");

        StringBuilder sb = new StringBuilder();



        for (int i = 0; i < text.length(); i++) {

            char a = text.charAt(i);

            char b = (i + 1 < text.length()) ? text.charAt(i + 1) : 'X';



            if (a == b) {

                sb.append(a).append('X');

            } else {

                sb.append(a).append(b);

                i++;

            }

        }



        if (sb.length() % 2 != 0) {

            sb.append('X');

        }



        return sb.toString();

    }



    public String encrypt(String text) {

        text = prepareText(text);

        StringBuilder result = new StringBuilder();



        for (int i = 0; i < text.length(); i += 2) {

            char a = text.charAt(i);

            char b = text.charAt(i + 1);



            int[] posA = position.get(a);

            int[] posB = position.get(b);



            if (posA[0] == posB[0]) { // Same row

                result.append(table[posA[0]][(posA[1] + 1) % 5]);

                result.append(table[posB[0]][(posB[1] + 1) % 5]);

            } else if (posA[1] == posB[1]) { // Same column

                result.append(table[(posA[0] + 1) % 5][posA[1]]);

                result.append(table[(posB[0] + 1) % 5][posB[1]]);

            } else { // Rectangle

                result.append(table[posA[0]][posB[1]]);

                result.append(table[posB[0]][posA[1]]);

            }

        }

        return result.toString();

    }



    public String decrypt(String text) {

        StringBuilder result = new StringBuilder();



        for (int i = 0; i < text.length(); i += 2) {

            char a = text.charAt(i);

            char b = text.charAt(i + 1);



            int[] posA = position.get(a);

            int[] posB = position.get(b);



            if (posA[0] == posB[0]) { // Same row

                result.append(table[posA[0]][(posA[1] + 4) % 5]);

                result.append(table[posB[0]][(posB[1] + 4) % 5]);

            } else if (posA[1] == posB[1]) { // Same column

                result.append(table[(posA[0] + 4) % 5][posA[1]]);

                result.append(table[(posB[0] + 4) % 5][posB[1]]);

            } else { // Rectangle

                result.append(table[posA[0]][posB[1]]);

                result.append(table[posB[0]][posA[1]]);

            }

        }

        return result.toString();

    }



    // Test the cipher

    public static void main(String[] args) {

        play cipher = new play("sejal");



        String plaintext = "INSTRUMENTS";

        String encrypted = cipher.encrypt(plaintext);

        String decrypted = cipher.decrypt(encrypted);



        System.out.println("Plaintext: " + plaintext);

        System.out.println("Encrypted: " + encrypted);

        System.out.println("Decrypted: " + decrypted);

    }

}
