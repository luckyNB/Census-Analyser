package censusanalyser;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.Reader;
import java.util.Iterator;
import java.util.List;

public class OpenCSVBuilder<E> implements ICSVBuilder {
    @Override
    public <E> Iterator<E> getCSVFileIterator(Reader reader, Class csvClass) throws CSVBuilderException {
        try {
            CsvToBean<E> csvToBean = null;

            csvToBean = (CsvToBean<E>) this.getCSVBean(reader, csvClass);
            return csvToBean.iterator();
        } catch (IllegalStateException e) {
            throw new CSVBuilderException("", CSVBuilderException.ExceptionType.UNABLE_TO_PARSE);
        }
    }

    @Override
    public <E> List<E> getCSVFileList(Reader reader, Class csvClass) throws CSVBuilderException {

       return (List<E>) this.getCSVBean(reader, csvClass).parse();

    }

    private CsvToBean<E> getCSVBean(Reader reader, Class csvClass) {
        {
            CsvToBean<E> csvToBean = null;
            CsvToBeanBuilder<IndiaCensusCSV> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
            csvToBeanBuilder.withType((Class<? extends IndiaCensusCSV>) csvClass);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            csvToBean = (CsvToBean<E>) csvToBeanBuilder.build();
            return csvToBean;

        }
    }
}
