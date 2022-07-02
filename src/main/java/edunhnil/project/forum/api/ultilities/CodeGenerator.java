package edunhnil.project.forum.api.ultilities;

import org.apache.commons.lang3.RandomStringUtils;

public class CodeGenerator {
    public static String userCodeGenerator() {
        return RandomStringUtils.randomAlphabetic(2).toUpperCase() + RandomStringUtils.randomNumeric(6);
    }

}
