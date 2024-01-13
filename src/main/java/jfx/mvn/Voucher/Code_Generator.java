package jfx.mvn.Voucher;

import java.util.Random;

public class Code_Generator {

    public int Disc() {
        Random random = new Random();
        int[] Random_Disc = { 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 };
        int index = random.nextInt(Random_Disc.length);
        int Disc = Random_Disc[index];

        return Disc;
    }

    public String Code_Generator() {

        Random random = new Random();
        String code = "";
        String[] Raw = { "a", "b", "c", "d", "e", "f", "g", "h",
                "i", "j", "k", "l", "m", "n", "o", "p", "q",
                "r", "s", "t", "u", "v", "w", "x", "y",
                "z", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0" };
        for (int i = 0; i < 6; i++) {
            int index = random.nextInt(Raw.length);
            code += Raw[index];
        }
        return code;
    }

    public static void main(String[] args) {
        Code_Generator generator = new Code_Generator();
        String randomcode = generator.Code_Generator();
        int Diskon = generator.Disc();
        System.out.println("Kode Acak: " + randomcode);
        System.out.println("Gacha Diskon: " + Diskon + "%");
    }

}
