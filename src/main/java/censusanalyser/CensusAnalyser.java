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

    private <E> Iterator<E> getCSVFileIterator(Reader reader, Class csvClass) {
        CsvToBean<E> csvToBean = null;
        CsvToBeanBuilder<IndiaCensusCSV> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
        csvToBeanBuilder.withType(csvClass);
        csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
        csvToBean = (CsvToBean<E>) csvToBeanBuilder.build();
        return csvToBean.iterator();
    }

    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        CsvToBean<IndiaCensusCSV> csvToBean = null;
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            Iterator<IndiaCensusCSV> csvIterator = new OpenCSVBuilder().getCSVFileIterator(reader, IndiaCensusCSV.class);
            return  this.getCount(csvIterator);
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (IllegalStateException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE);
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
            Iterator<IndiaStateCSVCode> csvIterator = new OpenCSVBuilder().getCSVFileIterator(reader, IndiaStateCSVCode.class);
            return this.getCount(csvIterator);
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (IllegalStateException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE);
        }
    }
}
