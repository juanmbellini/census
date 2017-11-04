package ar.edu.itba.pod.census.client.file;

import com.hazelcast.core.IMap;

import ar.edu.itba.pod.census.api.models.Citizen;

import static java.lang.System.exit;

public class NaiveReader {
	private String _inputFile;

  public NaiveReader(String inputFile) {
    _inputFile = inputFile;
  }

  public void loadData(final IMap<String, Citizen> iMap) {
    try {
      CensusReader.readCitizens(_inputFile, iMap);
    } catch (Exception e) {
      System.out.println("File or directory doesn't exist");
      exit(1);
    }
  }

  public String getInputFile() {
    return _inputFile;
  }
}
