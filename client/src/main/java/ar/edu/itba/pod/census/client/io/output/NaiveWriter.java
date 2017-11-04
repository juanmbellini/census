package ar.edu.itba.pod.census.client.io.output;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

import org.slf4j.Logger;

public class NaiveWriter {
	private String _fileName;
  private Logger _logger;

  public NaiveWriter(String fileName, Logger logger) {
    _fileName = fileName;
    _logger = logger;
  }

  public void write(List<String> lines) {
    Path path = Paths.get(_fileName);
    try {
      Files.write(path, lines, Charset.forName("UTF-8"), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    } catch (IOException e) {
      _logger.error("couldn't create output file " + _fileName);
    }
  }
}
