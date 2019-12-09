package censusanalyser;

public class CSVBuilderFactory {
    public static <E> ICSVBuilder createCSVBuilder() {
       return new OpenCSVBuilder<E>();
    }

}
