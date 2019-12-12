package CensusLoader;

import censusanalyser.CensusAnalyserException;
import censusanalyser.IndiaCensusCSV;
import censusanalyser.IndiaCensusDAO;
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

public class CensusLoader {


    public <E> int loadCensusData(String path, Class<E> className) throws CensusAnalyserException {
        Map<String, IndiaCensusDAO> censusCSVMap = new HashMap<>();
        try (Reader reader = Files.newBufferedReader(Paths.get(path));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<E> csvFileIterator = csvBuilder.getCSVFileIterator(reader, className);
            Iterable<E> usCSVCodeIterable = () -> csvFileIterator;
            if (className.getName().equals("censusanalyser.USCensusData")) {
                StreamSupport.stream(usCSVCodeIterable.spliterator(), false)
                        .map(USCensusData.class::cast)
                        .forEach(censusCSV -> censusCSVMap.put(censusCSV.state, new IndiaCensusDAO(censusCSV)));
                return censusCSVMap.size();
            } else if (className.getName().equals("censusanalyser.IndiaCensusCSV")) {
                StreamSupport.stream(usCSVCodeIterable.spliterator(), false)
                        .map(IndiaCensusCSV.class::cast)
                        .forEach(censusCSV -> censusCSVMap.put(censusCSV.state, new IndiaCensusDAO(censusCSV)));
                return censusCSVMap.size();
            }
        } catch (IOException e) {
            throw new CensusAnalyserException(
                    "", CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException("", CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
        return 0;
    }
}
