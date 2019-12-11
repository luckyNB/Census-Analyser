package censusanalyser;

import com.bridgelabz.CSVBuilderException;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CensusAnalyserTest {

    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String INDIA_STATTE_CODE_CSV_FILE_PATH = "/home/admin1/Downloads/CensusAnalyser/CensusAnalyser/src/test/resources/IndiaStateCode.csv";
    private static final String INDIA_STATE_CENSUS_WRONG = "/home/admin1/Downloads/CensusAnalyser/CensusAnalyser/src/test/resources/IndiaStateCensusDataWrong.csv";
private  static final String INDIA_CENSUS_WRONG_FILE_TYPE="/home/admin1/Downloads/CensusAnalyser/CensusAnalyser/src/test/resources/IndiaStateCensusDataWrong.json";
    @Test
    public void givenIndianCodeCSVFile_WhenCorrect_Should_ReturnNumberOfRecordAvailable() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfRecords = censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(29, numOfRecords);
        } catch (CensusAnalyserException e) {
        }
    }

    @Test
    public void givenIndianCensusCSVFile_When_IncorrectDelimeteter_ShouldThrowCensusAnalyserException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfRecords = censusAnalyser.loadIndiaCensusData(INDIA_STATE_CENSUS_WRONG);
            Assert.assertEquals(29, numOfRecords);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.WRONG_DELIMETER_OR_HEADER, e.type);
        }
    }

    @Test
    public void givenIndianCensusCSVFile_When_WrongHeader_Should_Throw_CensusAnalyserException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfRecords = censusAnalyser.loadIndiaCensusData(INDIA_STATE_CENSUS_WRONG);
            Assert.assertEquals(29, numOfRecords);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.WRONG_DELIMETER_OR_HEADER, e.type);
        }
    }

    @Test
    public void givenIndianCensusData_WhenWrongFile_Should_ThrowCensusAnalyserException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaCensusData(WRONG_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
         //   Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIndianCensusCSVFile_WhenCorrect_Should_ReturnsNumberOfRecord() {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        ExpectedException exceptionRule = ExpectedException.none();
        int numOfRecords = 0;
        try {
            numOfRecords = censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            censusAnalyser.loadIndiaStateCodeData(INDIA_STATTE_CODE_CSV_FILE_PATH);
            Assert.assertEquals(29, numOfRecords);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenStateCensusData_WhenSorted_ShouldReturn_SortedResult() {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        String sortedCensusData = null;
        try {
            censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            censusAnalyser.loadIndiaStateCodeData(INDIA_STATTE_CODE_CSV_FILE_PATH);
            sortedCensusData = censusAnalyser.getStateWiseSortedCensusData();
            IndiaCensusCSV[] indiaCensusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Andhra Pradesh", indiaCensusCSV[0].state.trim());
        } catch (CensusAnalyserException | CSVBuilderException e) {
            e.printStackTrace();
        }
    }
}
