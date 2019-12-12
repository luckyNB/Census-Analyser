package censusanalyser;

public class IndiaCensusDAO {
    public int population;
    public int densityPerSqKm;
    public int areaInSqKm;
    public String state;
    public String stateCode;


    public IndiaCensusDAO(IndiaCensusCSV indiaCensusCSV) {
        this.state = indiaCensusCSV.state;
        this.areaInSqKm = indiaCensusCSV.areaInSqKm;
        this.population = indiaCensusCSV.population;
        this.densityPerSqKm = indiaCensusCSV.densityPerSqKm;

    }




    public IndiaCensusCSV getIndiaCensusCSV(){
        return new IndiaCensusCSV(state,population,densityPerSqKm,areaInSqKm);
    }
}
