package censusanalyser;


import com.google.gson.Gson;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CensusAnalyser {

    Map<String, CensusDAO> censusMap;


    public CensusAnalyser() {
        this.censusMap = new HashMap<>();
    }

    private String sortData(Comparator<CensusDAO> censusComparator) throws CensusAnalyserException {
        if (censusMap.size() == 0 || censusMap.equals(null)) {
            throw new CensusAnalyserException("No Data to Read", CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
        List<CensusDAO> censusDAOS = censusMap.values().stream().collect(Collectors.toList());
        this.sort(censusDAOS, censusComparator);
        String sortedStateCensusJson = new Gson().toJson(censusDAOS);
        return sortedStateCensusJson;
    }





    private void sort(List<CensusDAO> censusDAOS, Comparator<CensusDAO> censusComparator) {
        for (int i = 0; i < censusDAOS.size() - 1; i++) {
            for (int j = 0; j < censusDAOS.size() - i - 1; j++) {
                CensusDAO census1 = censusDAOS.get(j);
                CensusDAO census2 = censusDAOS.get(j + 1);
                if (censusComparator.compare(census1, census2) > 0) {
                    censusDAOS.set(j, census2);
                    censusDAOS.set(j + 1, census1);
                }
            }
        }
    }

    public enum Country {
        INDIA, USA
    }
}


