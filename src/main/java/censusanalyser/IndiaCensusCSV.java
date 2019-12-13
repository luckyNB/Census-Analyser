package censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class IndiaCensusCSV {
    @CsvBindByName(column = "State")
    public String state;

    @CsvBindByName(column = "Population")
    public int population;

    @CsvBindByName(column = "AreaInSqKm")
    public int areaInSqKm;

    @CsvBindByName(column = "DensityPerSqKm")
    public int densityPerSqKm;

    public IndiaCensusCSV() {
    }

    public IndiaCensusCSV(String state, int population, int densityPerSqKm, int areaInSqKm) {
        this.state = state;
        this.population = population;
        this.densityPerSqKm = densityPerSqKm;
        this.areaInSqKm = areaInSqKm;
    }
}
