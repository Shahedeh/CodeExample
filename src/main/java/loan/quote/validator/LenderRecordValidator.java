package loan.quote.validator;

public class LenderRecordValidator extends AbstractValidator {

    /**
     * checks rate value to be positive and a float.
     * checks available value to be a whole number
     * these checks are required before creating a lender object using these values
     *
     * @param lenderRecord from lenders .csv file
     */
    @Override
    public boolean isValid(final String[] lenderRecord) {
        //test the float part
        return (!lenderRecord[1].equals("0") && !lenderRecord[1].equals("0.0") && lenderRecord[1].matches("^(?=.+)(?:[1-9]\\d*|0)?(?:\\.\\d+)?$") && !lenderRecord[2].equals("0") && lenderRecord[2].matches("^[0-9]+$")) ? true : false;
    }
}
