package com.epita.tfidf.service.stemming;

/**
 * Stemmer, implementing the Porter Stemming Algorithm.
 * The Stemmer class transforms a word into its root form. The input
 * word can be provided a character at time (by calling add()), or at once
 * by calling one of the various add(something) methods.
 */
class PorterStemmer {

    private char[] b;
    private int i;     /* offset into b */
    private int iEnd;  /* offset to end of stemmed word */
    private int j, k;
    private static final int INC = 50; /* unit of size whereby b is increased */

    public PorterStemmer() {
        b = new char[INC];
        i = 0;
        iEnd = 0;
    }

    /**
     * Add a character to the word being stemmed. When you are finished
     * adding characters, you can call stem(void) to stem the word.
     */
    public void add(char ch) {
        if (i == b.length) {
            char[] newB = new char[i + INC];
            for (int c = 0; c < i; c++)
                newB[c] = b[c];
            b = newB;
        }
        b[i++] = ch;
    }

    /**
     * Adds wLen characters to the word being stemmed contained in a portion
     * of a char[] array. This is like repeated calls of add(char ch), but
     * faster.
     */
    public void add(char[] w, int wLen) {
        if (i + wLen >= b.length) {
            char[] newB = new char[i + wLen + INC];
            for (int c = 0; c < i; c++)
                newB[c] = b[c];
            b = newB;
        }
        for (int c = 0; c < wLen; c++)
            b[i++] = w[c];
    }

    /**
     * Adds a word in the stemmer if no character has been added before.
     */
    public void add(String w) {
        if (this.iEnd == 0)
            add(w.toCharArray(), w.length());
    }

    /**
     * Clears the word in the stemmer and resets the cursors.
     */
    public void clear() {
        this.b = new char[INC];
        this.i = 0;
        this.iEnd = 0;
    }

    /**
     * After a word has been stemmed, it can be retrieved by toString(),
     * or a reference to the internal buffer can be retrieved by getResultBuffer
     * and getResultLength (which is generally more efficient.)
     */
    public String toString() {
        return new String(b, 0, iEnd);
    }

    /**
     * Returns the length of the word resulting from the stemming process.
     */
    public int getResultLength() {
        return iEnd;
    }

    /**
     * Returns a reference to a character buffer containing the results of
     * the stemming process.  You also need to consult getResultLength()
     * to determine the length of the result.
     */
    public char[] getResultBuffer() {
        return b;
    }

    /**
     *  Returns true if the letter at index b[i] is a consonant.
     */
    private boolean isCons(int i) {
        switch (b[i]) {
            case 'a':
            case 'e':
            case 'i':
            case 'o':
            case 'u':
                return false;
            case 'y':
                return i == 0 || !isCons(i - 1);
            default:
                return true;
        }
    }

   /**
    * Measures the number of consonant sequences between 0 and j. If c is
    * a consonant sequence and v a vowel sequence, and <..> indicates arbitrary
    * presence.
    * ex:
    *    <c><v>       gives 0
    *    <c>vc<v>     gives 1
    *    <c>vcvc<v>   gives 2
    *    <c>vcvcvc<v> gives 3
    *    ....
    */
    private int consSequences() {
        int n = 0;
        int m = 0;
        while (true) {
            if (m > j) return n;
            if (!isCons(m)) break;
            m++;
        }
        m++;
        while (true) {
            while (true) {
                if (m > j) return n;
                if (isCons(m)) break;
                m++;
            }
            m++;
            n++;
            while (true) {
                if (m > j) return n;
                if (!isCons(m)) break;
                m++;
            }
            m++;
        }
    }

    /**
     * Returns true if the letters in the interval 0,...j contains a vowel.
     */
    private boolean hasVowelInStem() {
        for (int n = 0; n <= j; n++)
            if (!isCons(n))
                return true;
        return false;
    }

    /**
     * Returns true if the letters at index j,(j-1) are double consonants.
     */
    private boolean hasDoubleCons(int j) {
        if (j < 1)
            return false;
        if (b[j] != b[j - 1])
            return false;
        return isCons(j);
    }

    /**
     * Returns true if the letters at index i-2,i-1,i has the form
     * consonant - vowel - consonant and also if the second c is not w,x or y.
     * This is used when trying to restore an e at the end of a short word.
     * ex:
     *   cav(e), lov(e), hop(e), crim(e), but
     *   snow, box, tray.
    */
    private boolean cvc(int i) {
        if (i < 2 || !isCons(i) || isCons(i - 1) || !isCons(i - 2))
            return false;
        int ch = b[i];
        return ch != 'w' && ch != 'x' && ch != 'y';
    }

    /**
     * Returns true if the word ends by the suffix given in parameter.
     */
    private boolean endsBy(String s) {
        int l = s.length();
        int o = k - l + 1;
        if (o < 0)
            return false;
        for (int n = 0; n < l; n++)
            if (b[o + n] != s.charAt(n))
                return false;
        j = k - l;
        return true;
    }

    /**
     * Sets (j+1),...k to the characters in the string s, readjusting
     * k.
     */
    private void setTo(String s) {
        int l = s.length();
        int o = j + 1;
        for (int n = 0; n < l; n++)
            b[o + n] = s.charAt(n);
        k = j + l;
    }

    /**
     * Append given word termination.
     */
    private void appendSuffix(String s) {
        if (consSequences() > 0) setTo(s);
    }

    /**
     * Gets rid of plurals and -ed or -ing.
     * e.g.:
     *      caresses  ->  caress
     *      ponies    ->  poni
     *      ties      ->  ti
     *      caress    ->  caress
     *      cats      ->  cat
     *
     *      feed      ->  feed
     *      agreed    ->  agree
     *      disabled  ->  disable
     *
     *      matting   ->  mat
     *      mating    ->  mate
     *      meeting   ->  meet
     *      milling   ->  mill
     *      messing   ->  mess
     *
     *      meetings  ->  meet
     */
    private void step1() {
        if (b[k] == 's') {
            if (endsBy("sses"))
                k -= 2;
            else if (endsBy("ies"))
                setTo("i");
            else if (b[k - 1] != 's')
                k--;
        }
        if (endsBy("eed")) {
            if (consSequences() > 0)
                k--;
        }
        else if ((endsBy("ed") || endsBy("ing")) && hasVowelInStem()) {
            k = j;
            if (endsBy("at"))
                setTo("ate");
            else if (endsBy("bl"))
                setTo("ble");
            else if (endsBy("iz"))
                setTo("ize");
            else if (hasDoubleCons(k)) {
                int ch = b[k-1];
                if (!(ch == 'l' || ch == 's' || ch == 'z'))
                    k--;
            }
            else if (consSequences() == 1 && cvc(k))
                setTo("e");
        }
    }

    /**
     * Turns terminal y to i when there is another vowel in the stem.
     */
    private void step2() {
        if (endsBy("y") && hasVowelInStem()) b[k] = 'i';
    }

    /**
     * Maps double suffices to single ones. so -ization ( = -ize plus
     * -ation) maps to -ize etc. note that the string before the suffix must give
     * m() > 0.
     */
    private void step3() {
        if (k == 0) return; /* For Bug 1 */
        switch (b[k - 1]) {
            case 'a':
                if (endsBy("ational")) {
                    appendSuffix("ate");
                    break;
                }
                if (endsBy("tional")) {
                    appendSuffix("tion");
                    break;
                }
                break;
            case 'c':
                if (endsBy("enci")) {
                    appendSuffix("ence");
                    break;
                }
                if (endsBy("anci")) {
                    appendSuffix("ance");
                    break;
                }
                break;
            case 'e':
                if (endsBy("izer")) {
                    appendSuffix("ize");
                    break;
                }
                break;
            case 'l':
                if (endsBy("bli")) {
                    appendSuffix("ble");
                    break;
                }
                if (endsBy("alli")) {
                    appendSuffix("al");
                    break;
                }
                if (endsBy("entli")) {
                    appendSuffix("ent");
                    break;
                }
                if (endsBy("eli")) {
                    appendSuffix("e");
                    break;
                }
                if (endsBy("ousli")) {
                    appendSuffix("ous");
                    break;
                }
                break;
            case 'o':
                if (endsBy("ization")) {
                    appendSuffix("ize");
                    break;
                }
                if (endsBy("ation")) {
                    appendSuffix("ate");
                    break;
                }
                if (endsBy("ator")) {
                    appendSuffix("ate");
                    break;
                }
                break;
            case 's':
                if (endsBy("alism")) {
                    appendSuffix("al");
                    break;
                }
                if (endsBy("iveness")) {
                    appendSuffix("ive");
                    break;
                }
                if (endsBy("fulness")) {
                    appendSuffix("ful");
                    break;
                }
                if (endsBy("ousness")) {
                    appendSuffix("ous");
                    break;
                }
                break;
            case 't':
                if (endsBy("aliti")) {
                    appendSuffix("al");
                    break;
                }
                if (endsBy("iviti")) {
                    appendSuffix("ive");
                    break;
                }
                if (endsBy("biliti")) {
                    appendSuffix("ble");
                    break;
                }
                break;
            case 'g':
                if (endsBy("logi")) {
                    appendSuffix("log");
                    break;
                }
        }
    }

    /**
     * Deals with -ic-, -full, -ness etc. Similar strategy to step3.
     */
    private void step4() {
        switch (b[k]) {
            case 'e':
                if (endsBy("icate")) {
                    appendSuffix("ic");
                    break;
                }
                if (endsBy("ative")) {
                    appendSuffix("");
                    break;
                }
                if (endsBy("alize")) {
                    appendSuffix("al");
                    break;
                }
                break;
            case 'i':
                if (endsBy("iciti")) {
                    appendSuffix("ic");
                    break;
                }
                break;
            case 'l':
                if (endsBy("ical")) {
                    appendSuffix("ic");
                    break;
                }
                if (endsBy("ful")) {
                    appendSuffix("");
                    break;
                }
                break;
            case 's':
                if (endsBy("ness")) {
                    appendSuffix("");
                    break;
                }
                break;
        }
    }

    /**
     * Takes off -ant, -ence etc., in context <c>vcvc<v>.
     */
    private void step5() {
        if (k == 0) return; /* for Bug 1 */
        switch (b[k - 1]) {
            case 'a':
                if (endsBy("al")) break;
                return;
            case 'c':
                if (endsBy("ance")) break;
                if (endsBy("ence")) break;
                return;
            case 'e':
                if (endsBy("er")) break;
                return;
            case 'i':
                if (endsBy("ic")) break;
                return;
            case 'l':
                if (endsBy("able")) break;
                if (endsBy("ible")) break;
                return;
            case 'n':
                if (endsBy("ant")) break;
                if (endsBy("ement")) break;
                if (endsBy("ment")) break;
                /* element etc. not stripped before the m */
                if (endsBy("ent")) break;
                return;
            case 'o':
                if (endsBy("ion") && j >= 0 && (b[j] == 's' || b[j] == 't')) break;
                /* j >= 0 fixes Bug 2 */
                if (endsBy("ou")) break;
                return;
            /* takes care of -ous */
            case 's':
                if (endsBy("ism")) break;
                return;
            case 't':
                if (endsBy("ate")) break;
                if (endsBy("iti")) break;
                return;
            case 'u':
                if (endsBy("ous")) break;
                return;
            case 'v':
                if (endsBy("ive")) break;
                return;
            case 'z':
                if (endsBy("ize")) break;
                return;
            default:
                return;
        }
        if (consSequences() > 1) k = j;
    }

    /**
     * Removes a final -e if m() > 1.
     */
    private void step6() {
        j = k;
        if (b[k] == 'e') {
            int a = consSequences();
            if (a > 1 || a == 1 && !cvc(k - 1)) k--;
        }
        if (b[k] == 'l' && hasDoubleCons(k) && consSequences() > 1) k--;
    }

    /**
     * Stem the word placed into the Stemmer buffer through calls to add().
     * You can retrieve the result with getResultLength()/getResultBuffer()
     * or toString().
     */
    public void stem() {
        k = i - 1;
        if (k > 1) {
            step1();
            step2();
            step3();
            step4();
            step5();
            step6();
        }
        iEnd = k + 1;
        i = 0;
    }
}
