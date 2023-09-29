package at.fhtw.mtcg.utils;

public class Converter {
    public static String byteArrayToHexString(byte[] byteArray) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : byteArray) {
            // Convert each byte to a hexadecimal string representation
            String hex = Integer.toHexString(b & 0xFF);

            // Ensure that the resulting string has two characters for each byte
            if (hex.length() == 1) {
                hexString.append("0");
            }

            hexString.append(hex);
        }
        String result = hexString.toString().toUpperCase();
        return "0x"+result;
    }
}
