package censusanalyser;

public class CensusDAO {

    public String state;
    public String stateCode;
    public int population;
    public Double populationDensity;
    public Double totalArea;

    public CensusDAO(USCensusData usCensusData) {
        this.state = usCensusData.state;
        this.stateCode = usCensusData.stateCode;
        this.population = usCensusData.population;
        this.populationDensity=usCensusData.populationDensity;
        this.totalArea=usCensusData.totalArea;
    }
}
