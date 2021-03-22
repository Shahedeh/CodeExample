package loan.quote.validator;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractValidator {

    public List<String> validateAndReturnErrors(final String[] args) {

        return new ArrayList<>();
    }

    public boolean isValid(final String[] args) {
        return false;
    }
}
