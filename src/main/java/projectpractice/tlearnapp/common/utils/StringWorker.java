package projectpractice.tlearnapp.common.utils;

public class StringWorker {

    public static String toCamelCase(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(str.substring(0, 1).toLowerCase());
        sb.append(str.substring(1));
        return sb.toString();
    }
}
