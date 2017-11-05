package ar.edu.itba.pod.census.client.io.output;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

/**
 * Naive writer.
 */
public class NaiveWriter {
    private static final Logger LOGGER = LoggerFactory.getLogger(NaiveWriter.class);

    /**
     * The path to the output file.
     */
    private final String fileName;

    /**
     * Constructor
     *
     * @param fileName The path to the output file.
     */
    public NaiveWriter(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Writes the given {@code lines}into the file.
     *
     * @param lines The lines to be written.
     */
    public void write(List<String> lines) {
        final Path path = Paths.get(this.fileName);
        try {
            Files.write(path, lines, Charset.forName("UTF-8"),
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            LOGGER.error("couldn't create output file " + fileName);
        }
    }
}
