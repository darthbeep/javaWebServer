import java.net.*;
import java.io.*;

public class SocketWrapper {

  int port;

  private ServerSocket serverSocket;


  public SocketWrapper(int p) {
    port = p;
  }

  public void setupSocket() {
    try {
      serverSocket = new ServerSocket(port);
      Socket clientSocket = serverSocket.accept();
      System.out.println(clientSocket.getOutputStream());
    }
    catch(IOException e) {
      System.out.println("yep");
    }
  }
}
