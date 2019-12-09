package censusanalyser;

public class CSVBuilderException extends Exception {
    ExceptionType type;

    enum ExceptionType {
        CENSUS_FILE_PROBLEM, UNABLE_TO_PARSE
    }

    public CSVBuilderException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }
    public CSVBuilderException(String message, String name) {
        super(message);
        this.type =ExceptionType.valueOf(name);
    }

}
