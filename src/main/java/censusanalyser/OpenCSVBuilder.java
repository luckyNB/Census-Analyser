package censusanalyser;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.Reader;
import java.util.Iterator;

public class OpenCSVBuilder<E> implements ICSVBuilder {
    @Override
    public <E> Iterator<E> getCSVFileIterator(Reader reader, Class csvClass) throws CSVBuilderException {
        try {
            CsvToBean<E> csvToBean = null;
            CsvToBeanBuilder<IndiaCensusCSV> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
            csvToBeanBuilder.withType((Class<? extends IndiaCensusCSV>) csvClass);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            csvToBean = (CsvToBean<E>) csvToBeanBuilder.build();
            return csvToBean.iterator();
        } catch (IllegalStateException e) {
            throw new CSVBuilderException("", CSVBuilderException.ExceptionType.UNABLE_TO_PARSE);
        }
    }
}
