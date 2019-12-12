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
    Comparator<IndiaCensusDAO> censusCSVComparator = null;
    Map<FieldName, Comparator> comparatorMap = null;

    public CensusAnalyser() {
        this.censusCSVMap = new HashMap<String, IndiaCensusDAO>();
        comparatorMap = new HashMap<>();
        comparatorMap.put(FieldName.POULATION, censusCSVComparator = Comparator.comparing(indiaCensusCSV -> indiaCensusCSV.population));
        comparatorMap.put(FieldName.AREA, censusCSVComparator = Comparator.comparing(indiaCensusCSV -> indiaCensusCSV.areaInSqKm));
        comparatorMap.put(FieldName.STATE, censusCSVComparator = Comparator.comparing(indiaCensusCSV -> indiaCensusCSV.state));
        comparatorMap.put(FieldName.DENSITY, censusCSVComparator = Comparator.comparing(indiaCensusCSV -> indiaCensusCSV.densityPerSqKm));
    }

    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        CsvToBean<IndiaCensusCSV> csvToBean = null;
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaCensusCSV> csvFileIterator = csvBuilder.getCSVFileIterator(reader, IndiaCensusCSV.class);
            Iterable<IndiaCensusCSV> censusCSVIterable = () -> csvFileIterator;
            StreamSupport.stream(censusCSVIterable.spliterator(), false).forEach(censusCSV -> censusCSVMap.put(censusCSV.state, new IndiaCensusDAO(censusCSV)));
            return censusCSVMap.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(
                    "", CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException("", CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (RuntimeException e) {
            throw new CensusAnalyserException("", CensusAnalyserException.ExceptionType.WRONG_DELIMETER_OR_HEADER);
        }
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
        try (Reader reader = Files.newBufferedReader(Paths.get(usCensusDataFile));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<USCensusData> csvFileIterator = csvBuilder.getCSVFileIterator(reader, USCensusData.class);
            Iterable<USCensusData> usCSVCodeIterable = () -> csvFileIterator;
            StreamSupport.stream(usCSVCodeIterable.spliterator(), false).forEach(censusCSV -> censusCSVMap.put(censusCSV.state, new IndiaCensusDAO(censusCSV)));
            return censusCSVMap.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(
                    "", CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException("", CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }


    }


}
