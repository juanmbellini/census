package ar.edu.itba.pod.census.client.io;

import org.jeasy.props.PropertiesInjectorBuilder;
import org.jeasy.props.annotations.SystemProperty;

import java.util.Arrays;
import java.util.Optional;

/**
 * Container class that holds program arguments.
 */
public class InputParams {

    // =======================================
    // Cluster arguments
    // =======================================
    @SystemProperty(value = "addresses")
    private String[] addresses;

    // =======================================
    // Files arguments
    // =======================================
    @SystemProperty(value = "inPath", defaultValue = "census.csv")
    private String dataFilePath;

    @SystemProperty(value = "outPath", defaultValue = "output.txt")
    private String outputFilePath;

    @SystemProperty(value = "timeOutPath", defaultValue = "time.txt")
    private String timestampsFilePath;

    // =======================================
    // Execution arguments
    // =======================================
    @SystemProperty(value = "query", defaultValue = "1")
    private int queryId;

    // =======================================
    // Query param arguments
    // =======================================

    @SystemProperty(value = "n", defaultValue = "10")
    private Integer n;

    @SystemProperty(value = "prov", defaultValue = "Santa Fe")
    private String prov;


    /**
     * Default constructor that uses JEasy to initialize variables.
     */
    public InputParams() {
        PropertiesInjectorBuilder.aNewPropertiesInjector().injectProperties(this);
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


    // ================================================
    // Setters for JEasy
    // ================================================

    /**
     * @param addresses Sets the addresses.
     */
    public void setAddresses(String[] addresses) {
        this.addresses = addresses;
    }

    /**
     * @param dataFilePath Sets the data file path.
     */
    public void setDataFilePath(String dataFilePath) {
        this.dataFilePath = dataFilePath;
    }

    /**
     * @param outputFilePath Sets the output file path.
     */
    public void setOutputFilePath(String outputFilePath) {
        this.outputFilePath = outputFilePath;
    }

    /**
     * @param timestampsFilePath Sets the timestamps output file path.
     */
    public void setTimestampsFilePath(String timestampsFilePath) {
        this.timestampsFilePath = timestampsFilePath;
    }

    /**
     * @param queryId Sets the query id.
     */
    public void setQueryId(int queryId) {
        this.queryId = queryId;
    }

    /**
     * @param n Sets the n query param.
     */
    public void setN(Integer n) {
        this.n = n;
    }

    /**
     * @param prov Sets the prov query param.
     */
    public void setProv(String prov) {
        this.prov = prov;
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
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
