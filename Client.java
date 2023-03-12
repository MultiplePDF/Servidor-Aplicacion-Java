import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket socketClient = new Socket("localhost",6666);
        System.out.println("Conexión establecida con el cliente, escriba su mensaje a continuación");
        System.out.println("Si quiere salir de la comunicación escriba 'x'");
        Scanner scan = new Scanner(System.in);

        DataInputStream dis = new DataInputStream(socketClient.getInputStream());
        DataOutputStream dout = new DataOutputStream(socketClient.getOutputStream());

        String msgSalida = "";
        String msgEntrada = "";

        while(!msgSalida.equals("x")){
            msgSalida = scan.nextLine();
            dout.writeUTF(msgSalida);
            dout.flush();

            msgEntrada = dis.readUTF();
            System.out.println("El servidor dice: "+ msgEntrada);
        }

        dout.close();
        socketClient.close();
    }
}
