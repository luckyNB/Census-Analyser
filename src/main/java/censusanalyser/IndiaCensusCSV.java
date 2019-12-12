package censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class IndiaCensusCSV {
    @CsvBindByName(column = "State")
    public String state;
    @CsvBindByName(column = "Population")
    public int population;
    @CsvBindByName(column = "AreaInSqKm")
    public Double areaInSqKm;
    @CsvBindByName(column = "DensityPerSqKm")
    public Double densityPerSqKm;

    public IndiaCensusCSV(String state, int population, Double densityPerSqKm, Double areaInSqKm) {


    }

    @Override
    public String toString() {
        return "IndiaCensusCSV{" +
                "state='" + state + '\'' +
                ", population=" + population +
                ", areaInSqKm=" + areaInSqKm +
                ", densityPerSqKm=" + densityPerSqKm +
                '}';
    }
}
