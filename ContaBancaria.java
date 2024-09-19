import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Scanner;

public class ContaBancaria implements Serializable {
    private static final long serialVersionUID = 1L;
    private String agencia;
    private String numeroConta;
    private double saldo;
    private String nomeCliente;

    public ContaBancaria(String agencia, String numeroConta, double saldo, String nomeCliente) {
        this.agencia = agencia;
        this.numeroConta = numeroConta;
        this.saldo = saldo;
        this.nomeCliente = nomeCliente;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public String getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(String numeroConta) {
        this.numeroConta = numeroConta;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public void salvarEmArquivo() {
        String nomeArquivo = agencia + "-" + numeroConta + ".ser";
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nomeArquivo))) {
            oos.writeObject(this);
            System.out.println("Conta salva: " + nomeArquivo);
        } catch (IOException e) {
            System.out.println("Erro ao salvar a conta: " + e.getMessage());
        }
    }

    public static ContaBancaria carregarDeArquivo(String agencia, String numeroConta) {
        String nomeArquivo = agencia + "-" + numeroConta + ".ser";
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nomeArquivo))) {
            return (ContaBancaria) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao carregar a conta: " + e.getMessage());
            return null;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("BANCO CENTRAL DO BRASIL");
        System.out.println("[1] Salvar conta");
        System.out.println("[2] Carregar conta");
        System.out.print("INSIRA UM VALOR: ");
        int opcao = scanner.nextInt();
        scanner.nextLine(); 
        System.out.println("");

        if (opcao == 1) {
            System.out.print("Nome da Agencia: ");
            String agencia = scanner.nextLine();

            System.out.print("Numero da Conta: ");
            String numeroConta = scanner.nextLine();

            System.out.print("Saldo: ");
            double saldo = scanner.nextDouble();
            scanner.nextLine();

            System.out.print("Digite o nome do cliente: ");
            String nomeCliente = scanner.nextLine();
            System.out.println("");

            ContaBancaria conta = new ContaBancaria(agencia, numeroConta, saldo, nomeCliente);
            conta.salvarEmArquivo();

        } else if (opcao == 2) {
            System.out.print("Nome da Agencia: ");
            String agencia = scanner.nextLine();

            System.out.print("Numero da Conta: ");
            String numeroConta = scanner.nextLine();

            ContaBancaria conta = ContaBancaria.carregarDeArquivo(agencia, numeroConta);

            if (conta != null) {
                System.out.println("");
                System.out.println("Conta carregada com sucesso!");
                System.out.println("Numero da Agência: " + conta.getAgencia());
                System.out.println("Numero da Conta: " + conta.getNumeroConta());
                System.out.println("Saldo: " + conta.getSaldo());
                System.out.println("Nome do Cliente: " + conta.getNomeCliente());
            }

        } else {
            System.out.println("Opção inválida.");
        }

        scanner.close();
    }
}
