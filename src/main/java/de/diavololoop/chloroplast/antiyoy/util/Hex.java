package de.diavololoop.chloroplast.antiyoy.util;

/**
 * Created by Chloroplast on 16.11.2017.
 */
public class Hex {

    private final static char[] ALPHABET = "0123456789abcdef".toCharArray();

    public static String toHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder(bytes.length * 2);


        for(int i = 0; i < bytes.length; ++i){
            builder.append(ALPHABET[(bytes[i] >> 4) & 0xF]);
            builder.append(ALPHABET[bytes[i] & 0xF]);
        }

        return builder.toString();
    }

}
