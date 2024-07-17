public class SumTest {
    public static void main(java.lang.String[] args) {
        int m = (java.lang.Integer.MAX_VALUE / 2) + 1;
        int n = (java.lang.Integer.MAX_VALUE / 2) + 1;
        int overflow = m + n;
        java.lang.System.out.println(((long) (m)) + n);
        java.lang.System.out.println(overflow);
    }
}