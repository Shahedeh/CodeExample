package loan.quote.print;

import java.util.List;

public class PrintError extends AbstractPrint {

    @Override
    /**
     * prints input validation errors if any
     *
     * @param inputValidationErrors
     */
    public void print (final List<String> inputValidationErrors) {
        System.out.println("*********************************************************************");
        System.out.println("*********************************************************************");
        System.out.println("*********************************************************************");
        System.out.println("*********************Cannot Provide Loan Quote***********************");
        inputValidationErrors.stream().forEach(System.out::println);
        System.out.println("*********************************************************************");
        System.out.println("*********************************************************************");
        System.out.println("*********************************************************************");
    }
}
