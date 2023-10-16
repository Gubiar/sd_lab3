import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class ClienteRMI {

	public static void main(String[] args) {

		try {
			Registry registro = LocateRegistry.getRegistry("127.0.0.1", 1099);
			IMensagem stub = (IMensagem) registro.lookup("servidorFortunes");

			String opcao = "";
			Scanner leitura = new Scanner(System.in);

			do {
				System.out.println("1) Read");
				System.out.println("2) Write");
				System.out.println("x) Exit");
				System.out.print(">> ");
				opcao = leitura.next();

				switch(opcao) {
					case "1": {
						Mensagem mensagem = new Mensagem("", "read");
						Mensagem resposta = stub.enviar(mensagem);
						System.out.println(resposta.getMensagem());
						break;
					}
					case "2": {
						System.out.print("Add fortune: ");
						String fortune = leitura.next();
						Mensagem mensagem = new Mensagem(fortune, "write");
						Mensagem resposta = stub.enviar(mensagem);
						System.out.println(resposta.getMensagem());
						break;
					}
				}
			} while (!opcao.equals("x"));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
