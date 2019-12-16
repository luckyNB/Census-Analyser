package censusanalyser;

import censusanalyser.adapter.AdapterFactory;
import censusanalyser.adapter.IndiaCensusAdapter;
import censusanalyser.adapter.USCensusAdapter;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class USAdapterTest {
    private static final String US_CENSUS_DATA = "/home/admin1/Downloads/CensusAnalyser/CensusAnalyser/src/test/resources/USCensusData.csv";
    private static final String US_CENSUS_DATA_WRONG_DATA = "/home/admin1/Downloads/CensusAnalyser/CensusAnalyser/src/test/resources/USCensusDataWrongData.csv";
    private static String WRONGFILETYPE = "/home/admin1/Downloads/CensusAnalyser/CensusAnalyser/src/test/resources/IndiaStateCensusDataWrong.json";
    String US_WRONG_FILE_TYPE = "/home/admin1/Downloads/CensusAnalyser/CensusAnalyser/src/test/resources/IndiaStateCensusDataWrong.json";

    @Test
    public void givenUSCensusData_WhenCorrect_Should_ReturnValidNumberOfRecords() {
        USCensusAdapter usCensusAdapter = AdapterFactory.getAdapterObject(CensusAnalyser.Country.USA);
        try {
            Map<String, CensusDAO> result = usCensusAdapter.loadingCensusData(US_CENSUS_DATA);
            Assert.assertEquals(51, result.size());
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCensusData_WhenIncorrectDelimeter_Then_Should_ThrowCensusAnalyserException() {
        USCensusAdapter usCensusAdapter = AdapterFactory.getAdapterObject(CensusAnalyser.Country.USA);
        try {
            Map<String, CensusDAO> result = usCensusAdapter.loadingCensusData(US_CENSUS_DATA_WRONG_DATA);
            Assert.assertEquals(51, result.size());
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.WRONG_DELIMETER_OR_HEADER, e.type);
        }
    }

    @Test
    public void givenStateCensusData_WhenWrongFileType_ThenShould_ThrowCensusAnalyserException() {
        IndiaCensusAdapter indiaCensusAdapter = new IndiaCensusAdapter();
        try {
            Map<String, CensusDAO> result = indiaCensusAdapter.loadingCensusData(US_WRONG_FILE_TYPE);
            Assert.assertEquals(29, result.size());
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.WRONG_DELIMETER_OR_HEADER, e.type);
        }
    }

    @Test
    public void givenUSCensusData_WhenWrongHeader_ThenShould_ThrowCensusAnalyserException() {
        USCensusAdapter usCensusAdapter = AdapterFactory.getAdapterObject(CensusAnalyser.Country.USA);
        try {
            Map<String, CensusDAO> result = usCensusAdapter.loadingCensusData(WRONGFILETYPE);
            Assert.assertEquals(51, result.size());
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.WRONG_DELIMETER_OR_HEADER, e.type);
        }
    }
}
