package censusanalyser;

import java.util.HashMap;
import java.util.Map;

public class USCensusAdapter extends CensusAdapter {

    Map<String, CensusDAO> censusDAOMap = null;

    public USCensusAdapter() {
        censusDAOMap = new HashMap<>();
    }

    @Override
    public Map<String, CensusDAO> loadingCensusData(String... csvFilePath) throws CensusAnalyserException {
        censusDAOMap = super.loadCensusData(USCensusData.class, csvFilePath[0]);
        return censusDAOMap;
    }
}
