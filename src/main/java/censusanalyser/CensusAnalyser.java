package censusanalyser;

import com.bridgelabz.CSVBuilderException;
import com.bridgelabz.CSVBuilderFactory;
import com.bridgelabz.ICSVBuilder;
import com.google.gson.Gson;
import com.opencsv.bean.CsvToBean;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class CensusAnalyser {
    Map<String, IndiaCensusDAO> censusCSVMap = null;
    private Comparator<IndiaCensusDAO> censusCSVComparator = null;
    private Map<FieldName, Comparator> comparatorMap = null;

    public CensusAnalyser() {
        this.censusCSVMap = new HashMap<String, IndiaCensusDAO>();
        comparatorMap = new HashMap<>();
        comparatorMap.put(FieldName.POULATION, censusCSVComparator = Comparator.comparing(indiaCensusCSV -> indiaCensusCSV.population));
        comparatorMap.put(FieldName.AREA, censusCSVComparator = Comparator.comparing(indiaCensusCSV -> indiaCensusCSV.areaInSqKm));
        comparatorMap.put(FieldName.STATE, censusCSVComparator = Comparator.comparing(indiaCensusCSV -> indiaCensusCSV.state));
        comparatorMap.put(FieldName.DENSITY, censusCSVComparator = Comparator.comparing(indiaCensusCSV -> indiaCensusCSV.densityPerSqKm));
    }

    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {

        return loadCensusData(csvFilePath, IndiaCensusCSV.class);
    }

    public int loadIndiaStateCodeData(String indiaCensusCsvFilePath) throws CensusAnalyserException {
        CsvToBean<IndiaStateCSVCode> csvToBean = null;
        try (Reader reader = Files.newBufferedReader(Paths.get(indiaCensusCsvFilePath));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaStateCSVCode> csvFileIterator = csvBuilder.getCSVFileIterator(reader, IndiaStateCSVCode.class);
            Iterable<IndiaStateCSVCode> indiaStateCSVCodeIterable = () -> csvFileIterator;
            StreamSupport.stream(indiaStateCSVCodeIterable.spliterator(), false)
                    .filter(csvState -> censusCSVMap.get(csvState.StateCode) != null)
                    .forEach(csvState -> censusCSVMap.get(csvState.StateName).stateCode = csvState.StateCode);
            return censusCSVMap.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(
                    "", CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException("", CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }

    public String getStateWiseSortedCensusData(FieldName fieldName) throws CensusAnalyserException {
        try {
            List<IndiaCensusDAO> indiaCensusDAOList = censusCSVMap.values().stream().collect(Collectors.toList());
            censusCSVComparator = comparatorMap.get(fieldName);
            this.sort(censusCSVComparator, indiaCensusDAOList);
            String sorted = new Gson().toJson(indiaCensusDAOList);
            return sorted;
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException("", CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }

    private void sort(Comparator<IndiaCensusDAO> censusCSVComparator, List<IndiaCensusDAO> list) throws CSVBuilderException {

        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.size() - i - 1; j++) {
                IndiaCensusDAO censusCSV1 = list.get(j);
                IndiaCensusDAO censusCSV2 = list.get(j + 1);
                if (censusCSVComparator.compare(censusCSV1, censusCSV2) > 0) {
                    list.set(j, censusCSV2);
                    list.set(j + 1, censusCSV1);
                }
            }
        }
    }

    public int loadUSCensusData(String usCensusDataFile) throws CensusAnalyserException {
        return loadCensusData(usCensusDataFile, USCensusData.class);

    }

    public <E> int loadCensusData(String path, Class<E> className) throws CensusAnalyserException {
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


