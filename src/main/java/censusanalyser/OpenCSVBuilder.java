package censusanalyser;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.Reader;
import java.util.Iterator;

public class OpenCSVBuilder {

    static  <E> Iterator<E> getCSVFileIterator(Reader reader, Class<E> csvClass) {
        CsvToBean<E> csvToBean = null;
        CsvToBeanBuilder<IndiaCensusCSV> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
        csvToBeanBuilder.withType((Class<? extends IndiaCensusCSV>) csvClass);
        csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
        csvToBean = (CsvToBean<E>) csvToBeanBuilder.build();
        return csvToBean.iterator();
    }
}
