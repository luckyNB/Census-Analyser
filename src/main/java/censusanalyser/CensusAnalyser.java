package censusanalyser;


import com.google.gson.Gson;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public Map<String, CensusDAO> loadCensusData(Country country, String... csvFilePath) throws CensusAnalyserException {
        CensusAdapter censusAdapter = AdapterFactory.getAdapterObject(country);
        censusMap = censusAdapter.loadingCensusData(csvFilePath);

        return censusMap;
    }

    public String getSortedCensusCSV(FieldName field, Country country) throws CensusAnalyserException {
        if (censusMap == null || censusMap.size() == 0) {
            throw new CensusAnalyserException("no census data",
                    CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        ArrayList censusDTOS = (ArrayList) censusMap.values().stream()
                .sorted(this.comparatorMap.get(field))
                .map(censusDAO -> ((CensusDAO) censusDAO).getCensusDTO(country))
                .collect(toCollection(ArrayList::new));
        String sortedStateCensusJson = new Gson().toJson(censusDTOS);

        return sortedStateCensusJson;
    }

    public String sortList() {
        List<CensusDAO> list = (ArrayList) censusMap.values().stream().collect(Collectors.toList());
        Comparator<CensusDAO> comparator = Comparator.comparing(censusDAO -> censusDAO.population);
        comparator.reversed().thenComparing(Comparator.comparing(censusDAO -> censusDAO.densityPerSqKm));
        comparator.reversed();
        Stream<CensusDAO> daoStream = list.stream().sorted(comparator);
        String sortedStateCensusJson = new Gson().toJson(list);
        return sortedStateCensusJson;
    }

    public enum Country {
        INDIA, USA
    }
}
