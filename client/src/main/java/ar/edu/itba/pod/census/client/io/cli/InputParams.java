package ar.edu.itba.pod.census.client.io.cli;

public class InputParams {
	
	private String name;
  private String pass;
  private String[] addresses;
  private Integer queryID;
  private String inputPath;
  private String outputPath;


  public InputParams(String name, String pass, String[] addresses,  Integer queryID, String inputPath, String outputPath) {
    this.name = name;
    this.pass = pass;
    this.addresses = addresses;
    this.queryID = queryID;
    this.inputPath = inputPath;
    this.outputPath = outputPath;
  }

  public String getName() {
    return name;
  }

  public String getPass() {
    return pass;
  }

  public String[] addresses() {
    return addresses;
  }

  public Integer getQueryID() {
    return queryID;
  }

  public String getInputPath() {
    return inputPath;
  }

  public String getOutputPath() {
    return outputPath;
  }

  @Override
  public String toString() {
    String string = "";
    string += "name: " + name + "\n";
    string += "pass: " + pass + "\n";
    string += "address:" + addresses + "\n";
    string += "query: " + queryID + "\n";
    string += "input path: " + inputPath + "\n";
    string += "output path: " + outputPath + "\n";

    return string;
  }
}
