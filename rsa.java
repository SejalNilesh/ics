import java.util.*;
public class rsa{
    public static void main(String[] args) {
        System.out.println("hiii");
        List<Integer> k= toBits(4);
        System.out.println(k);
        System.out.println(4);
    }

    public static boolean gcd(int a, int b) {
    // Take absolute values to handle negative inputs correctly
        a = Math.abs(a);
        b = Math.abs(b);
    
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        if(a==1){
         return true;
        }
        return false;
    }

    public static int mod(int n,int mod){
        int r=n%mod;
        if(n<0){
            return r+mod;
        }
        return r;
    }

    public static List<Integer> toBits(int n){
        List<Integer> bits= new ArrayList<>();
        while(n>0){
            bits.add(n%2);
            n=n/2;
        }
        bits =bits.reversed();
        return bits;
    }

    public static int fastexp(int n,int exp,int mode){
        List<Integer> bits=toBits(exp);
        int c=1;
        for(int bit :bits){
            int a=(c*c);
            if(bit==1){
                a=a*n;
            }
            c=mod(a,mode);
        }
        return c;
    }
    //solves the problem of the overflow
    public static int fastExp(int n, int exp, int mode) {
        if (mode == 1) return 0;

        List<Integer> bits = toBits(exp); // MUST be MSB → LSB
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

        // If gcd(e, phi) != 1, inverse does not exist
        if (r > 1) {
            throw new ArithmeticException("e is not invertible");
        }

    // Make positive
        if (t < 0) {
            t += phi;
        }

        return t;
    }


        
}


 
