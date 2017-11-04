package ar.edu.itba.pod.census.client.file;

public class CensusReader {
	private static final String FILENAME = "files/census1000000.csv";

    private static CellProcessor[] getProcessors() {
        return new CellProcessor[] {
            new ParseInt(new NotNull()), // condicionActividad
            new ParseInt(new NotNull()), // hogarId
            new NotNull(), // nombreDepartamento
            new NotNull() // nombreProvincia
        };
    }

    public static void readCitizens(final String fileName, final IMap<String, Citizen> theIMap) throws Exception {
        ICsvBeanReader beanReader = null;
        try {
            final InputStream is = VotacionReader.class.getClassLoader().getResourceAsStream(fileName);
            final Reader aReader = new InputStreamReader(is);
            beanReader = new CsvBeanReader(aReader, CsvPreference.STANDARD_PREFERENCE);
            
            beanReader.getHeader(true);

            final String[] header = new String[] {
                "condicionActividad",
                "hogarId",
                "nombreDepartamento",
                "nombreProvincia"
            };



            final CellProcessor[] processors = getProcessors();

            Citizen citizen;
            while ((citizen = beanReader.read(Citizen.class, header, processors)) != null) {
                theIMap.set(String.valueOf(beanReader.getLineNumber()), citizen);
            }
        } finally {
            if (beanReader != null) {
                beanReader.close();
            }
        }
    }
}