package validators;

public final class StringValidator {
    public static boolean checkNotOutOfBounds(String string, int min, int max){
        boolean res = string.matches("[0-9]+");
        
        if (res) {
            int integer = Integer.parseInt(string);
            res = (integer >= min && integer <= max);
        }
        
        return res;
    }
}
