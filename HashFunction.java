/**
 * @author Gerson Kodjovi A.
 */

import java.util.Random;

public class HashFunction{

    /** Generation de numHashes fonction de hashage
     * Hashage universelle. Ref: wikipedia
     * Gestion de collision: double hachage
     */

    private int c1, c2, p, numHashes, tabSize;

    // On va gener aleatoirement les coefficients c1 et c2 pour la fonction
    public HashFunction(int numHashes, int tabSize){

        //Reference: https://en.wikipedia.org/wiki/List_of_prime_numbers
        p=1073676287;       //un grand nombre 1er pouvant renter dans "int"
        int seed = 100;
        Random rand = new Random(seed);

        this.numHashes = numHashes;
        this.tabSize = tabSize;

        this.c1 = Math.abs(rand.nextInt()) % (this.p-1) + 1;
        this.c2 = Math.abs(rand.nextInt()) % this.p;

    }


    /**
     * Crée plusieurs hashages pour key
     * Combinaison de : hashage polynomial +
     * double hashage pour la gestion de collision +
     * Hachage universel
     *
     * @param key à hasher
     * @return un tableau contenant les indexes de key
     */
    public int[] hash(byte[] key) {

        int hashVal = 0;
        int[] result = new int[this.numHashes];
        int c = 127; // la cste de la fct polynomiale (nb 1er)

        for(int i=0; i<this.numHashes; i++) {

            // Je fais d'abord un hashage polynomial de la key
            for (int j = 0; j < key.length; j++)
                hashVal += key[j] * (int)(Math.pow(c, j));

            // Je fais un double hashage pour la gestion de collision
            hashVal += 1 + (hashVal % (this.tabSize -1));

            // Ensuite j'applique le hachage universel pour la securisation du hashage
            result[i] = (Math.abs(this.c1 * hashVal + this.c2)) % this.tabSize;
        }

        return result;
    }
}