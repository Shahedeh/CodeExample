package loan.quote.service;

import com.opencsv.CSVReader;
import loan.quote.constants.LoanQuoteServiceConstants;
import loan.quote.domain.Lender;
import loan.quote.print.PrintError;
import loan.quote.print.PrintResult;
import loan.quote.validator.InputValidator;
import loan.quote.validator.LenderRecordValidator;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Shahedeh KHANI
 *
 */
public class LoanQuoteService {

    private InputValidator inputValidator = new InputValidator();
    private LenderRecordValidator lenderRecordValidator = new LenderRecordValidator();
    private PrintError printErrors = new PrintError();
    private PrintResult printResult = new PrintResult();

    /**
     * Calls validator to validate input arguments of the application
     * Calls for list of lenders to be created from the .csv file
     * Checks if the loan amount is available, creates related message if not
     * Calls for lenders offering lowest rates to be selected for covering the loan amount
     * Calls for requested loan amount to be adjusted to the nearest increment of 100
     * Calls for quote calculation
     * Passes the result to the main method
     *
     * @param args the input arguments, file path for lenders .csv and requested loan amount
     */
    public void retrieveLoanQuote(final String[] args) {

        List<String> inputValidationErrors = inputValidator.validateAndReturnErrors(args);
        if (inputValidationErrors.size() > 0) {
            printErrors.print(inputValidationErrors);
        } else {
            final SortedSet<Lender> lenders = retrieveLenders(args[0]);

            if (isAvailableFundLessThanRequestedAmount(lenders, args[1])) {
                inputValidationErrors.add(LoanQuoteServiceConstants.NO_SUFFICIENT_AMOUNT);
                printErrors.print(inputValidationErrors);
            } else {
                printResult.print(getQuote(lenders, args[1]));
            }
        }
    }

     /**
     * Uses CSVReader library to parse the lenders .csv file
     * Iterates through records in the .csv file
     * Calls for each record to be validated, making sure it
     * matches fields requirement for creating a lender object
     * Creates a list of lenders sorted in ascending order of the rate they offer
     *
     * @param csvFailPath, file path for lenders .csv
     */
    public SortedSet<Lender> retrieveLenders(final String csvFailPath) {

        final SortedSet<Lender> lenders = new TreeSet<>();
        try {
            final CSVReader reader = new CSVReader(new FileReader(csvFailPath));
            String[] line;
            while ((line = reader.readNext()) != null) {
                if ((line.length == 3 && lenderRecordValidator.isValid(line))) {
                    Lender lender = new Lender(line[0], new BigDecimal(line[1]), Integer.parseInt(line[2]));
                    lenders.add(lender);
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lenders;
    }

    /**
     * Checks whether total available loans are sufficient to cover the requested loan
     *
     * @param lenders list of lenders created from lenders .csv
     * @param requestedLoanAmount user's requested loan amount
     */
    public boolean isAvailableFundLessThanRequestedAmount(final SortedSet<Lender> lenders, final String requestedLoanAmount) {
        return Integer.parseInt(requestedLoanAmount) > lenders.stream().map(Lender::getAvailable).reduce(Integer::sum).get().intValue();
    }

    /**
     * Checks prepares input data for loan calculation then executes it
     *
     * @param lenders list of lenders created from lenders .csv
     * @param requestedLoanAmount user's requested loan amount
     */
    public Map<String, String> getQuote(final SortedSet<Lender> lenders, final String requestedLoanAmount) {
        final SortedSet<Lender> selectedLenders = searchLendersMarket(lenders, Integer.parseInt(requestedLoanAmount));
        final int supportedRequestedLoanAmount = getSupportedRequestLoanAmount(requestedLoanAmount);
        return calculateLoanQuote(selectedLenders, supportedRequestedLoanAmount, requestedLoanAmount);
    }

    /**
     * Calculates annual interest rate
     * Calculates monthly and overall repayment using Amortization
     *
     * @param selectedLenders list of selected lenders for covering the requested loan
     * @param supportedRequestedLoanAmount requested loan amount adjusted to the nearest increment of 100
     */
    public Map<String, String> calculateLoanQuote(final SortedSet<Lender> selectedLenders,
                                                  final int supportedRequestedLoanAmount, final String requestedAmount) {

        final Map<String, String> loanQuoteResult = new HashMap<>();
        final MathContext precision = new MathContext(5);
        final BigDecimal totalAnnualRates = selectedLenders.stream().map(Lender::getAnnualRate).reduce(BigDecimal::add).get();
        final BigDecimal annualInterestRate = totalAnnualRates.divide(BigDecimal.valueOf(selectedLenders.size()), precision);
        final BigDecimal monthlyInterest = annualInterestRate.divide(BigDecimal.valueOf(12), precision);
        final BigDecimal monthlyRepayment = ((BigDecimal.valueOf(supportedRequestedLoanAmount)).multiply(monthlyInterest))
                .divide(BigDecimal.ONE.subtract(BigDecimal.ONE.divide((BigDecimal.ONE.add(monthlyInterest)).pow(36, precision), precision)), precision);
        final BigDecimal totalRepayment = monthlyRepayment.multiply(LoanQuoteServiceConstants.TERM_MONTHS);
        loanQuoteResult.put("originalRequestAmount",requestedAmount );
        loanQuoteResult.put("requestedAmount", String.valueOf(supportedRequestedLoanAmount));
        loanQuoteResult.put("annualInterestRate", annualInterestRate.multiply(BigDecimal.valueOf(100)).setScale(1, RoundingMode.HALF_DOWN).toString());
        loanQuoteResult.put("monthlyRepayment", monthlyRepayment.setScale(2, RoundingMode.HALF_DOWN).toString());
        loanQuoteResult.put("totalRepayment", totalRepayment.setScale(2, RoundingMode.HALF_DOWN).toString());
        return loanQuoteResult;
    }

    /**
     * Iterates through list of lenders and selects them while the
     * total offered loan amount is less than requested loan amount
     *
     * @param lenders list of lenders created from lenders .csv
     * @param requestedAmount user's requested loan amount
     */
    private SortedSet<Lender> searchLendersMarket(final SortedSet<Lender> lenders, final int requestedAmount) {

        int finalMarketOfferSum = 0;
        final SortedSet<Lender> selectedLenders = new TreeSet<>();
        for (Lender lender : lenders) {
            finalMarketOfferSum = finalMarketOfferSum + lender.getAvailable();
            selectedLenders.add(lender);
            if (finalMarketOfferSum >= requestedAmount) {
                break;
            }
        }
        return selectedLenders;
    }

    /**
     * Adjusts the requested loan amount to nearest increment of 100
     *
     * @param requestedLoanAmount loan amount requested by user
     */
    private int getSupportedRequestLoanAmount(final String requestedLoanAmount) {
        final BigInteger requestedAmount = new BigInteger(requestedLoanAmount);
        final List<BigInteger> supportedLoanAmounts = new ArrayList<BigInteger>();

        for(BigInteger supportedAmount = LoanQuoteServiceConstants.MIN_LOAN_AMOUNT;
            supportedAmount.compareTo(LoanQuoteServiceConstants.MAX_LOAN_AMOUNT) != 1;
            supportedAmount = supportedAmount.add(LoanQuoteServiceConstants.INCREMENT_AMOUNT)) {
            supportedLoanAmounts.add(supportedAmount);
        }

        final List<BigInteger> filteredAmounts = supportedLoanAmounts.stream().filter(a -> a.compareTo(requestedAmount) != 1).collect(Collectors.toList());
        return filteredAmounts.get(filteredAmounts.size() - 1).intValue();
    }
}

