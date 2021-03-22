package loan.quote.validator;

import loan.quote.constants.LoanQuoteServiceConstants;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Shahedeh KHANI
 *
 */
public class InputValidatorTest {

    private final ClassLoader classLoader = getClass().getClassLoader();
    private final String filepath =classLoader.getResource("market_with_correct_data.csv").getPath();
    private InputValidator inputValidator;

    @Before
    public void setUp() {
        inputValidator = new InputValidator();
    }

    @Test
    public void validateNumberOfInputArgumentsFailWhenOnlyOneArgumentIsProvided() {
        final String[] args = {filepath};
        List<String> validationResult = inputValidator.validateAndReturnErrors(args);
        assert(validationResult.size() == 1);
        assert (validationResult.get(0).equals(LoanQuoteServiceConstants.INVALID_INPUT_ARGS));
    }

    @Test
    public void validateNumberOfInputArgumentsFailWhenMoreThanTwoArgrumentAreProvided() {
        final String[] args = {filepath, "1000", "extra_argument"};
        List<String> validationResult = inputValidator.validateAndReturnErrors(args);
        assert(validationResult.size() == 1);
        assert (validationResult.get(0).equals(LoanQuoteServiceConstants.INVALID_INPUT_ARGS));
    }

    @Test
    public void validateNumberOfInputArgumentsPassWhenExactlyTwoCorrectArgrumentIsProvided() {
        final String[] args = {filepath, "1000"};
        List<String> validationResult = inputValidator.validateAndReturnErrors(args);
        assert(validationResult.size() == 0);
    }

    @Test
    public void validateRequestedAmountFailWhenIsNotNumber() {
        final String[] args = {filepath, "ab1*&^"};
        List<String> validationResult = inputValidator.validateAndReturnErrors(args);
        assert(validationResult.size() == 1);
        assert (validationResult.get(0).equals(LoanQuoteServiceConstants.INVALID_INPUT_AMOUNT_TYPE));
    }

    @Test
    public void validateRequestedAmountFailWhenItIsNegativeNumber() {
        final String[] args = {filepath, "-1000"};
        List<String> validationResult = inputValidator.validateAndReturnErrors(args);
        assert(validationResult.size() == 1);
        assert (validationResult.get(0).equals(LoanQuoteServiceConstants.INVALID_INPUT_AMOUNT_TYPE));
    }

    @Test
    public void validateRequestedAmountFailWhenItIsFloatNumber() {
        final String[] args = {filepath, "1000.1"};
        List<String> validationResult = inputValidator.validateAndReturnErrors(args);
        assert(validationResult.size() == 1);
        assert (validationResult.get(0).equals(LoanQuoteServiceConstants.INVALID_INPUT_AMOUNT_TYPE));
    }

    @Test
    public void validateRequestedAmountFailWhenLowerThanMinLimit() {
        final String[] args = {filepath, "100"};
        List<String> validationResult = inputValidator.validateAndReturnErrors(args);
        assert(validationResult.size() == 1);
        assert (validationResult.get(0).equals(LoanQuoteServiceConstants.INVALID_INPUT_AMOUNT));
    }

    @Test
    public void validateRequestedAmountFailWhenHigherThanMaxLimit() {
        final String[] args = {filepath, "100"};
        List<String> validationResult = inputValidator.validateAndReturnErrors(args);
        assert(validationResult.size() == 1);
        assert (validationResult.get(0).equals(LoanQuoteServiceConstants.INVALID_INPUT_AMOUNT));
    }
}