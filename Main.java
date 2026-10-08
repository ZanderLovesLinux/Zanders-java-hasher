import java.math.BigInteger;

public class Main {
    public static void main(String[] args) {
        String toEncrpyt = "Hello, World!";

        int bits = 20;
        long hash = 0;

        // turn hash to unicode
        for (int i = 0; i < toEncrpyt.length(); i++) {
            char ch = toEncrpyt.charAt(i);
            hash = hash + (int) ch;
        }

        // this will be added to the final to make it start dependent
        int oghash = (int) hash;

        // multiplies hash by 2 until its 9 bits so i can cut off the first one
        while (String.valueOf(hash).length() < bits) {
            hash = (hash << 1) + oghash;

        }

        /*
         * modulo divides the hash by an arbitrary large prime number, you can replace
         * that with any large prime number, I chose that one arbitrarily. This makes it
         * more random and irreversible, and if you want, you can replace largeprime
         * with really anything larger than 2^bits + like idk 5.
         */
        Long largeprime = 9223372036854775737L;
        hash = largeprime % hash;
        Long forlhash = hash;

        int ct = 0;
        while (String.valueOf(hash).length() < bits - 1) {
            hash = (hash << 1) + (oghash << ct) + (oghash * ct);
            ct++;
        }

        // check ts hidden BigInteger tech
        BigInteger bigHash = new BigInteger(Long.toString(hash));

        // bigNumber is arbitrary, i keyboardmashed and got the nearest prime number
        BigInteger bigNumber = new BigInteger("181393131");
        BigInteger bigNumberTwo = new BigInteger("75939812349");
        BigInteger finalNeeded = new BigInteger("991152921504606846977");

        /*
         * This is a doozy of an operation, but it effectively makes stuff random. In
         * order, it shifts it left a large amount (dependent on the startint hash) then
         * does modulo with bignumber which is a large prime then multiplies by the
         * orgininal hash and shifts left and xors etc until the number is so completely
         * distant from the original its not reproducible anymore. You coud probably get
         * a collision relatively quickly with like a dehashing program that's good
         * enough but I doubt anyone will be that dedicated to dehashing my code.
         * Finalneeded just ensures that the number is big enough for a 16 digit hash
         */

        BigInteger newHash = bigHash.shiftLeft((int) (largeprime % forlhash)).mod(bigNumber).multiply(bigHash)
                .shiftLeft((int) ((hash % oghash) % oghash)).xor(bigNumberTwo).shiftLeft(oghash)
                .mod(finalNeeded);

        /*
         * i cut off the first character because i get the vibes that it had a pattern,
         * so i built the algo so it would have 17-18 characters so i could do this
         */

        //prints the bigint as a string with 16 chars and formatted in hexidecimal
        System.out.println(newHash.toString(16).substring(1, 17));

    }
}