// You can experiment here, it won�t be checked

import java.io.ByteArrayOutputStream;

public class Task {
  public static void main(String[] args) {
    // put your code here
    int[] message = new int[] {114, 101, 97, 100, 32, 97, 98, 111, 117, 116, 32, 65, 83, 67, 73, 73};

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    for (int code : message) {
      outputStream.write(code);
    }

    System.out.println(outputStream.toString());
  }
}
