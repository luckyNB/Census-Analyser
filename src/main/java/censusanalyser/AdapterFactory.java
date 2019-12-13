package censusanalyser;

public class AdapterFactory {
    public static  <E extends CensusAdapter> E  getAdapterObject(CensusAnalyser.Country country){

        if(country.equals(CensusAnalyser.Country.INDIA)){
            return (E) new IndiaCensusAdapter();
        }
        else if(country.equals(CensusAnalyser.Country.USA))
        {
            return (E) new USCensusAdapter();
        }

        return null;
    }


}
