package dev.eeasee.scenemasker.utils;

public class BooleanUtils {
    public static int countTrues(boolean[] booleans) {
        if (booleans == null) {
            return 0;
        }
        int t = 0;
        for (int i = 0; i < 4096; i++) {
            if (booleans[i]) {
                t++;
            }
        }
        return t;
    }

    public static boolean and(boolean[] booleans) {
        return org.apache.commons.lang3.BooleanUtils.and(booleans);
    }

    public static boolean or(boolean[] booleans) {
        return org.apache.commons.lang3.BooleanUtils.or(booleans);
    }
}
