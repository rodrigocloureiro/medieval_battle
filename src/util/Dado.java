package util;

public abstract class Dado {
    private static int rolar(int faces) {
        return (int) Math.floor(Math.random() * faces) + 1;
    }

    public static int rolarD2() {
        return rolar(2);
    }

    public static int rolarD4() {
        return rolar(4);
    }

    public static int rolarD6() {
        return rolar(6);
    }

    public static int rolarD8() {
        return rolar(8);
    }

    public static int rolarD10() {
        return rolar(10);
    }
}
