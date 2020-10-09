/**
 * @author Gerson Kodjovi A.
 */

public class BloomFilter {
    /**
     * Crée un filtre de Bloom basé sur la taille de l'ensemble de bits et du
     * nombre de fonctions de hachage.
     *
     * @param numBits taille de l'ensemble de bits
     * @param numHashes nombre de fonctions de hachage
     */

    private int numBits;
    private int numHashes;
    private int numElems;
    private double falsePosProb;
    private HashFunction hashFunc;
    private BitSet bitSet;

    public BloomFilter(int numBits, int numHashes) {
        // TODO À compléter
        this.numBits = numBits;
        this.numHashes = numHashes;

        this.bitSet = new BitSet(this.numBits);
        this.hashFunc = new HashFunction(this.numHashes, this.numBits);
    }


    /**
     * Crée un filtre de Bloom basé sur le nombre d'éléments attendus et de la
     * probabilité de faux positifs désirée.
     *
     * @param numElems nombre d'éléments à insérer
     * @param falsePosProb probabilité de faux positifs
     */
    public BloomFilter(int numElems, double falsePosProb) {
        // TODO À compléter
        this.numElems = numElems;
        this.falsePosProb = falsePosProb;

        this.numHashes = (int)Math.ceil(-Math.log(this.falsePosProb) / Math.log(2));
        this.numBits = (int)Math.ceil(-this.numElems * Math.log(this.falsePosProb) / (Math.pow(Math.log(2), 2)));
        this.bitSet = new BitSet(this.numBits);

        this.hashFunc = new HashFunction(this.numHashes, this.numBits);
    }


    /**
     * Ajoute un élément au filtre de Bloom.
     *
     * @param key l'élément à insérer
     */
    public void add(byte[] key) {
        // TODO À compléter

        // On hash d'abord la key pour obtenir les indexes
        int[] hashResuts = hashFunc.hash(key);

        // On set les bits du filtre par la suite
        for(int i=0; i<this.numHashes; i++)
            this.bitSet.set(hashResuts[i]);
    }


    /**
     * Cherche pour l'élément dans le filtre de Bloom.
     *
     * @param key l'élément à trouver
     * @return si l'élément est possiblement dans le filtre
     */
    public boolean contains(byte[] key) {
        // TODO À compléter
        // On hash d'abord la key pour obtenir les indexes
        int[] hashResuts = hashFunc.hash(key);

        // false dès qu'on trouve un bit à 0
        for (int i=0; i<hashResuts.length; i++) {
            if (!this.bitSet.get(hashResuts[i]))
                return false;
        }
        return true;
    }


    /**
     * Remet à zéro le filtre de Bloom.
     */
    public void reset() {
        // TODO À compléter
        // Faire un clear pour chacun des bits settés
        int m = this.size();
        for(int i=0; i<m; i++){
            if(this.bitSet.get(i))
                this.bitSet.clear(i);
        }
    }


    /**
     * Retourne le nombre de bits du filtre de Bloom.
     *
     * @return nombre de bits
     */
    public int size() {
        // TODO À compléter
        return this.numBits;
    }


    /**
     * Retourne le nombre d'éléments insérés dans le filtre de Bloom.
     *
     * @return nombre d'éléments insérés
     */
    public int count() {
        // TODO À compléter
        // Compter le nombre de bits settés à 1
        int m = this.numBits;
        int nbSet = 0;
        for(int i=0; i<m; i++){
            if(this.bitSet.get(i))
                nbSet++;
        }

        // Estimer le nombre d'items insérés: formule trouvée dans la référence suivante
        // Reference: https://en.wikipedia.org/wiki/Bloom_filter
        return (int)Math.round(-m * (Math.log(1 - 1.0*nbSet/this.size()))/(this.numHashes));
    }


    /**
     * Retourne la probabilité actuelle de faux positifs.
     *
     * @return probabilité de faux positifs
     */
    public double fpp() {
        // TODO À compléter
        int m = this.numBits;
        int k = this.numHashes;

        // On compter le nombre d'éléments ajoutés dans le filtre
        int n = this.count();


        if (m==0) { // Pour gérer une éventuelle division par 0
            return 0;
        } else {

            // fpp = (1-exp(-kn/m)^k)
            double result = Math.pow((1 - Math.exp(-1.0*(k * n)/m)), k);

            // Précision de 6 décimals
            return (double)Math.round(result * 1000000d) / 1000000d;
        }
    }


    @Override
    public String toString() {
        return "n = "+this.numElems+"; "+"m = "+this.numBits+"; "+"fpp_desiré = "+this.falsePosProb+"; "+"k = "+this.numHashes;
    }
}