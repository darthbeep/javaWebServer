import java.net.*;
import java.io.*;

public class Main {
  public static void main(String[] args) {
    SocketWrapper s = new SocketWrapper(8000);
    while (true) {
      s.setupSocket();
      s.interperateSocket();
    }

  }
}
