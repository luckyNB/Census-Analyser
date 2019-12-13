package censusanalyser;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

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
        censusMap = censusAdapter.loadCensusData(IndiaCensusCSV.class, csvFilePath[0]);

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


    public enum Country {
        INDIA, USA
    }
}


