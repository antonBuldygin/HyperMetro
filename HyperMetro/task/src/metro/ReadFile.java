package metro;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static java.nio.charset.StandardCharsets.*;
import static java.util.regex.Pattern.*;

public class ReadFile {

    public static StringBuilder toImport(String name) throws FileNotFoundException {
        String path = name;
        StringBuilder str = new StringBuilder();
        List<String> list = new LinkedList<>();
        File file = new File(path);

//        FileInputStream fis = new FileInputStream(path);
//        InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
//        System.out.println(file.getName() + " " + file.exists() + " " + file.isFile() + " " + file.getAbsolutePath() + " " + file.getParent());
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String line = null;

            while ((line = br.readLine()) != null) {
                str.append(decodeText(line, "UTF-8"));
            }
        } catch (FileNotFoundException e) {
//            e.printStackTrace();
            System.out.println("Error! Such a file doesn't exist!");
        }
        catch (IOException e) {
            e.printStackTrace();
        } finally {

            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                }
            }
        }
        return str;
    }

    static String decodeText(String input, String encoding) throws IOException {
        return
                new BufferedReader(
                        new InputStreamReader(
                                new ByteArrayInputStream(input.getBytes()),
                                Charset.forName(encoding)))
                        .readLine();
    }
}