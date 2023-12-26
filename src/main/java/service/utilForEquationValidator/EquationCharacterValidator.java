package service.utilForEquationValidator;

public class EquationCharacterValidator {
    public static boolean isValidCharacters(String equation) {
        char[] allowedCharacters = {'x', '(', ')', '+', '-', '*', '/', '=', '.', ' ', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

        for (char ch : equation.toCharArray()) {
            if (!isValidCharacter(ch, allowedCharacters)) {
                return false;
            }
        }

        return true;
    }

    private static boolean isValidCharacter(char ch, char[] allowedCharacters) {
        for (char allowedChar : allowedCharacters) {
            if (ch == allowedChar) {
                return true;
            }
        }
        return false;
    }
}