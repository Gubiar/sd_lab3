import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;


public class ServidorImpl implements IMensagem {

    private Principal principal;

    public ServidorImpl() {
        principal = new Principal(); // Crie uma instância de Principal
    }

    @Override
    public Mensagem enviar(Mensagem mensagem) throws RemoteException {
        Mensagem resposta;
        try {
            System.out.println("Mensagem recebida: " + mensagem.getMensagem());

            if (mensagem.getOpcao().equals("read")) {
                String fortune = principal.read();
                resposta = new Mensagem(fortune);
            } else if (mensagem.getOpcao().equals("write")) {
                String fortune = parse(mensagem.getMensagem());
                principal.write(fortune);
                resposta = new Mensagem("Fortune added: " + mensagem.getMensagem());
            } else {
                resposta = new Mensagem("{\"result\": false}");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resposta = new Mensagem("{\"result\": false}");
        }
        return resposta; //Retorno para o cliente
    }

    public String readFortune() {
        return principal.read();
    }

    public void writeFortune(String fortune) {
        principal.write(fortune);
    }

    public void iniciar() {
        try {
            Registry servidorRegistro = LocateRegistry.createRegistry(1099);
            IMensagem skeleton  = (IMensagem) UnicastRemoteObject.exportObject(this, 0);
            servidorRegistro.rebind("servidorFortunes", skeleton);
            System.out.println("Servidor RMI: Aguardando conexões...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static HashMap<String, String> parseJSONToMap(String jsonString) {
        HashMap<String, String> resultMap = new HashMap<String, String>();

        jsonString = jsonString.replaceAll("[{}\"]", "");
        String[] keyValuePairs = jsonString.split(",");

        for (String pair : keyValuePairs) {
            String[] entry = pair.split(":", 2);
            String key = entry[0].trim();
            String value = entry[1].trim();

            if (value.startsWith("[")) {
                // Handle JSON arrays
                value = value.substring(1, value.length() - 1); // Remove square brackets
                String arrayValues = value;
                resultMap.put(key, arrayValues);
            } else {
                resultMap.put(key, value);
            }
        }

        return resultMap;
    }

    public String parse(String mensagem) throws RemoteException {
        HashMap<String, String> map = parseJSONToMap(mensagem);
        String msg = map.get("args");
        return msg;
    }

    public static void main(String[] args) {
        ServidorImpl servidor = new ServidorImpl();
        servidor.iniciar();
    }
}