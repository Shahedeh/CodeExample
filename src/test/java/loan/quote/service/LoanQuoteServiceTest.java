package loan.quote.service;

import loan.quote.domain.Lender;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import static org.junit.Assert.*;

/**
 * @author Shahedeh KHANI
 *
 */
public class LoanQuoteServiceTest {

    private final ClassLoader classLoader = getClass().getClassLoader();
    private LoanQuoteService loanQuoteService;

    @Before
    public void setUp() {
        loanQuoteService = new LoanQuoteService();
    }

    @Test
    public void validateListOfLendersPassWhenNoDuplicates() {
        /*
         * duplicate in the application means two identical lenders
         * having same name, same rate and same available
         *
         */
        final String filepath =classLoader.getResource("market_with_incorrect_available_amount_v1.csv").getPath();
        try {
            final SortedSet<Lender> lenders = loanQuoteService.retrieveLenders(filepath);
            assert(lenders.size() == 12);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void validateListOfLendersPassWhenAvailableIsWholeNumberOnly() {
        final String filepath =classLoader.getResource("market_with_incorrect_available_amount_v2.csv").getPath();
        try {
            final SortedSet<Lender> lenders = loanQuoteService.retrieveLenders(filepath);;
            assert(lenders.size() == 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void validateListOfLendersPassWhenRatesAreFloatORWholeNumberOnly() {
        final String filepath =classLoader.getResource("market_with_incorrect_available_amount_v3.csv").getPath();
        try {
            final SortedSet<Lender> lenders = loanQuoteService.retrieveLenders(filepath);
            assert(lenders.size() == 2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void validateLoanQuoteResultPassWhenLoanAmountNotAvailable() {
        final SortedSet<Lender> lenders = new TreeSet<>();
        Lender lender1 = new Lender("John", new BigDecimal("0.005"), 320);
        Lender lender2 = new Lender("Jane", new BigDecimal("0.069"), 480);
        Lender lender3 = new Lender("Jane", new BigDecimal("0.069"), 600);
        lenders.add(lender1);
        lenders.add(lender2);
        lenders.add(lender3);
        assertTrue(loanQuoteService.isAvailableFundLessThanRequestedAmount(lenders, "11000"));
    }

    @Test
    public void validateLoanQuoteResultPassWhenRequestedAmountIsAdjustedToNearestIncrementOfHundred() {
        final String filepath =classLoader.getResource("market_with_correct_data.csv").getPath();
        final SortedSet<Lender> lenders = loanQuoteService.retrieveLenders(filepath);
        final Map<String, String> loanQuoteResult = loanQuoteService.getQuote(lenders, "1111");
        assert(loanQuoteResult.size() == 5);
        assert(("1100").equals(loanQuoteResult.get("requestedAmount")));
    }

    @Test
    public void validateLoanQuoteResultPassWhenRequestedAmountNotAdjusted() {
        final String filepath =classLoader.getResource("market_with_correct_data.csv").getPath();
        final SortedSet<Lender> lenders = loanQuoteService.retrieveLenders(filepath);
        final Map<String, String> loanQuoteResult = loanQuoteService.getQuote(lenders, "1100");
        assert(loanQuoteResult.size() == 5);
        assert(("1100").equals(loanQuoteResult.get("requestedAmount")));
    }

    @Test
    public void validateLoanQuoteResultPassWhenCorrectQuoteIsReturned() {
        final String filepath =classLoader.getResource("market_with_correct_data_v2.csv").getPath();
        final SortedSet<Lender> lenders = loanQuoteService.retrieveLenders(filepath);
        final Map<String, String> loanQuoteResult = loanQuoteService.getQuote(lenders, "1000");
        assert(loanQuoteResult.size() == 5);
        assert(("1000").equals(loanQuoteResult.get("requestedAmount")));
        assert(("38.1").equals(loanQuoteResult.get("annualInterestRate")));
        assert(("47.03").equals(loanQuoteResult.get("monthlyRepayment")));
        assert(("1692.97").equals(loanQuoteResult.get("totalRepayment")));
    }

}