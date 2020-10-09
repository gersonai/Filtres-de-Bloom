/**
 * @author Gerson Kodjovi A.
 */

import java.util.Random;

public class TestsBloomFilter {

    // Générer les nbStr strings de tailles entre StrMinSize et StrMaxSize
    static String[] generateStrings(int nbStr, int StrMinSize, int StrMaxSize){

        int lowerLimit = 33; // lower limit for chars
        int upperLimit = 126; // lower limit for chars

        Random random = new Random();
        String[] result = new String[nbStr];

        for (int i = 0; i < nbStr; i++) {

            // Générer un nombre entre min to max (including both)
            int strSize = random.nextInt(StrMaxSize - StrMinSize + 1) + StrMinSize;

            // Créer un StringBuffer pour storer le string de strSize
            StringBuffer r = new StringBuffer(strSize);


            for (int j = 0; j < strSize; j++) {
                // Générer une valeur random entre 33 and 126
                int caract = random.nextInt(upperLimit - lowerLimit + 1) + lowerLimit;
                r.append((char) caract); // ajouter le caractère généré à la fin du StringBuffer
            }
            result[i] = r.toString();
        }
        return result;
    }


    static void testerBloomFilter(BloomFilter bfATester, String[] LesKeys){

        System.out.println("   \nParams: " + bfATester.toString());

        System.out.println("   \nAjout de " + LesKeys.length + " keys au filtre");
        for (int i = 0; i<LesKeys.length; i++) {
            //System.out.println("   " + LesKeys[i]);
            bfATester.add(LesKeys[i].getBytes());
        }
        System.out.println("   \nDone!");

        System.out.println("   \nVerification presence de la key " + LesKeys[0] + " : " + bfATester.contains(LesKeys[0].getBytes()));

        String key1Modif = LesKeys[0];
        key1Modif = key1Modif.substring(0,1).toUpperCase() + key1Modif.substring(1).toLowerCase();

        System.out.println("   \nVerification presence de la key " + key1Modif + " : " + bfATester.contains(key1Modif.getBytes()));
        System.out.println("   \n.size() : " + bfATester.size());
        System.out.println("   \n.count() : " + bfATester.count());
        System.out.println("   \n.fpp() : " + bfATester.fpp());

        System.out.println("   \nTest de .reset()");
        bfATester.reset();
        System.out.println("   \nDone!");

        System.out.println("   \nVerification presence de la key " + LesKeys[0] + " : " + bfATester.contains(LesKeys[0].getBytes()));
        System.out.println("   \n.size() : " + bfATester.size());
        System.out.println("   \n.count() : " + bfATester.count());
        System.out.println("   \n.fpp() : " + bfATester.fpp());
    }


    public static void main (String[] args) {

        String[] LesKeys = generateStrings(100, 3, 10);

        System.out.println("   \n===== Test constr 1 - BloomFilter(int numBits, int numHashes) =====");
        BloomFilter bloomFilt1 = new BloomFilter(480,4);
        testerBloomFilter(bloomFilt1, LesKeys);


        System.out.println("   \n===== Test constr 2 - BloomFilter(int numElems, int falsePosProb) =====");
        BloomFilter bloomFilt2 = new BloomFilter(100, 0.1);
        testerBloomFilter(bloomFilt2, LesKeys);
    }
}