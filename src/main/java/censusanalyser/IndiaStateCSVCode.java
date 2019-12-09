package censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class IndiaStateCSVCode {


    @CsvBindByName(column = "State Name",required = true)
    private String StateName;

    @CsvBindByName(column = "StateCode",required = true)
    private String StateCode;

    @Override
    public String toString() {
        return "IndiaStateCSVCode{" +
                "StateName='" + StateName + '\'' +
                ", StateCode='" + StateCode + '\'' +
                '}';
    }
}
