import java.io.CharArrayWriter;
import java.io.IOException;
import java.util.Arrays;

class Converter {
    public static char[] convert(String[] words) throws IOException {

        StringBuilder stringBuilder = new StringBuilder();
        for (String ot : words
        ) {
            stringBuilder.append(ot);
        }
        char[] chars = stringBuilder.toString().toCharArray();
        CharArrayWriter charArrayWriter = new CharArrayWriter();
        charArrayWriter.write(chars);
        return charArrayWriter.toCharArray();

    }
}