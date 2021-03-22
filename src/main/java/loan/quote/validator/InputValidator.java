package loan.quote.validator;

import loan.quote.constants.LoanQuoteServiceConstants;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Shahedeh KHANI
 *
 */
public class InputValidator extends AbstractValidator {

    /**
     * checks exactly two input arguments are provided.
     * checks the second input argument to be a whole number between 1000 and 15000
     *
     * @param args the input arguments, file path for lenders .csv and requested loan amount
     */
    @Override
    public List<String> validateAndReturnErrors(final String[] args) {

        final List<String> errors = new ArrayList<>();
        validateInputSizeIsExactlyTwo(args, errors);
        if (args.length > 1) {
            validateLoanAmountIsWholeNumber(args[1], errors);
            if(errors.size() == 0)
            validateLoanAmountIsWithinAcceptedRange(args[1], errors);
        }
        return errors;
    }

    private static void validateLoanAmountIsWithinAcceptedRange(final String loanAmount, final List<String> errors) {

        if ((new BigInteger(loanAmount)).compareTo(LoanQuoteServiceConstants.MIN_LOAN_AMOUNT) == -1
                || (new BigInteger(loanAmount)).compareTo(LoanQuoteServiceConstants.MAX_LOAN_AMOUNT) == 1)
            errors.add(LoanQuoteServiceConstants.INVALID_INPUT_AMOUNT);
    }

    private static void validateLoanAmountIsWholeNumber(final String loanAmount, final List<String> errors) {

        if (!loanAmount.matches("^[0-9]+$"))
            errors.add(LoanQuoteServiceConstants.INVALID_INPUT_AMOUNT_TYPE);
    }

    private static void validateInputSizeIsExactlyTwo(final String[] inputArgs, final List<String> errors) {

        if (inputArgs.length != 2)
            errors.add(LoanQuoteServiceConstants.INVALID_INPUT_ARGS);
    }
}

