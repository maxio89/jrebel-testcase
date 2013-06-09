package pl.itcrowd.base;

@SuppressWarnings("UnusedDeclaration")
public interface Constants {

    // ------------------------------ FIELDS ------------------------------
    // ------------------------------ FIELDS ------------------------------
    int DEFAULT_MAX_RESULTS = 20;
    int INTERNAL_SERVER_ERROR = 500;
    int NOT_FOUND = 404;
    String OUTCOME_SUCCESS = "success";
    String OUTCOME_FAILURE = "failure";
    String OUTCOME_STAY = "stay";
    String BASE64_PREFIX = "data:image/gif;base64,";
    //    BigDecimal compareTo() return value:
    int BIGDECIMAL_LESS_THAN = -1;
    int BIGDECIMAL_EQUAL = 0;
    int BIGDECIMAL_GREATER_THAN = 1;
    String EMAIL_REGEXP = ".+\\@.+\\..+";
}
