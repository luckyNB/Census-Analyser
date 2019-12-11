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
