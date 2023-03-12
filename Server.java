import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(6666);
        Socket socketServer = server.accept();
        System.out.println("Conexión establecida con el servidor, escriba su mensaje a continuación");
        System.out.println("Si quiere salir de la comunicación escriba 'x'");
        Scanner scan = new Scanner(System.in);

        DataInputStream dis=new DataInputStream(socketServer.getInputStream());
        DataOutputStream dout=new DataOutputStream(socketServer.getOutputStream());

        String msgSalida = "";
        String msgEntrada = "";
        while(!msgEntrada.equals("x")){
            msgEntrada = dis.readUTF();
            System.out.println("El cliente dice: "+msgEntrada);

            msgSalida= scan.nextLine();
            dout.writeUTF(msgSalida);
            dout.flush();
        }
        dis.close();
        socketServer.close();
        server.close();

    }
}
