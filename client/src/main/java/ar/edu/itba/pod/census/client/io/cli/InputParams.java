package ar.edu.itba.pod.census.client.io.cli;

import org.jeasy.props.PropertiesInjectorBuilder;
import org.jeasy.props.annotations.SystemProperty;

import java.util.Arrays;
import java.util.Optional;

/**
 * Container class that holds program arguments.
 */
public class InputParams {

    private String name;
    private String pass;


    // =======================================
    // Cluster arguments
    // =======================================
    @SystemProperty(value = "addresses")
    private String[] addresses;

    // =======================================
    // Files arguments
    // =======================================
    @SystemProperty(value = "inPath", defaultValue = "census.csv")
    private String dataFilePath = "census.csv";

    @SystemProperty(value = "outPath", defaultValue = "output.txt")
    private String outputFilePath = "output.txt";

    @SystemProperty(value = "timeOutPath", defaultValue = "time.txt")
    private String timestampsFilePath = "time.txt";

    // =======================================
    // Execution arguments
    // =======================================
    @SystemProperty(value = "query")
    private int queryId;

    // =======================================
    // Query param arguments
    // =======================================

    @SystemProperty(value = "n")
    private Integer n;

    @SystemProperty(value = "prov")
    private String prov;


    /**
     * Default constructor that uses JEasy to initialize variables.
     */
    public InputParams() {
        PropertiesInjectorBuilder.aNewPropertiesInjector().injectProperties(this);

        // TODO: validate arguments
    }

    public String getName() {
        return name;
    }

    public String getPass() {
        return pass;
    }


    /**
     * @return The list of member node ip addresses.
     */
    public String[] getAddresses() {
        return addresses;
    }

    /**
     * @return The path of the data file to be processed.
     */
    public String getDataFilePath() {
        return dataFilePath;
    }

    /**
     * @return The path to the output file.
     */
    public String getOutputFilePath() {
        return outputFilePath;
    }

    /**
     * @return The path to the timestamps file.
     */
    public String getTimestampsFilePath() {
        return timestampsFilePath;
    }

    /**
     * @return The id of the query being executed.
     */
    public int getQueryId() {
        return queryId;
    }

    /**
     * @return The 'n' query param.
     */
    public Integer getN() {
        return n;
    }

    /**
     * @return The 'prov' query param.
     */
    public String getProv() {
        return prov;
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("name: ").append(name).append("\n");
        stringBuilder.append("pass: ").append(pass).append("\n");
        stringBuilder.append("addresses: ").append(Arrays.toString(addresses)).append("\n");
        stringBuilder.append("data file path: ").append(dataFilePath).append("\n");
        stringBuilder.append("output path: ").append(outputFilePath).append("\n");
        stringBuilder.append("timestamps output path: ").append(timestampsFilePath).append("\n");
        stringBuilder.append("queryId: ").append(queryId).append("\n");
        Optional.ofNullable(this.n)
                .ifPresent(value -> stringBuilder.append("Query param n: ").append(value).append("\n"));
        Optional.ofNullable(this.prov)
                .ifPresent(value -> stringBuilder.append("Query param prov: ").append(value).append("\n"));

        return stringBuilder.toString();
    }
}
