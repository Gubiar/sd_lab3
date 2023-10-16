import java.io.Serializable;

public class Mensagem implements Serializable {
    
    private String mensagem;
    private String opcao;

    // Cliente -> Servidor
    public Mensagem(String mensagem, String opcao) {
        setMensagem(mensagem, opcao);
    }

    // Servidor -> Cliente
    public Mensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return this.mensagem;
    }

    public String getOpcao() {
        return this.opcao;
    }

    public void setMensagem(String fortune, String opcao) {
        this.opcao = opcao; // Define a opção
        String mensagem = "";

        switch (opcao) {
            case "1": {
                mensagem += "MENSAGEM DE LEITURA JSON NÃO PRONTA.";
                break;
            }
            case "2": {
                mensagem += "MENSAGEM DE ESCRITA JSON NÃO PRONTA.";
                break;
            }
        }
        this.mensagem = mensagem;
    }
}
