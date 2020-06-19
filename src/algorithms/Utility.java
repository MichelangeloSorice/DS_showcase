package algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Utility {

    /**
     * Rabin-Kap - PatternMatching O(S) P = patternLen S = Slen && S >= P
     * We compute the hash of every window of length P and compare it with the hash of P
     */
    public List<Integer> matchingIdx(String s, String p){
        List<Integer> res = new ArrayList();
        int sl = s.length(), pl = p.length();
        long mod = (long)(1e9+7); // Big prime number
        long wh = 0, ph = 0; // Sliding window and pattern hash;
        long alphSz = 26; // Alphabet size 26 for lowercase english letters
        long h = (long)(Math.pow(alphSz, pl-1));

        for(int i = 0; i<pl; i++){
            ph = (ph*alphSz + (long)(p.charAt(i)-'a'))%mod;
            wh = (wh*alphSz + (long)(s.charAt(i)-'a'))%mod;
        }
        if(ph == wh){
            // Should first check actual matching of chars and then
            res.add(0);
        }

        for(int i = pl; i<sl; i++){
            // First remove hash component associated with first char of previous window
            wh = (wh - (long)(s.charAt(i-pl)-'a')*h);
            // Be aware wh can be negative now
            if(wh < 0) wh+=mod;

            // The comupte hash including new character
            wh = (wh * alphSz + (long)(s.charAt(i)-'a'))%mod;



            if(wh == ph){
                // Should first check actual matching of chars and then
                res.add(i-pl+1);
            }
        }

        return res;
    }

// ================================================================================================================

    /**
     * RESERVOIR SAMPLING - Randomly select k elements from a set of M items or from a continuous stram of items
     * Proof
     * - For elements initially in reservoir prob of being in reservoir at the end is
     * ProbOfNonSelFor(k+1) * ProbOfNonSelFor(k+2) *  ... * ProbOfNonSelFor(N)
     * k/k+1 * k+1/k+2 * ... * n-1/n = k/n
     *
     * - For elements at position i > k in items
     * ProbOfBeingSel(i) * ProbOfNotBeingSelFor(i+1) * ..
     * k/i * i/i+1 * ... * n-1/n = K/n
     *
     */

    public int[] sample(int[] items, int k){
        // error condition
        if(k > items.length) return null;

        Random rand = new Random();
        int[] out = new int[k];

        // Filling up reservoir all elements have prob 1 to be selected now
        for(int i = 0; i<k; i++) out[i] = items[i];

        for(int i = k; i<items.length; i++){
            // Pick in range [0, i] inclusive
            int pick = rand.nextInt(i+1);

            if(pick < k){
                out[pick] = items[i];
            }
        }

        return out;

    }



// ================================================================================================================

    /**
     * Moore's Voting Algorithm - Find Majority Element (for array of length N appears at least N/2 times)
     * The algorithm always identifies a candidate majority element even if no majority element is present
     * thus requires a verification step
     */

    public int majorityElement(int[] nums) {
        int candidate = nums[0], count = 1;

        for(int i = 1; i<nums.length; i++){
            if(candidate == nums[i]) {
                count++;
            }else if(count == 0){
                candidate = nums[i]; // Set up new candidate
                count = 1;
            }else{
                count--;
            }
        }

        // check if candidate is actually a majority element
        count = 0;
        for(int x : nums) if(x==candidate) count++;

        return count >= nums.length/2 ? candidate : null;
    }

// ================================================================================================================

    /**
     * GCD - Series of numbers
     * NB gcd(a,b,c) = gcd(gcd(a,b),c) = gcd(gcd(c,b),a) = gcd(gcd(a,c),b)
     */
    public int generalGcd(int[] values){
        int res = values[0];
        for(int i = 1; i<values.length; i++) {
            res = gcd(res, values[i]);
        }
        return res;
    }

    /**
     * Euclidean Algorithm for GCD - O(log(min(a,b)))
     */
    public int gcd(int a, int b){
        /*if(a == 0)
            return b;
        return gcd(b%a, a);*/ //recursive version
        while(a != 0){
            int tmp = b%a;
            b = a;
            a = tmp;
        }
        return a;
    }

}
