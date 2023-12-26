package service;

import service.utilForEquationValidator.EquationCharacterValidator;
import service.utilForEquationValidator.EquationOperatorValidator;
import service.utilForEquationValidator.EquationParenthesisValidator;

public class EquationValidatorService {
    public static boolean validateEquation(String equation) {
        return EquationCharacterValidator.isValidCharacters(equation) &&
                EquationParenthesisValidator.isBalancedParenthesis(equation) &&
                EquationOperatorValidator.isValidOperatorPlacement(equation);
    }
}