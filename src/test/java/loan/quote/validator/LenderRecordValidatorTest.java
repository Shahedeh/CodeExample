package loan.quote.validator;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LenderRecordValidatorTest {

    private LenderRecordValidator lenderRecordValidator;

    @Before
    public void setUp() {
        lenderRecordValidator = new LenderRecordValidator();
    }

    @Test
    public void validateLenderRecordReturnFalseWhenAvailableIsNotNumber() {
        final String[] lenderRecord = {"name", "0.075", "asf2$%"};
        assertFalse(lenderRecordValidator.isValid(lenderRecord));
    }

    @Test
    public void validateLenderRecordReturnFalseWhenAvailableIsZero() {
        final String[] lenderRecord = {"name", "0.075", "0"};
        assertFalse(lenderRecordValidator.isValid(lenderRecord));
    }

    @Test
    public void validateLenderRecordReturnFalseWhenAvailableIsNegativeNumber() {
        final String[] lenderRecord = {"name", "0.075", "-500"};
        assertFalse(lenderRecordValidator.isValid(lenderRecord));
    }

    @Test
    public void validateLenderRecordReturnFalseWhenAvailableIsFloatNumber() {
        final String[] lenderRecord = {"name", "0.075", "500.1"};
        assertFalse(lenderRecordValidator.isValid(lenderRecord));
    }

    @Test
    public void validateLenderRecordReturnFalseWhenRateIsNotNumber() {
        final String[] lenderRecord = {"name", "k%2", "500"};
        assertFalse(lenderRecordValidator.isValid(lenderRecord));
    }

    @Test
    public void validateLenderRecordReturnFalseWhenRateIsNegativeNumber() {
        final String[] lenderRecord = {"name", "-0.075", "500"};
        assertFalse(lenderRecordValidator.isValid(lenderRecord));
    }

    @Test
    public void validateLenderRecordReturnTrueWhenRateIsWholeNumber() {
        final String[] lenderRecord = {"name", "1", "500"};
        assert(lenderRecordValidator.isValid(lenderRecord));
    }

}