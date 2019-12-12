package censusanalyser;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class CensusAnalyserTest {

    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String INDIA_STATTE_CODE_CSV_FILE_PATH = "/home/admin1/Downloads/CensusAnalyser/CensusAnalyser/src/test/resources/IndiaStateCode.csv";
    private static final String INDIA_STATE_CENSUS_WRONG = "/home/admin1/Downloads/CensusAnalyser/CensusAnalyser/src/test/resources/IndiaStateCensusDataWrong.csv";
    private static final String INDIA_CENSUS_WRONG_FILE_TYPE = "/home/admin1/Downloads/CensusAnalyser/CensusAnalyser/src/test/resources/IndiaStateCensusDataWrong.json";
    private static final String US_CENSUS_DATA = "/home/admin1/Downloads/CensusAnalyser/CensusAnalyser/src/test/resources/USCensusData.csv";


    @Test
    public void givenStateCensusData_ShouldReturn_NoOfRecord() {
        IndiaCensusAdapter indiaCensusAdapter = new IndiaCensusAdapter();
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            Object result = indiaCensusAdapter.loadingCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(29, result);
        } catch (CensusAnalyserException e) {

        }

    }


    @Test
    public void givenStateCensusData_WhenWrongFileType_ThenShould_ThrowCensusAnalyserException() {
        IndiaCensusAdapter indiaCensusAdapter = new IndiaCensusAdapter();
        try {
            Map<String, CensusDAO> result = indiaCensusAdapter.loadingCensusData(CensusAnalyser.Country.INDIA, WRONG_CSV_FILE_PATH);
            Assert.assertEquals(29, result.size());
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }


    @Test
    public void givenStateCensusData_WhenWrongDelimeter_ThenShould_ThrowCensusAnalyserException() {
        IndiaCensusAdapter indiaCensusAdapter = new IndiaCensusAdapter();
        try {
            Map<String, CensusDAO> result = indiaCensusAdapter.loadingCensusData(CensusAnalyser.Country.INDIA, INDIA_STATE_CENSUS_WRONG);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.NO_CENSUS_DATA, e.type);
        }
    }


    @Test
    public void givenIndianStateCensusData_WhenWrongFileExtension_ThenShould_Throw_CensusAnalyserException() {
        IndiaCensusAdapter indiaCensusAdapter = (IndiaCensusAdapter) AdapterFactory.getAdapterObject(CensusAnalyser.Country.INDIA);
        try {

            Map<String, CensusDAO> result = indiaCensusAdapter.loadingCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_WRONG_FILE_TYPE);
            Assert.assertEquals(29, result.size());
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCensusData_WhenCorrect_Should_ReturnValidNumberOfRecords(){

        USCensusAdapter usCensusAdapter=AdapterFactory.getAdapterObject(CensusAnalyser.Country.USA);
        try {
           Map<String,CensusDAO> result= usCensusAdapter.loadingCensusData(CensusAnalyser.Country.USA,US_CENSUS_DATA);
            Assert.assertEquals(51,result.size());
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }

    }
}


