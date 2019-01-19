import java.net.*;
import java.io.*;

public class SocketWrapper {

  int port;
  private ServerSocket serverSocket;
  private Socket response;

  PrintWriter out;
  BufferedReader in;

  String header;

  public SocketWrapper(int p) {
    port = p;
    try {
      serverSocket = new ServerSocket(port);
    }
    catch (IOException e){}
  }

  public void setupSocket() {
    try {
      response = serverSocket.accept();
      out = new PrintWriter(response.getOutputStream(), true);
      in = new BufferedReader(new InputStreamReader(response.getInputStream()));
      header = in.readLine();
    }
    catch(IOException e) {
      System.out.println("something went wrong");
    }
  }

  public void interperateSocket() {
      String args[] = header.split("\\s+");
      if (!args[0].equals("GET")) {
        out.println("HTTP/1.0 501 Not Implemented\r\n\r\n<html><body><h1>501 Not Implemented</h1></body></html>");
      }
      else if (!args[2].equals("HTTP/1.0") && !args[2].equals("HTTP/1.1")) {
        out.println("HTTP/1.0 501 Not Implemented\r\n\r\n<html><body><h1>501 Not Implemented</h1></body></html>");
      }
      else if (!args[1].subSequence(0, 1).equals("/")) {
        out.println("HTTP/1.0 400 Bad Request\r\n\r\n<html><body><h1>501 Bad Request</h1></body></html>");
      }
      else {
        out.println("HTTP/1.0 200 OK\r\n\r\n");
        out.println("This is a webpage");
      }
      out.close();
      for (String a : args) {
        System.out.println(a);
      }
  }


}
