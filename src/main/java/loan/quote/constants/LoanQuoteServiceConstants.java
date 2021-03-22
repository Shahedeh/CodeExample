package loan.quote.constants;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author Shahedeh KHANI
 *
 */
public class LoanQuoteServiceConstants {

    public static BigInteger INCREMENT_AMOUNT = BigInteger.valueOf(100);
    public static BigInteger MIN_LOAN_AMOUNT = BigInteger.valueOf(1000);
    public static BigInteger MAX_LOAN_AMOUNT = BigInteger.valueOf(15000);
    public static BigDecimal TERM_MONTHS = BigDecimal.valueOf(36);
    public static String NO_SUFFICIENT_AMOUNT = "It is not possible to provide a quot";
    public static String INVALID_INPUT_ARGS = "Path to lenders .csv file and loan amount are required as input arguments only";
    public static String INVALID_INPUT_AMOUNT = "Application supports loan values of 1000-15000 only";
    public static String INVALID_INPUT_AMOUNT_TYPE = "Loan amount should be whole number";
}
