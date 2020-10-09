/**
 * @author Gerson Kodjovi A.
 */

public class BitSet {
    /**
     * Crée un ensemble de bits, d'une certaine taille. Ils sont initialisés à
     * {@code false}.
     *
     * @param nbits taille initiale de l'ensemble
     */

    private byte[] bfArray; // le tableau de bytes qui représente le tableau de bits


    public BitSet(int nbits) {
        // TODO À compléter

        // On calcule le nbre de byte necessaire dans le filtre
        int nBytes = (int) Math.ceil(1.0 * nbits/8);
        this.bfArray = new byte[nBytes];

        // On parcours le tableau en mettant chaque byte à 0
        for(int i=0; i<nBytes; i++){
            this.bfArray[i] = 0;
        }
    }


    /**
     * Retourne la valeur du bit à l'index spécifié.
     *
     * @param bitIndex l'index du bit
     * @return la valeur du bit à l'index spécifié
     */
    public boolean get(int bitIndex) {
        // TODO À compléter

        // Retrouver l'index du byte qui contient le bit avec une division entière
        int i = bitIndex / 8;

        // Retrouver la position du bit dans le byte avec modulo
        int j = bitIndex % 8;

        return (this.bfArray[i] >> j & 1) == 1;
    }


    /**
     * Définit le bit à l'index spécifié comme {@code true}.
     *
     * @param bitIndex l'index du bit
     */
    public void set(int bitIndex) {
        // TODO À compléter

        // Retrouver l'index du byte qui contient le bit avec une division entière
        int i = bitIndex / 8;

        // Retrouver la position du bit dans le byte avec modulo
        int j = bitIndex % 8;

        this.bfArray[i] |= (1 << j);
    }


    /**
     * Définit le bit à l'index spécifié comme {@code false}.
     *
     * @param bitIndex l'index du bit
     */
    public void clear(int bitIndex) {
        // TODO À compléter

        // Retrouver l'index du byte qui contient le bit avec une division entière
        //
        int i = bitIndex / 8;

        // Retrouver la position du bit dans le byte avec modulo
        int j = bitIndex % 8;

        this.bfArray[i] &= (0 << j);
    }
}