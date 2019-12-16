package censusanalyser;

import censusanalyser.adapter.IndiaCensusAdapter;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class IndianAdapterTest {

    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String INDIA_STATE_CENSUS_WRONG = "/home/admin1/Downloads/CensusAnalyser/CensusAnalyser/src/test/resources/IndiaStateCensusDataWrong.csv";
  @Test
    public void givenStateCensusData_ShouldReturn_NoOfRecord() {
        IndiaCensusAdapter indiaCensusAdapter = new IndiaCensusAdapter();
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            Map<String, CensusDAO> result = indiaCensusAdapter.loadingCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(29, result.size());
        } catch (CensusAnalyserException e) {

        }
    }
    @Test
    public void givenStateCensusData_WhenWrongFileType_ThenShould_ThrowCensusAnalyserException() {
        IndiaCensusAdapter indiaCensusAdapter = new IndiaCensusAdapter();
        try {
            Map<String, CensusDAO> result = indiaCensusAdapter.loadingCensusData(WRONG_CSV_FILE_PATH);
            Assert.assertEquals(29, result.size());
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenStateCensusData_WhenWrongDelimeter_ThenShould_ThrowCensusAnalyserException() {
        IndiaCensusAdapter indiaCensusAdapter = new IndiaCensusAdapter();
        try {
            Map<String, CensusDAO> result = indiaCensusAdapter.loadingCensusData(INDIA_STATE_CENSUS_WRONG);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.WRONG_DELIMETER_OR_HEADER, e.type);
        }
    }
}
