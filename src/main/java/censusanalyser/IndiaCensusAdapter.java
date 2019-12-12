package censusanalyser;

import com.bridgelabz.CSVBuilderException;
import com.bridgelabz.CSVBuilderFactory;
import com.bridgelabz.ICSVBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.StreamSupport;

public class IndiaCensusAdapter extends CensusAdapter {
    Map<String, CensusDAO> censusDAOMap = null;

   public IndiaCensusAdapter() {
        censusDAOMap = new HashMap<>();
    }

    @Override
    public Map<String, CensusDAO> loadingCensusData(CensusAnalyser.Country country, String... csvFilePath) throws CensusAnalyserException {
        censusDAOMap = super.loadCensusData(IndiaCensusCSV.class, csvFilePath[0]);
        return censusDAOMap;
    }
}





