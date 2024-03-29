package censusanalyser.adapter;

import censusanalyser.CensusAnalyserException;
import censusanalyser.CensusDAO;
import censusanalyser.IndiaCensusCSV;
import censusanalyser.USCensusData;
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

public abstract class CensusAdapter {
    public abstract Map<String, CensusDAO> loadingCensusData(String... csvFilePath) throws CensusAnalyserException;

    protected <E> Map<String, CensusDAO> loadCensusData(Class<E> censusCSVClass, String csvFilePath) throws CensusAnalyserException {
        Map<String, CensusDAO> censusMap = new HashMap<>();
        Iterator<E> csvFileIterator = null;
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
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
                } else if (censusCSVClass.getName().equals("censusanalyser.USCensusData")) {
                    StreamSupport.stream
                            (csvIterable.spliterator(), false)
                            .map(USCensusData.class::cast)
                            .forEach(censusCSV -> censusMap.put(censusCSV.state, new CensusDAO(censusCSV)));
                }
            } catch (CSVBuilderException e) {
                throw new CensusAnalyserException("Error in CSV file Binding", CensusAnalyserException.ExceptionType.CSV_BIND_ERROR);
            }
        } catch (RuntimeException e) {
            throw new CensusAnalyserException("Error in format delimter or header wrong", CensusAnalyserException.ExceptionType.WRONG_DELIMETER_OR_HEADER);
        } catch (IOException e) {
            throw new CensusAnalyserException("Error while reading file", CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
        return censusMap;
    }
}
