package censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class IndiaStateCSVCode {
    @CsvBindByName(column = "StateName")
    public String StateName;
    @CsvBindByName(column = "StateCode")
    public String StateCode;

    @Override
    public String toString() {
        return "IndiaStateCSVCode{" +
                "StateName='" + StateName + '\'' +
                ", StateCode='" + StateCode + '\'' +
                '}';
    }
}
