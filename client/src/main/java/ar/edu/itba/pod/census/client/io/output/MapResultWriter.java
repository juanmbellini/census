package ar.edu.itba.pod.census.client.io.output;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Map;

/**
 * Helper class to be used to write the results obtained from a {@link ar.edu.itba.pod.census.client.query.Query}.
 */
public class MapResultWriter {

    /**
     * The {@link Logger}.
     */
    private final static Logger LOGGER = LoggerFactory.getLogger(MapResultWriter.class);

    /**
     * Prints the given {@code results} into the standard output (i.e stdout).
     *
     * @param results The {@link Map} holding the results to be written.
     * @param <K>     The concrete type of the {@code results} keys.
     * @param <V>     The concrete type of the {@code results} values.
     */
    public static <K, V> void printResults(Map<K, V> results) {
        writeResults(results, System.out);
    }

    /**
     * Saves the given {@code results} into a file with the given {@code path}.
     *
     * @param results The {@link Map} holding the results to be written.
     * @param path    The path of the file to which results will be saved.
     * @param <K>     The concrete type of the {@code results} keys.
     * @param <V>     The concrete type of the {@code results} values.
     * @throws IOException If any IO error occurs while operating with the file.
     */
    public static <K, V> void saveResults(Map<K, V> results, String path) throws IOException {
        final PrintStream printStream = generateFilePrintStream(path);
        writeResults(results, printStream);
        printStream.close(); // Close once the file was written
    }

    /**
     * Generates a {@link PrintStream} for a file with the given {@code path}.
     *
     * @param path The path of the file to be written.
     * @return The said {@link PrintStream}.
     */
    private static PrintStream generateFilePrintStream(String path) throws IOException {
        final File file = new File(path);
        final File parent = file.getParentFile();
        // Create directory structure if it not exists
        if (!parent.exists() && !parent.mkdirs()) {
            throw new IOException(); // Throw IOException in case the dir structure could not be created
        }
        if (!file.exists() && !file.createNewFile()) {
            throw new IOException();
        }

        return new PrintStream(new FileOutputStream(file, false)); // Will replace the file
    }


    /**
     * Writes the given {@code results} {@link Map} into the given {@link PrintStream}.
     *
     * @param results The {@link Map} holding the results to be written.
     * @param stream  The {@link PrintStream} to which the {@code results} are going to be written.
     * @param <K>     The concrete type of the {@code results} keys.
     * @param <V>     The concrete type of the {@code results} values.
     */
    private static <K, V> void writeResults(Map<K, V> results, PrintStream stream) {
        results.forEach((k, v) -> stream.println(k + "," + v));
    }
}
