package censusanalyser;

import java.util.HashMap;
import java.util.Map;

public class IndiaCensusAdapter extends CensusAdapter {
    Map<String, CensusDAO> censusDAOMap = null;

   public IndiaCensusAdapter() {
        censusDAOMap = new HashMap<>();
    }

    @Override
    public Map<String, CensusDAO> loadingCensusData(String... csvFilePath) throws CensusAnalyserException {
        censusDAOMap = super.loadCensusData(IndiaCensusCSV.class, csvFilePath[0]);
        return censusDAOMap;
    }
}





