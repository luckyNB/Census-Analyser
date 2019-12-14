package censusanalyser;

public class CensusDAO {
    public String state;
    public String stateCode = null;
    public double areaInSqKm;
    public double densityPerSqKm;
    public int population;

    public CensusDAO(IndiaCensusCSV indiaCensusCsv) {
        this.state = indiaCensusCsv.state;
        this.areaInSqKm = indiaCensusCsv.areaInSqKm;
        this.densityPerSqKm = indiaCensusCsv.densityPerSqKm;
        this.population = indiaCensusCsv.population;
    }

    public CensusDAO(USCensusData usCensusCsv) {
        this.state = usCensusCsv.state;
        this.stateCode = usCensusCsv.stateId;
        this.population = usCensusCsv.population;
        this.densityPerSqKm = usCensusCsv.populationDensity;
        this.areaInSqKm = usCensusCsv.totalArea;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public double getAreaInSqKm() {
        return areaInSqKm;
    }

    public void setAreaInSqKm(double areaInSqKm) {
        this.areaInSqKm = areaInSqKm;
    }

    public double getDensityPerSqKm() {
        return densityPerSqKm;
    }

    public void setDensityPerSqKm(double densityPerSqKm) {
        this.densityPerSqKm = densityPerSqKm;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public Object getCensusDTO(CensusAnalyser.Country country) {
        if (country.equals(CensusAnalyser.Country.USA))
            return new USCensusData(state, population, densityPerSqKm, areaInSqKm);
        return new IndiaCensusCSV(state, population, (int) densityPerSqKm, (int) areaInSqKm);
    }

    @Override
    public String toString() {
        return "CensusDAO{" +
                "state='" + state + '\'' +
                ", stateCode='" + stateCode + '\'' +
                ", areaInSqKm=" + areaInSqKm +
                ", densityPerSqKm=" + densityPerSqKm +
                ", population=" + population +
                '}';
    }
}

