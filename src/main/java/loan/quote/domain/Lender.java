package loan.quote.domain;

import java.math.BigDecimal;

/**
 * @author Shahedeh KHANI
 *
 */
public class Lender implements Comparable<Lender> {
    private final String name;
    private final int available;
    private final BigDecimal annualRate;

    public Lender(final String name, final BigDecimal annualRate, final int available) {
        this.name = name;
        this.available = available;
        this.annualRate = annualRate;
    }

    public String getName() {
        return this.name;
    }

    public int getAvailable() {
        return this.available;
    }

    public BigDecimal getAnnualRate() {
        return this.annualRate;
    }

    /**
     * Enables to add lenders to the list of lenders in ascending order of their
     * interest rates while duplicates are avoided
     *
     * @param lender, from lenders .csv
     */
    @Override
    public int compareTo(Lender lender) {
        final int compareRates = this.annualRate.compareTo(lender.annualRate);
        final boolean compareDuplicate = this.annualRate.equals(lender.annualRate) && this.name.equals(lender.name) && this.getAvailable() == lender.getAvailable();
        if (compareDuplicate) {
            return 0;
        } else {
            return (compareRates == 0 || compareRates == 1) ? 1 : -1;
        }
    }
}
