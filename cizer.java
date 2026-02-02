package cizercipher;

import java.util.*;

//public class code {

//	public static void main(String[] args) {

////		char k=(char)('k'-3);

////		System.out.println(k);

//		

//		Scanner sc=new Scanner(System.in);

//		System.out.println("Enter String");

//		String st=sc.nextLine();

//	

//		StringBuilder sb=new StringBuilder();

//		char k='a';

//		for(int i=0;i<st.length();i++) {

//			k=(char)((st.charAt(i)+3)%26);

//			sb.append(k);

//		}

//		String resultString = sb.toString();

//		System.out.println(resultString);

//		sc.close();

//	}

//} 





import java.util.*;



public class code {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("Enter String (lowercase letters only for this example):");

        String st = sc.nextLine();



        StringBuilder sb = new StringBuilder();

        int shift = 3;



        for (int i = 0; i < st.length(); i++) {

            char originalChar = st.charAt(i);



            

            if (originalChar >= 'a' && originalChar <= 'z') {

                

                int offset = originalChar - 'a';

                int shiftedOffset = (offset + shift) % 26;

                char newChar = (char) ('a' + shiftedOffset);

                

                sb.append(newChar);

            } else {

                sb.append(originalChar);

            }

        }



        String resultString = sb.toString();

        System.out.println(resultString);

        

        

        

       //DECRYPT

     

        StringBuilder sb2 = new StringBuilder();

        for (int i = 0; i < resultString.length(); i++) {

            char originalChar = resultString.charAt(i);



            

            if (originalChar >= 'a' && originalChar <= 'z') {

                

                int offset = originalChar - 'a';

                int shiftedOffset = (offset - shift) % 26;

                char newChar = (char) ('a' + shiftedOffset);

                

                sb2.append(newChar);

            } else {

                sb2.append(originalChar);

            }

        }

        String resultString2 = sb2.toString();

        System.out.println(resultString2);

        

        

        

        

        

        

        

        

        		

        sc.close();

    }

}
