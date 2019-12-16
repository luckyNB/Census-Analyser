package censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class USCensusData {
    @CsvBindByName(column = "State Id")
    public String stateId;
    @CsvBindByName(column = "State")
    public String state;
    @CsvBindByName(column = "Population")
    public int population;
    @CsvBindByName(column = "Total area")
    public double totalArea;
    @CsvBindByName(column = "Population Density")
    public double populationDensity;

    public USCensusData(String state, int population, double densityPerSqKm, double areaInSqKm) {
        this.state = state;
        this.population = population;
        this.populationDensity = densityPerSqKm;
        this.totalArea = areaInSqKm;
    }

    public USCensusData() {
    }
}



