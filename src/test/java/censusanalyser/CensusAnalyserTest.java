package censusanalyser;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class CensusAnalyserTest {

    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String INDIA_STATE_CENSUS_WRONG = "/home/admin1/Downloads/CensusAnalyser/CensusAnalyser/src/test/resources/IndiaStateCensusDataWrong.csv";
    private static final String US_CENSUS_DATA = "/home/admin1/Downloads/CensusAnalyser/CensusAnalyser/src/test/resources/USCensusData.csv";
    private static final String US_CENSUS_DATA_WRONG_DATA = "/home/admin1/Downloads/CensusAnalyser/CensusAnalyser/src/test/resources/USCensusDataWrongData.csv";
    private static String WRONGFILETYPE = "/home/admin1/Downloads/CensusAnalyser/CensusAnalyser/src/test/resources/IndiaStateCensusDataWrong.json";

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

    @Test
    public void givenStateCensusCSVData_WhenSortedByStateName_Then_AndhraPradesh_ShouldBe_FirstState() {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        try {
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH);
            String result = censusAnalyser.getSortedCensusCSV(FieldName.STATE, CensusAnalyser.Country.INDIA);
            IndiaCensusCSV[] indiaCensusCSVS = new Gson().fromJson(result, IndiaCensusCSV[].class);
            Assert.assertEquals("Andhra Pradesh", indiaCensusCSVS[0].state.trim());
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenStateCensusCSVData_WhenSortedByStateName_Then_WestBengal_ShouldBe_LastState() {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        try {
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH);
            String result = censusAnalyser.getSortedCensusCSV(FieldName.STATE, CensusAnalyser.Country.INDIA);
            IndiaCensusCSV[] indiaCensusCSVS = new Gson().fromJson(result, IndiaCensusCSV[].class);
            Assert.assertEquals("West Bengal", indiaCensusCSVS[indiaCensusCSVS.length - 1].state.trim());
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void givenStateCensusCSVData_WhenSortedByPopulation_ThenSikkim_ShouldBe_FirstState() {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        try {
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH);
            String result = censusAnalyser.getSortedCensusCSV(FieldName.POULATION, CensusAnalyser.Country.INDIA);
            IndiaCensusCSV[] indiaCensusCSVS = new Gson().fromJson(result, IndiaCensusCSV[].class);
            Assert.assertEquals("Sikkim", indiaCensusCSVS[0].state.trim());
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenStateCensusCSVData_WhenSortedByPopulation_ThenUttarPradesh_ShouldBe_LastState() {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        try {
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH);
            String result = censusAnalyser.getSortedCensusCSV(FieldName.POULATION, CensusAnalyser.Country.INDIA);
            IndiaCensusCSV[] indiaCensusCSVS = new Gson().fromJson(result, IndiaCensusCSV[].class);
            Assert.assertEquals("Uttar Pradesh", indiaCensusCSVS[indiaCensusCSVS.length - 1].state.trim());
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenStateCensusCSVData_WhenSortedByDensity_ThenArunachal_Pradesh_ShouldBe_FirstState() {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        try {
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH);
            String sortedData = censusAnalyser.getSortedCensusCSV(FieldName.DENSITY, CensusAnalyser.Country.INDIA);
            IndiaCensusCSV[] indiaCensusCSVS = new Gson().fromJson(sortedData, IndiaCensusCSV[].class);
            Assert.assertEquals("Arunachal Pradesh", indiaCensusCSVS[0].state.trim());
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenStateCensusCSVData_WhenSortedByDensity_ThenBihar_ShouldBe_LastState() {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        try {
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH);
            String sortedData = censusAnalyser.getSortedCensusCSV(FieldName.DENSITY, CensusAnalyser.Country.INDIA);
            IndiaCensusCSV[] indiaCensusCSVS = new Gson().fromJson(sortedData, IndiaCensusCSV[].class);
            Assert.assertEquals("Bihar", indiaCensusCSVS[indiaCensusCSVS.length - 1].state.trim());
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenStateCensusCSVData_WhenSortedByArea_Then_Goa_ShouldBe_FirstState() {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        try {
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH);
            String sortedData = censusAnalyser.getSortedCensusCSV(FieldName.AREA, CensusAnalyser.Country.INDIA);
            IndiaCensusCSV[] indiaCensusCSVS = new Gson().fromJson(sortedData, IndiaCensusCSV[].class);
            Assert.assertEquals("Goa", indiaCensusCSVS[0].state.trim());
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenStateCensusCSVData_WhenSortedByArea_Then_Rajasthan_ShouldBe_LastState() {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        try {
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH);
            String sortedData = censusAnalyser.getSortedCensusCSV(FieldName.AREA, CensusAnalyser.Country.INDIA);
            IndiaCensusCSV[] indiaCensusCSVS = new Gson().fromJson(sortedData, IndiaCensusCSV[].class);
            Assert.assertEquals("Rajasthan", indiaCensusCSVS[indiaCensusCSVS.length - 1].state.trim());
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

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
    public void givenUSCensusData_WhenWrongHeader_ThenShould_ThrowCensusAnalyserException() {
        USCensusAdapter usCensusAdapter = AdapterFactory.getAdapterObject(CensusAnalyser.Country.USA);
        try {
            Map<String, CensusDAO> result = usCensusAdapter.loadingCensusData(WRONGFILETYPE);
            Assert.assertEquals(51, result.size());
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.WRONG_DELIMETER_OR_HEADER, e.type);
        }
    }

    @Test
    public void givenUSCensusData_WhenSortedByStateName_ThenAlabama_ShouldFirstState() {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        try {
            censusAnalyser.loadCensusData(CensusAnalyser.Country.USA, US_CENSUS_DATA);
            String sortedDataIndiaCensusCSV = censusAnalyser.getSortedCensusCSV(FieldName.STATE, CensusAnalyser.Country.USA);
            USCensusData[] usCensusData = new Gson().fromJson(sortedDataIndiaCensusCSV, USCensusData[].class);
            Assert.assertEquals("Alabama", usCensusData[0].state.trim());
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCensusData_WhenSortedByStateName_ThenWyoming_ShouldLastState() {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        try {
            censusAnalyser.loadCensusData(CensusAnalyser.Country.USA, US_CENSUS_DATA);
            String sortedDataIndiaCensusCSV = censusAnalyser.getSortedCensusCSV(FieldName.STATE, CensusAnalyser.Country.USA);
            USCensusData[] usCensusData = new Gson().fromJson(sortedDataIndiaCensusCSV, USCensusData[].class);
            Assert.assertEquals("Wyoming", usCensusData[usCensusData.length - 1].state.trim());
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void givenUSCensusData_WhenSortedByPopulation_ThenWyoming_ShouldFirstState() {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        try {
            censusAnalyser.loadCensusData(CensusAnalyser.Country.USA, US_CENSUS_DATA);
            String sortedDataIndiaCensusCSV = censusAnalyser.getSortedCensusCSV(FieldName.POULATION, CensusAnalyser.Country.USA);
            USCensusData[] usCensusData = new Gson().fromJson(sortedDataIndiaCensusCSV, USCensusData[].class);
            Assert.assertEquals("Wyoming", usCensusData[0].state.trim());
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCensusData_WhenSortedByPopulation_ThenCalifornia_ShouldLastState() {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        try {
            censusAnalyser.loadCensusData(CensusAnalyser.Country.USA, US_CENSUS_DATA);
            String sortedDataIndiaCensusCSV = censusAnalyser.getSortedCensusCSV(FieldName.POULATION, CensusAnalyser.Country.USA);
            USCensusData[] usCensusData = new Gson().fromJson(sortedDataIndiaCensusCSV, USCensusData[].class);
            Assert.assertEquals("California", usCensusData[usCensusData.length - 1].state.trim());
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCensusData_WhenSortedByDensity_ThenAlaska_ShouldBeFirstState() {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        try {
            censusAnalyser.loadCensusData(CensusAnalyser.Country.USA, US_CENSUS_DATA);
            String sortedDataIndiaCensusCSV = censusAnalyser.getSortedCensusCSV(FieldName.DENSITY, CensusAnalyser.Country.USA);
            USCensusData[] usCensusData = new Gson().fromJson(sortedDataIndiaCensusCSV, USCensusData[].class);
            Assert.assertEquals("Alaska", usCensusData[0].state.trim());
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCensusData_WhenSortedByDensity_ThenDistrict_of_Columbia_ShouldBeLastState() {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        try {
            censusAnalyser.loadCensusData(CensusAnalyser.Country.USA, US_CENSUS_DATA);
            String sortedDataIndiaCensusCSV = censusAnalyser.getSortedCensusCSV(FieldName.DENSITY, CensusAnalyser.Country.USA);
            USCensusData[] usCensusData = new Gson().fromJson(sortedDataIndiaCensusCSV, USCensusData[].class);
            Assert.assertEquals("District of Columbia", usCensusData[usCensusData.length - 1].state.trim());
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCensusData_WhenSortedByArea_Then_Alaska_ShouldBeLastState() {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        try {
            censusAnalyser.loadCensusData(CensusAnalyser.Country.USA, US_CENSUS_DATA);
            String sortedDataIndiaCensusCSV = censusAnalyser.getSortedCensusCSV(FieldName.AREA, CensusAnalyser.Country.USA);
            USCensusData[] usCensusData = new Gson().fromJson(sortedDataIndiaCensusCSV, USCensusData[].class);
            Assert.assertEquals("Alaska", usCensusData[usCensusData.length - 1].state.trim());
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCensusData_WhenSortedByArea_Then_District_of_Columbia_ShouldBeFirstState() {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        try {
            censusAnalyser.loadCensusData(CensusAnalyser.Country.USA, US_CENSUS_DATA);
            String sortedDataIndiaCensusCSV = censusAnalyser.getSortedCensusCSV(FieldName.AREA, CensusAnalyser.Country.USA);
            USCensusData[] usCensusData = new Gson().fromJson(sortedDataIndiaCensusCSV, USCensusData[].class);
            Assert.assertEquals("District of Columbia", usCensusData[0].state.trim());
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianCensusData_SortedByPopulationAndDensity_Should_ReturnSortedListOfStateOfIndia() {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        try {
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH);
            String list = censusAnalyser.sortList();
            IndiaCensusCSV[] indiaCensusCSVS = new Gson().fromJson(list, IndiaCensusCSV[].class);
            Assert.assertEquals("Tamil Nadu", indiaCensusCSVS[0].state.trim());
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void givenUSCensusData_SortedByPopulationAndDensity_ShouldReturnReturnSortedListOfStateOfUS() {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        try {
            censusAnalyser.loadCensusData(CensusAnalyser.Country.USA, US_CENSUS_DATA);
            String list = censusAnalyser.sortList();
            USCensusData[] usCensusData = new Gson().fromJson(list, USCensusData[].class);
            Assert.assertEquals("Rhode Island",usCensusData[0].state.trim());
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }
}


