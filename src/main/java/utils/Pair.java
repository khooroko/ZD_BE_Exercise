package main.java.utils;

public class Pair<U, V>
{
    private U p1;
    private V p2;

    public Pair(U a, V b)
    {
        this.p1 = a;
        this.p2 = b;
    }
    public Pair<U, V> getValue()
    {
        return this;
    }
    public U getFirst() {
        return p1;
    }
    public V getSecond() {
        return p2;
    }
}
