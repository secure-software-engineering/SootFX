package resource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileConnector {

    public static List<String> getMethodSignatures(String path){
        List<String> result = null;
        try (Stream<String> lines = Files.lines(Paths.get(path))) {
            result = lines.map(l->l.trim()).collect(Collectors.toList());
        }catch (IOException e){
            e.printStackTrace();
        }
        return result;
    }

}
