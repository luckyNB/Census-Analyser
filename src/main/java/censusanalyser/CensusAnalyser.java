package censusanalyser;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.StreamSupport;

public class CensusAnalyser {
    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        CsvToBean<IndiaCensusCSV> csvToBean = null;
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            CsvToBeanBuilder<IndiaCensusCSV> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
            csvToBeanBuilder.withType(IndiaCensusCSV.class);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            csvToBean = csvToBeanBuilder.build();
            Iterator<IndiaCensusCSV> csvIterator = csvToBean.iterator();

            Iterable<IndiaCensusCSV> censusCSVS = () -> csvIterator;
            int namOfEateries = (int) StreamSupport.stream(censusCSVS.spliterator(), false).count();
            return namOfEateries;

        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (IllegalStateException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE);
        }

    }

    public int loadIndiaStateCodeData(String indiaCensusCsvFilePath) throws CensusAnalyserException {

        CsvToBean<IndiaStateCSVCode> csvToBean = null;
        try (Reader reader = Files.newBufferedReader(Paths.get(indiaCensusCsvFilePath));) {
            CsvToBeanBuilder<IndiaStateCSVCode> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
            csvToBeanBuilder.withType(IndiaStateCSVCode.class);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            csvToBean = csvToBeanBuilder.build();
            Iterator<IndiaStateCSVCode> csvIterator = csvToBean.iterator();

            Iterable<IndiaStateCSVCode> censusCSVS = () -> csvIterator;
            int namOfEateries = (int) StreamSupport.stream(censusCSVS.spliterator(), false).count();
            return namOfEateries;
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (IllegalStateException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE);
        }
    }
}
