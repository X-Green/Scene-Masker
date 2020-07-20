package dev.eeasee.scenemasker.utils;

public class Byte2Boolean {
    private static boolean[] convertToBooleanArray(byte[] bytes) {
        boolean [] result = new boolean[bytes.length << 3];

        for (int i=0; i<bytes.length; i++) {
            int index = i<<3;
            result[index|0] = (bytes[i] & 0x80) != 0;
            result[index|1] = (bytes[i] & 0x40) != 0;
            result[index|2] = (bytes[i] & 0x20) != 0;
            result[index|3] = (bytes[i] & 0x10) != 0;
            result[index|4] = (bytes[i] & 0x8) != 0;
            result[index|5] = (bytes[i] & 0x4) != 0;
            result[index|6] = (bytes[i] & 0x2) != 0;
            result[index|7] = (bytes[i] & 0x1) != 0;
        }

        return result;
    }

    private static byte[] convertToByteArray(boolean[] booleans) {
        byte[] result = new byte[booleans.length >> 3];

        for (int i=0; i<result.length; i++) {
            int index = i << 3;
            byte b = (byte)(
                    (booleans[index] ? 1<<7 : 0) |
                            (booleans[index|1] ? 1<<6 : 0) |
                            (booleans[index|2] ? 1<<5 : 0) |
                            (booleans[index|3] ? 1<<4 : 0) |
                            (booleans[index|4] ? 1<<3 : 0) |
                            (booleans[index|5] ? 1<<2 : 0) |
                            (booleans[index|6] ? 1<<1 : 0) |
                            (booleans[index|7] ? 1 : 0));
            result[i] = b;
        }

        return result;
    }

}
