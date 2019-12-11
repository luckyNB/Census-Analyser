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

    public CensusAnalyser() {
        this.censusCSVMap = new HashMap<String, IndiaCensusDAO>();
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
        } catch (IllegalStateException e) {
            throw new CensusAnalyserException(
                    "", CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException("", CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
        catch (RuntimeException e){
            throw  new CensusAnalyserException("",CensusAnalyserException.ExceptionType.WRONG_DELIMETER_OR_HEADER);
        }
    }

    public int loadIndiaStateCodeData(String indiaCensusCsvFilePath) throws CensusAnalyserException {
        CsvToBean<IndiaStateCSVCode> csvToBean = null;
        try (Reader reader = Files.newBufferedReader(Paths.get(indiaCensusCsvFilePath));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaStateCSVCode> csvFileIterator = csvBuilder.getCSVFileIterator(reader, IndiaStateCSVCode.class);
            while (csvFileIterator.hasNext()) {
                IndiaStateCSVCode indiaStateCSVCode = csvFileIterator.next();
                IndiaCensusDAO censusDAO = censusCSVMap.get(indiaStateCSVCode.StateCode);
                if (censusDAO == null)
                    continue;
                censusDAO.state = indiaStateCSVCode.StateCode;
            }
            return censusCSVMap.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(
                    "", CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (IllegalStateException e) {
            throw new CensusAnalyserException(
                    "", CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException("", CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }

    public String getStateWiseSortedCensusData() throws CSVBuilderException, CensusAnalyserException {
        try {
            List<IndiaCensusDAO> indiaCensusDAOList = censusCSVMap.values().stream().collect(Collectors.toList());
            censusCSVMap.size();
            Comparator<IndiaCensusDAO> censusCSVComparator = Comparator.comparing(indiaCensusCSV -> indiaCensusCSV.state);

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
}
