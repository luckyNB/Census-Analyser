package censusanalyser;

public class IndiaCensusDAO {
    public int population;
    public Double densityPerSqKm;
    public Double areaInSqKm;
    public String state;
    public String stateCode;


    public IndiaCensusDAO(IndiaCensusCSV indiaCensusCSV) {
        this.state = indiaCensusCSV.state;
        this.areaInSqKm = indiaCensusCSV.areaInSqKm;
        this.population = indiaCensusCSV.population;
        this.densityPerSqKm = indiaCensusCSV.densityPerSqKm;

    }

    public IndiaCensusDAO(USCensusData usCensusData) {
        this.state = usCensusData.state;
        this.stateCode = usCensusData.stateCode;
        this.population = usCensusData.population;
        densityPerSqKm=usCensusData.populationDensity;
        this.areaInSqKm=usCensusData.totalArea;
    }


    public IndiaCensusCSV getIndiaCensusCSV(){
        return new IndiaCensusCSV(state,population,densityPerSqKm,areaInSqKm);
    }
}
