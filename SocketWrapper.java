import java.net.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class SocketWrapper {

  int port;
  private ServerSocket serverSocket;
  private Socket response;

  PrintWriter out;
  BufferedReader in;

  String header;
  String filestart="public_html";

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
        out.println("HTTP/1.0 400 Bad Request\r\n\r\n<html><body><h1>400 Bad Request</h1></body></html>");
      }
      else {

        try {
          Path fp = Paths.get(convertToPath(args[1]));
          Scanner s = new Scanner(fp);
          String contents = "";
          while (s.hasNextLine()) {
            contents += s.nextLine()+"\r\n";
          }
          System.out.println(contents);
          out.println("HTTP/1.0 200 OK\r\nhi\r\n");
        //  System.out.println(contents);
          out.print(contents);
          s.close();
        } catch(FileNotFoundException e) {
          out.println("HTTP/1.0 404 Not Found\r\n\r\n<html><body><h1>404 Not Found</h1></body></html>");
        }
        catch (Exception e) {
        }
      }
      out.close();
  }

  public String convertToPath(String givenPath) throws IOException {
    String ret = filestart + givenPath;
    int len = ret.length();
    if (ret.substring(len-1).equals("/")) {
      ret = ret.substring(0, len-1);
    }
    File exist = new File(ret);
    if (exist.exists() && Files.probeContentType(exist.toPath()) == null) {
      ret+="/index.html";
    }
    return ret;
  }

}
