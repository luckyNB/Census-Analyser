package censusanalyser;


import com.bridgelabz.*;
import com.google.gson.Gson;
import com.opencsv.bean.CsvToBean;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

public class CensusAnalyser {
    List<IndiaCensusDAO> censusCSVList = null;

    public CensusAnalyser() {
        this.censusCSVList = new ArrayList<IndiaCensusDAO>();
    }

    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        CsvToBean<IndiaCensusCSV> csvToBean = null;
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaCensusCSV> csvFileIterator = csvBuilder.getCSVFileIterator(reader, IndiaCensusCSV.class);
            while (csvFileIterator.hasNext()) {
                censusCSVList.add(new IndiaCensusDAO(csvFileIterator.next()));
            }

            return censusCSVList.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (IllegalStateException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException("", CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }

    private <E> int getCount(Iterator<E> iterator) {
        Iterable<E> censusCSVS = () -> iterator;
        int namOfEateries = (int) StreamSupport.stream(censusCSVS.spliterator(), false).count();
        return namOfEateries;
    }

    public int loadIndiaStateCodeData(String indiaCensusCsvFilePath) throws CensusAnalyserException {
        CsvToBean<IndiaStateCSVCode> csvToBean = null;
        try (Reader reader = Files.newBufferedReader(Paths.get(indiaCensusCsvFilePath));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaCensusCSV> csvFileIterator = csvBuilder.getCSVFileIterator(reader, IndiaCensusCSV.class);

           return this.getCount(csvFileIterator);
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (IllegalStateException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException("", CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }

    }

    public String getStateWiseSortedCensusData(String csvFilePath) throws CensusAnalyserException {


        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaCensusCSV> csvFileIterator = csvBuilder.getCSVFileIterator(reader, IndiaCensusCSV.class);
            while (csvFileIterator.hasNext()) {
                censusCSVList.add(new IndiaCensusDAO(csvFileIterator.next()));
            }
            censusCSVList.size();
            this.sort();
            String sorted = new Gson().toJson(this.censusCSVList);
            return sorted;
        } catch (IOException | CSVBuilderException | CensusAnalyserException e) {

        }
        return null;
    }

    private void sort() throws CensusAnalyserException {
        Comparator<IndiaCensusDAO> censusCSVComparator = Comparator.comparing(indiaCensusCSV -> indiaCensusCSV.state);

        for (int i = 0; i < censusCSVList.size(); i++) {
            for (int j = 0; j < censusCSVList.size() - i - 1; j++) {
                IndiaCensusDAO censusCSV1 = censusCSVList.get(j);
                IndiaCensusDAO censusCSV2 = censusCSVList.get(j + 1);
                if (censusCSVComparator.compare(censusCSV1, censusCSV2) > 0) {
                    censusCSVList.set(j, censusCSV2);
                    censusCSVList.set(j + 1, censusCSV1);
                }

            }
        }
    }
}
