package ar.edu.itba.pod.census.client.io.input;

import ar.edu.itba.pod.census.api.models.Citizen;
import com.hazelcast.core.IMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Naive reader.
 */
public class NaiveReader {

    private final static Logger LOGGER = LoggerFactory.getLogger(NaiveReader.class);

    /**
     * The path to the input file.
     */
    private final String inputFile;

    /**
     * Constructor.
     *
     * @param inputFile The path to the input file.
     */
    public NaiveReader(String inputFile) {
        this.inputFile = inputFile;
    }

    /**
     * Loads data into the given {@link IMap}.
     *
     * @param iMap The {@link IMap} to which data must be loaded into.
     */
    public void loadData(final IMap<Integer, Citizen> iMap) throws IOException {
        try {
            CensusReader.readCitizens(inputFile, iMap);
            CensusReader.readCitizens(inputFile, iMap);
        } finally {
            LOGGER.error("File or directory doesn't exist");
        }
    }

    /**
     * @return The path to the input file.
     */
    public String getInputFile() {
        return inputFile;
    }
}
