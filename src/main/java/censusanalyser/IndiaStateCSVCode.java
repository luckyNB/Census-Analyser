package censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class IndiaStateCSVCode {


    @CsvBindByName(column = "StateName")
    private String StateName;

    @CsvBindByName(column = "StateCode" )
    private String StateCode;

    @Override
    public String toString() {
        return "IndiaStateCSVCode{" +
                "StateName='" + StateName + '\'' +
                ", StateCode='" + StateCode + '\'' +
                '}';
    }
}
