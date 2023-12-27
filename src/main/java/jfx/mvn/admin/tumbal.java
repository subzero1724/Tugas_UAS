package jfx.mvn.admin;

import java.util.concurrent.atomic.AtomicInteger;

public class tumbal {
    private static AtomicInteger lastId = new AtomicInteger(0);

    public static String generateProductId() {
        int productId = lastId.incrementAndGet();
        return String.format("PROD-%03d", productId);
    }

    public static void main(String[] args) {
        // Contoh penggunaan
        String newProductId = generateProductId();
        System.out.println(newProductId); // Output: PROD-010
    }

}
