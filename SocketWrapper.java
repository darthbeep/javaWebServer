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
      //System.out.println(clientSocket.getOutputStream());
      interperateSocket(clientSocket);
    }
    catch(IOException e) {
      System.out.println("yep");
    }
  }

  private void interperateSocket(Socket clientSocket) {
    try {
      PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
      BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

      String inputLine;
      while ((inputLine = in.readLine()) != null) {
        out.println(inputLine);
      }
    }
    catch(IOException e) {
      System.out.println("idk");
    }
  }


}
