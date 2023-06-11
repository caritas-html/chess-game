package application;

import java.util.Arrays;

public class UnicodeUtils {

    private static final String FULL_BLOCK = " \u2B1C";
    private static final String FULL_BLOCK_WHITE = " \u2B1B";

    public static String[] UnicodeUtils (String unicode, String unicode2) {
        final String[] UNICODE_ARRAY = {unicode, unicode2};
        return UNICODE_ARRAY;
    }
    public static String[] sortBoardUnicodes () {
        String[] unicodeArray = UnicodeUtils(FULL_BLOCK, FULL_BLOCK_WHITE);
        Arrays.sort(unicodeArray);

        return unicodeArray;
    }
}
