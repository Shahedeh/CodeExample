package loan.quote.print;

import java.util.Map;

public class PrintResult extends AbstractPrint {

    @Override
    /**
     * when no validation issue, the program either returns 'it is not possible to provide a quote' message
     * or the loan request result. This method prints the result accordingly.
     *
     * @param loanRequestResult, the result of running application
     */
    public void print(final Map<String, String> loanRequestResult) {
        System.out.println("*********************************************************************");
        System.out.println("*********************************************************************");
        System.out.println("*********************************************************************");
        System.out.println("********************Application Execution Result*********************");
        System.out.println("Original Requested Amount: " + loanRequestResult.get("originalRequestAmount"));
        System.out.println("Requested amount (closest increament of 100): " + loanRequestResult.get("requestedAmount"));
        System.out.println("Annual Interest Rate: " + loanRequestResult.get("annualInterestRate"));
        System.out.println("Monthly repayment: " + loanRequestResult.get("monthlyRepayment"));
        System.out.println("Total repayment: " + loanRequestResult.get("totalRepayment"));
        System.out.println("*********************************************************************");
        System.out.println("*********************************************************************");
        System.out.println("*********************************************************************");
    }
}
