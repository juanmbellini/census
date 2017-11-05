package ar.edu.itba.pod.census.client.io.input;

import ar.edu.itba.pod.census.api.models.Citizen;
import com.hazelcast.core.IMap;
import org.supercsv.cellprocessor.ParseInt;
import org.supercsv.cellprocessor.ParseLong;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/* package */ class CensusReader {
    private static final String FILENAME = "files/census1000000.csv";

    private static CellProcessor[] getProcessors() {
        return new CellProcessor[]{
                new ParseInt(new NotNull()),    // activityConditionId
                new ParseLong(new NotNull()),   // homeId
                new NotNull(),                  // departmentName
                new NotNull()                   // provinceName
        };
    }

    /* package */
    static void readCitizens(final String fileName, final IMap<Integer, Citizen> theIMap)
            throws IOException {
        ICsvBeanReader beanReader = null;
        try {
            final InputStream is = CensusReader.class.getClassLoader().getResourceAsStream(fileName);
            final Reader aReader = new InputStreamReader(is);
            beanReader = new CsvBeanReader(aReader, CsvPreference.STANDARD_PREFERENCE);

            beanReader.getHeader(true);

            final String[] header = new String[]{"condicionActividad", "hogarId", "nombreDepartamento", "nombreProvincia"};

            final CellProcessor[] processors = getProcessors();

            Citizen citizen;
            while ((citizen = beanReader.read(Citizen.class, header, processors)) != null) {
                theIMap.set(beanReader.getLineNumber(), citizen);
            }
        } finally {
            if (beanReader != null) {
                beanReader.close();
            }
        }
    }
}
