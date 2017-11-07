package ar.edu.itba.pod.census.client.io.output;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.AbstractMap;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * Helper class to be used to write the results obtained from a {@link ar.edu.itba.pod.census.client.query.Query}.
 */
public class OutputWriter {

    /**
     * The {@link Logger}.
     */
    private final static Logger LOGGER = LoggerFactory.getLogger(OutputWriter.class);

    /**
     * Prints the given {@code results} into the standard output (i.e stdout).
     *
     * @param results The {@link Map} holding the results to be written.
     * @param <K>     The concrete type of the {@code results} keys.
     * @param <V>     The concrete type of the {@code results} values.
     */
    public static <K, V> void printResults(Map<K, V> results) {
        printElement(results, OutputWriter::writeResults);
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
        saveElement(results, path, OutputWriter::writeResults);
    }

    /**
     * Prints the given {@code timestamps} into the standard output (i.e stdout).
     *
     * @param timestamps The {@link Map} holding the timestamps to be printed.
     */
    public static void printTimestamps(Map<LocalDateTime, String> timestamps) {
        printElement(timestamps, OutputWriter::writeTimestamps);
    }

    /**
     * Saves the given {@code timestamps} into a file with the given {@code path}.
     *
     * @param timestamps The {@link Map} holding the timestamps to be saved.
     * @param path       The path of the file to which timestamps will be saved.
     * @throws IOException If any IO error occurs while operating with the file.
     */
    public static void saveTimestamps(Map<LocalDateTime, String> timestamps, String path) throws IOException {
        saveElement(timestamps, path, OutputWriter::writeTimestamps);
    }


    /**
     * Prints the given {@code printable} according to the given {@code writingProcedure}.
     *
     * @param printable        The container object that holds the stuff to be printed.
     * @param writingProcedure The procedure that performs writing of stuff.
     * @param <T>              The concrete type of the {@code results} keys.
     */
    private static <T> void printElement(T printable, BiConsumer<T, PrintStream> writingProcedure) {
        writeElement(printable, System.out, writingProcedure);
    }

    /**
     * Sves the given {@code savable} according to the given {@code writingProcedure}.
     *
     * @param savable          The container object that holds the stuff to be saved.
     * @param path             The path of the file to which results will be saved.
     * @param writingProcedure The procedure that performs writing of stuff.
     * @param <T>              The concrete type of the {@code results} keys.
     */
    private static <T> void saveElement(T savable, String path, BiConsumer<T, PrintStream> writingProcedure)
            throws IOException {
        final PrintStream printStream = generateFilePrintStream(path);
        writeElement(savable, printStream, writingProcedure);
        printStream.close(); // Close once the file was written
    }

    /**
     * Writes the given {@code printable} according to the given {@code writingProcedure}.
     *
     * @param printable        The container object that holds the stuff to be written.
     * @param printStream      The {@link PrintStream} to which elements will be written into.
     * @param writingProcedure The procedure that performs writing of stuff.
     * @param <T>              The concrete type of the {@code results} keys.
     */
    private static <T> void writeElement(T printable, PrintStream printStream, BiConsumer<T, PrintStream> writingProcedure) {
        writingProcedure.accept(printable, printStream);
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
        if (parent != null && !parent.exists() && !parent.mkdirs()) {
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

    /**
     * Writes the given {@code timestamps} {@link Map} into the given {@link PrintStream}.
     * The key of the {@code timestamps} {@link Map} must hold the {@link LocalDateTime} representing timestamps.
     * The value of the {@code timestamps} represents a description of the timestamp.
     *
     * @param timestamps The {@link Map} holding the timestamps to be written.
     * @param stream     The {@link PrintStream} to which the {@code timestamps} are going to be written.
     */
    private static void writeTimestamps(Map<LocalDateTime, String> timestamps, PrintStream stream) {
        final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss:SSS");
        timestamps.entrySet().stream()
                .map(entry -> new AbstractMap.SimpleEntry<>(entry.getKey().format(dateTimeFormatter), entry.getValue()))
                .forEach(entry -> stream.println(entry.getKey() + " - " + entry.getValue()));
    }
}
