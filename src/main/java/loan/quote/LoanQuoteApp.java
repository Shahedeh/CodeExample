package loan.quote;

import loan.quote.service.LoanQuoteService;

import java.util.Map;

/**
 * @author Shahedeh KHANI
 *
 */
public class LoanQuoteApp {

    private static LoanQuoteService loanQuoteService = new LoanQuoteService();

    /**
     * @param args the input arguments, file path for lenders .csv and requested loan amount
     */
    public static void main(String[] args) {
        loanQuoteService.retrieveLoanQuote(args);
    }

}

