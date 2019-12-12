package censusanalyser;

import com.bridgelabz.CSVBuilderException;
import com.bridgelabz.CSVBuilderFactory;
import com.bridgelabz.ICSVBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.StreamSupport;

public class CensusLoader {

    public Map<String, CensusDAO> loadCensusData(CensusAnalyser.Country country, String[] csvFilePath) throws CensusAnalyserException {
        if (country.equals(CensusAnalyser.Country.INDIA))
            return loadCensusData(IndiaCensusCSV.class, csvFilePath);
        else {
            return loadCensusData(USCensusData.class, csvFilePath);
        }
    }


    private <E> Map<String, CensusDAO> loadCensusData(Class<E> censusCSVClass, String... csvFilePath) throws CensusAnalyserException {
        Map<String, CensusDAO> censusMap = new HashMap<>();
        Iterator<E> csvFileIterator = null;
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath[0]))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            try {

                csvFileIterator = csvBuilder.getCSVFileIterator(reader, censusCSVClass);

                Iterator<E> finalCsvFileIterator = csvFileIterator;
                Iterable<E> csvIterable = () -> finalCsvFileIterator;
                if (censusCSVClass.getName().equals("censusanalyser.IndiaCensusCSV")) {
                    StreamSupport.stream
                            (csvIterable.spliterator(), false)
                            .map(IndiaCensusCSV.class::cast)
                            .forEach(censusCSV -> censusMap.put(censusCSV.state, new CensusDAO(censusCSV)));
                }


            } catch (RuntimeException e) {
                throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE);
            } catch (CSVBuilderException e) {
                e.printStackTrace();
            }


            if (csvFilePath.length == 1)
                return censusMap;
            loadIndiaStateCode(censusMap, csvFilePath[1]);
            return censusMap;
        } catch (CensusAnalyserException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.WRONG_DELIMETER_OR_HEADER);
        }
    }

    private int loadIndiaStateCode(Map<String, CensusDAO> censusMap, String csvFilePath) throws CensusAnalyserException {
        Iterator<IndiaCensusCSV> stateCodeCSVIterator;
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            try {
                stateCodeCSVIterator = csvBuilder.getCSVFileIterator(reader, IndiaCensusCSV.class);
            } catch (RuntimeException e) {
                throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE);
            }
            Iterable<IndiaCensusCSV> csvIterable = () -> stateCodeCSVIterator;
            StreamSupport.stream(csvIterable.spliterator(), false).filter(csvState -> censusMap.get(csvState.state) != null)
                    .forEach(stateCSV -> censusMap.get(stateCSV.state).stateCode = stateCSV.state);
            return censusMap.size();
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.
                            ExceptionType.
                            NO_CENSUS_DATA);
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.
                    UNABLE_TO_PARSE);
        } catch (CensusAnalyserException e) {

        }
        return 0;
    }
}
