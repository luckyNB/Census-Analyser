package censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class USCensusData {
    @CsvBindByName(column = "State")
    public String state;

    @CsvBindByName(column = "Population Density")
    public Double populationDensity;
    @CsvBindByName(column = "Population")
    public int population;

    @CsvBindByName(column = "State Id")
    public String stateCode;

    @CsvBindByName(column = "Total area")
    public Double totalArea;





}



