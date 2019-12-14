package censusanalyser;

public class CensusAnalyserException extends Exception {
    ExceptionType type;

    public CensusAnalyserException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }
    public enum ExceptionType {
        CENSUS_FILE_PROBLEM, UNABLE_TO_PARSE, NO_CENSUS_DATA,WRONG_DELIMETER_OR_HEADER,CSV_BIND_ERROR
    }
}
