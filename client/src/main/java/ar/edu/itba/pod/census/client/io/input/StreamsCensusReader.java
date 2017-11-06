package ar.edu.itba.pod.census.client.io.input;

import ar.edu.itba.pod.census.api.models.Citizen;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Data readed using Java 8 Streams.
 */
public class StreamsCensusReader {

    /**
     * Reads a file containing citizens data.
     *
     * @param fileName The path to the file containing the data.
     * @return A {@link List} of {@link Citizen} taken from the data file.
     * @throws IOException If any IO error occurs while reading the file.
     */
    public static List<Citizen> readCitizens(final String fileName) throws IOException {

        // Using Stream.of(Object) encapsulates the File.lines() stream in a closable Stream
        return Stream.of(Files.lines(Paths.get(fileName)))
                .flatMap(Function.identity())
                .map(csvLine -> csvLine.split(","))
                .map(values -> new Citizen(Integer.valueOf(values[0]), Long.valueOf(values[1]), values[2], values[3]))
                .collect(Collectors.toList());
    }
}
