
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) throws IOException {
        //Paths.get(path to file)
        Stream<String> lines = Files.lines(Paths.get("D:\\acme_worksheet.csv")).skip(1);
        Map<String, Map<LocalDate, Double>> map = groupByNameAndDate(lines);
        FileWriter csvWriter = new FileWriter("new.csv");
        csvWriter.append("Name/Date");
        map.forEach((s, localDateIntegerMap) -> {
            localDateIntegerMap.forEach(((localDate, aDouble) -> {
                try {
                    csvWriter.append(", ").append(String.valueOf(localDate));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }));




        });
        map.forEach((s, localDateIntegerMap) -> {
                    try {
                        csvWriter.append("\n");
                        csvWriter.append(String.join(",", s));
                        localDateIntegerMap.forEach(((localDate, aDouble) -> {
                            try {
                                csvWriter.append(", ").append(String.valueOf(aDouble)).append(" ");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });


        csvWriter.flush();
        csvWriter.close();
    }

    private static Map<String, Map<LocalDate, Double>> groupByNameAndDate(Stream<String> lines) {
        Map<String, Map<LocalDate, Double>> map = new TreeMap<>();
        lines.forEach(s -> {
            String[] parsedParts = s.split(",");
            String name = parsedParts[0];
            Map<LocalDate, Double> localDateIntegerMap = map.get(name);
            if (localDateIntegerMap == null) {
                localDateIntegerMap = new TreeMap<>();
            }
            LocalDate date = LocalDate.parse(parsedParts[1], DateTimeFormatter.ofPattern("MMM dd yyyy", Locale.US));



            localDateIntegerMap.put(date, Double.valueOf(parsedParts[2]));
            map.put(name, localDateIntegerMap);


        });

        return map;
    }
}
