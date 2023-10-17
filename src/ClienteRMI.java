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
			
			Boolean isRunning = true;
			while (isRunning) {
				System.out.println("1) Read");
				System.out.println("2) Write");
				System.out.println("3) Exit");
				System.out.print(">> ");
				opcao = leitura.next();
				

				switch(opcao) {
					case "1": {
						leitura.nextLine(); //Consome o input anteiror (limpa o que o usuário digitou)
						Mensagem mensagem = new Mensagem("", "read");
						System.out.println(mensagem.getMensagem());
						Mensagem resposta = stub.enviar(mensagem); //Envio para o servidor
						System.out.println("Resultado:");
						System.out.println(resposta.getMensagem());
						
						break;
					}
					case "2": {
						leitura.nextLine(); //Consome o input anteiror (limpa o que o usuário digitou)
						System.out.print("Add fortune: ");
						String fortune = leitura.nextLine();
						Mensagem mensagem = new Mensagem(fortune, "write");
						System.out.println(mensagem.getMensagem());
						
						Mensagem resposta = stub.enviar(mensagem); //Envio para o servidor
						
						System.out.println(resposta.getMensagem());
						
						break;
					}
					case "3": {
						System.out.print("Processo finalizado!\n");
						isRunning = false;
						break;
					}
					default: {
						System.out.print("Digite uma opção válida!\n");
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
