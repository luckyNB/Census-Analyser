package censusanalyser;

import com.google.gson.Gson;
import com.opencsv.bean.CsvToBean;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

public class CensusAnalyser {
    List<IndiaCensusCSV> indiaCensusCSVList = null;

    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        CsvToBean<IndiaCensusCSV> csvToBean = null;
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            indiaCensusCSVList = csvBuilder.getCSVFileList(reader, IndiaCensusCSV.class);
            return indiaCensusCSVList.size();
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

    private <E> Integer getCount(Iterator<E> iterator) {
        Iterable<E> censusCSVS = () -> iterator;
        int namOfEateries = (int) StreamSupport.stream(censusCSVS.spliterator(), false).count();
        return namOfEateries;
    }

    public int loadIndiaStateCodeData(String indiaCensusCsvFilePath) throws CensusAnalyserException {
        CsvToBean<IndiaStateCSVCode> csvToBean = null;
        try (Reader reader = Files.newBufferedReader(Paths.get(indiaCensusCsvFilePath));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaStateCSVCode> csvIterator = csvBuilder.getCSVFileIterator(reader, IndiaStateCSVCode.class);
            return this.getCount(csvIterator);
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

    public String getStateWiseSortedCensusData(String csvFilePath) {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            indiaCensusCSVList = csvBuilder.getCSVFileList(reader, IndiaCensusCSV.class);
            this.sort();
            String sorted = new Gson().toJson(indiaCensusCSVList);
            return sorted;
        } catch (IOException | CSVBuilderException | CensusAnalyserException e) {

        }
        return null;
    }

    private void sort() throws CensusAnalyserException {
        Comparator<IndiaCensusCSV> censusCSVComparator = Comparator.comparing(indiaCensusCSV -> indiaCensusCSV.state);

        if (indiaCensusCSVList == null || indiaCensusCSVList.size() == 0) {
            throw new CensusAnalyserException("NO CENSUS DATA", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        for (int i = 0; i < indiaCensusCSVList.size(); i++) {
            for (int j = 0; j < indiaCensusCSVList.size() - i - 1; j++) {
                IndiaCensusCSV censusCSV1 = indiaCensusCSVList.get(j);
                IndiaCensusCSV censusCSV2 = indiaCensusCSVList.get(j + 1);
                if (censusCSVComparator.compare(censusCSV1, censusCSV2) > 0) {
                    indiaCensusCSVList.set(j, censusCSV2);
                    indiaCensusCSVList.set(j + 1, censusCSV1);
                }

            }
        }
    }
}
