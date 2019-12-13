package censusanalyser;


import com.google.gson.Gson;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toCollection;

public class CensusAnalyser {

    Map<String, CensusDAO> censusMap;
    Map<FieldName, Comparator> comparatorMap = null;
    Comparator<CensusDAO> censusCSVComparator = null;

    public CensusAnalyser() {
        this.censusMap = new HashMap<>();
        comparatorMap = new HashMap<>();
        comparatorMap.put(FieldName.POULATION, censusCSVComparator = Comparator.comparing(censusDAO -> censusDAO.population));
        comparatorMap.put(FieldName.AREA, censusCSVComparator = Comparator.comparing(indiaCensusCSV -> indiaCensusCSV.areaInSqKm));
        comparatorMap.put(FieldName.STATE, censusCSVComparator = Comparator.comparing(indiaCensusCSV -> indiaCensusCSV.state));
        comparatorMap.put(FieldName.DENSITY, censusCSVComparator = Comparator.comparing(indiaCensusCSV -> indiaCensusCSV.densityPerSqKm));

    }

    private String sortData(Comparator<CensusDAO> censusComparator) throws CensusAnalyserException {
        Comparator<IndiaCensusDAO> censusCSVComparator = Comparator.comparing(indiaCensusCSV -> indiaCensusCSV.state);
        if (censusMap.size() == 0 || censusMap.equals(null)) {
            throw new CensusAnalyserException("No Data to Read", CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
        List<CensusDAO> censusDAOS = censusMap.values().stream().collect(Collectors.toList());
        this.sort(censusDAOS, censusComparator);
        String sortedStateCensusJson = new Gson().toJson(censusDAOS);
        return sortedStateCensusJson;
    }


    public Map<String,CensusDAO> loadCensusData(Country country, String... csvFilePath) throws CensusAnalyserException {
        CensusAdapter censusAdapter = AdapterFactory.getAdapterObject(country);
        censusMap = censusAdapter.loadCensusData(IndiaCensusCSV.class,csvFilePath[0]);

        return censusMap;
    }

    public String getSortedData(FieldName field) throws CensusAnalyserException {
        if (censusMap == null || censusMap.size() == 0) {
            throw new CensusAnalyserException("no census data",
                    CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        ArrayList censusDTOS= (ArrayList) censusMap.values().stream()
                .sorted(this.comparatorMap.get(field))
                .map(censusDAO -> ((CensusDAO) censusDAO).getCensusDTO(Country.INDIA))
                .collect(toCollection(ArrayList::new));
        String sortedStateCensusJson = new Gson().toJson(censusDTOS);
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


