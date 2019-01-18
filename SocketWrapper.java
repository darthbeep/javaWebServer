import java.net.*;
import java.io.*;

public class SocketWrapper {

  int port;
  private ServerSocket serverSocket;
  private Socket response;

  public SocketWrapper(int p) {
    port = p;
  }

  public void setupSocket() {
    try {
      serverSocket = new ServerSocket(port);
      response = serverSocket.accept();
    }
    catch(IOException e) {
      System.out.println("something went wrong");
    }
  }

  public void interperateSocket() {
    try {
      PrintWriter out = new PrintWriter(response.getOutputStream(), true);
      BufferedReader in = new BufferedReader(new InputStreamReader(response.getInputStream()));

      String inputLine;
      while ((inputLine = in.readLine()) != null && inputLine.length()>3) {
        System.out.println(inputLine);
        inputLine = "HTTP/1.0 200 Success\r\n\r\n" + inputLine;
        out.println(inputLine + "!");
      }
    }
    catch(IOException e) {
      System.out.println("something went wrong");
    }
  }


}
