
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Q7{

    public static void main(String[] args) throws FileNotFoundException {
        SeparateChainingHashST separateChainingHashST = (SeparateChainingHashST) ST.fillST(new SeparateChainingHashST());
        LinearProbingHashST linearProbingHashST = (LinearProbingHashST) ST.fillST(new LinearProbingHashST());

        // Test is to find the key with the highest frequency count.
        long start = System.currentTimeMillis();
        SeperateChainingTest(separateChainingHashST);
        long time = System.currentTimeMillis() - start;
        System.out.println("SeperateChaining time: "+time+"ms");
        start = System.currentTimeMillis();
        LinearProbingTest(linearProbingHashST);
        time = System.currentTimeMillis() - start;
        System.out.println("LinearProbing time: "+time+"ms");
    }

    private static void SeperateChainingTest(SeparateChainingHashST separateChainingHashST){
        System.out.println("MAX: "+separateChainingHashST.getMaxKey()+" "+separateChainingHashST.get(separateChainingHashST.getMaxKey()));
    }

    private static void LinearProbingTest(LinearProbingHashST linearProbingHashST){
        System.out.println("MAX: "+linearProbingHashST.getMaxKey()+" "+ linearProbingHashST.get(linearProbingHashST.getMaxKey()));
    }

}

class SeparateChainingHashST extends  ST implements Iterable{
    private int N; // number of key-value pairs
    private int M; // hash table size

    private SequentialSearchST[] st; // array of ST objects
    public SeparateChainingHashST() {
        this(997);
    }

    public SeparateChainingHashST(int M) {
        this.M = M; // Create M linked lists.
        st = new SequentialSearchST[M];
        for (int i = 0; i < M; i++)
            st[i] = new SequentialSearchST();
    }

    int hash(String key) {
        return (key.hashCode() & 0x7fffffff) % M;
    }

    @Override
    public int get(String key) {
        return st[hash(key)].get(key);
    }

    @Override
    public void put(String key, int val) {
        st[hash(key)].put(key, val);
        N++;
    }

    @Override
    public boolean contains(String word) {
        return get(word)!=-1;
    }

    /**
     * Iterates from head to tail
     *
     * @return iterator that goes from head to tail
     */
    @Override
    public Iterator iterator() {
        return new ListIterator() {
        };
    }

    private class ListIterator implements Iterator {
        private String word = "";

        public boolean hasNext() {
            return word != null;
        }

        public String next() {
            if (!hasNext())
                throw new NoSuchElementException();
            return null;
        }
    }

    public String getMaxKey(){
        String maxKey = null;
        int maxValue = 0;
        int val;

        for(SequentialSearchST st : st)
            for (Object key : st)
                if ((val=st.get((String) key)) > maxValue){
                    maxKey = (String) key;
                    maxValue = val;
                }

        return maxKey;
    }
}

class LinearProbingHashST extends ST {
    private int N; // number of key-value pairs in the table
    private int M = 16; // size of linear-probing table
    private String[] keys; // the keys
    private int[] vals; // the values
    public LinearProbingHashST() {
        keys = new String[M];
        vals = new int[M];
    }

    public LinearProbingHashST(int cap) {
        keys = new String[cap];
        vals = new int[cap];
        M = cap;
    }

    public int getM() {
        return this.M;
    }

    private int hash(String key) {
        return (key.hashCode() & 0x7fffffff) % M;
    }

    private void resize(int cap) {
        LinearProbingHashST t;
        t = new LinearProbingHashST(cap);
        for (int i = 0; i < M; i++)
            if (keys[i] != null)
                t.put(keys[i], vals[i]);
        keys = t.keys;
        vals = t.vals;
        M = t.M;
    }

    @Override
    public void put(String key, int val) {
        if (N >= M/2) resize(2*M); // double M (see text)
        int i;
        for (i = hash(key); keys[i] != null; i = (i + 1) % M)
            if (keys[i].equals(key)) { vals[i] = val; return; }
        keys[i] = key;
        vals[i] = val;
        N++;
    }

    @Override
    public int get(String key) {
        for (int i = hash(key); keys[i] != null; i = (i + 1) % M)
            if (keys[i].equals(key))
                return vals[i];
        return -1;
    }

    @Override
    public boolean contains(String word) {
        return get(word)!=-1;
    }

    public String getMaxKey() {
        if (N==0) throw new NoSuchElementException();
        int maxIndex = 0;

        for(int i=0;i<M;i++)
            if (vals[i] > vals[maxIndex])
                maxIndex = i;

        return keys[maxIndex];
    }
}