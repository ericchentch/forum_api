package edunhnil.project.forum.api.ultilities;

import edunhnil.project.forum.api.exception.InvalidRequestException;
import edunhnil.project.forum.api.exception.ResourceNotFoundException;

public class InputValidation {

    public static void validMobile(String input) {
        if (input.isEmpty()) {
            throw new ResourceNotFoundException("Not found!");
        }
        if (input.matches("(((\\+|)84)|0)(3|5|7|8|9)+([0-9]{8})")) {
            return;
        } else {
            throw new InvalidRequestException("Invalid mobile format!");
        }
    }

    public static void validEmail(String input) {
        if (input.isEmpty())
            throw new ResourceNotFoundException("Not found!");
        if (input.matches("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")) {
            return;
        } else {
            throw new InvalidRequestException("Invalid email format!");
        }
    }

    public static void validDateFormat(String input) {
        if (input.isEmpty())
            throw new ResourceNotFoundException("Not found!");
        if (input.matches("^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$")) {
            return;
        } else {
            throw new InvalidRequestException("Invalid date format!");
        }
    }

    public static void validGender(int gender) {
        if (gender == 1 || gender == 0) {
            return;
        } else {
            throw new InvalidRequestException("Invalid gender format!");
        }
    }

}
