import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

import Classes.*;

public class App {
    // atributos static s�o atributos de classe
	private static ArrayList<Carro> vagas = new ArrayList<Carro>(); // o estacionamento tem 100 vagas numeradas de 0..99
	private static ArrayList<Marca> marcas = new ArrayList<Marca>();
	private static ArrayList<Modelo> modelos = new ArrayList<Modelo>();
	private static ArrayList<Carro> historico = new ArrayList<Carro>();
	
	// eventualmente outros atributos static
	
	public static void main(String[] args) {
		// outras variaveis locais
		// menu

        printString("Seja bem vindo!");
        int menu;
        Scanner scan = new Scanner(System.in);
        do {
            printString("\nEstacionamento - Menu");
            printString("1) Registrar chegada de carro");
            printString("2) Registrar saída de carro");
            printString("3) Gerar relatório");
            printString("4) Sair do sistema");
            printString("");
            printString("Insira um número para escolher uma ação:");
            menu = scan.nextInt();

            switch (menu) {
                case 1:
                    entradaCarro(scan);
                    break;
                case 2:
                    saidaCarro();
                    break;
                case 3:
                    gerarRelatorio();
                    break;
                case 4:
                    printString("Obrigado por utilizar! Até logo.");
                    break;
            
                default:
                    printString("Comando inválido, por favor, tente novamente.");
                    break;
            }
        } while (menu != 4);
		// opcao
		// chamar metodos static que correspondam as opcoes de menu
	}
	
	private static void entradaCarro(Scanner scan) {
		// criar o carro e cadastra-lo no vetor na posicao correta
        printString("Estacionar");
        
        Marca marca = adicionarMarca(scan);
        Modelo modelo = adicionarModelo(scan);

        printString("\nInsira a placa do carro: ");
        String placa = scan.nextLine();
        
        if (modelo == null || marca == null) {
            printString("Erro ao definir modelo e/ou marca.");
            return;
        }
        Carro carro = new Carro(placa, modelo, LocalDateTime.now());
        vagas.add(carro);
	}
	
	private static float saidaCarro() {
        printString("Sair");
		float preco = 0;
		// logica para calcular preco do estacionamento e coloca-lo no historico
		return preco;
	}

    private static void gerarRelatorio() {
        printString("Relatório");
    }
	
	// outros m�todos static conforme especificacao do trabalho e necessidades de implementacao
    private static void printString(String str) {
        System.out.println(str);
    }
    private static Marca adicionarMarca (Scanner scan) {
                
        int menuMarca = 0;
        Marca marca = null;
        boolean marcaOk = false;
        boolean novaMarca = false;

        do {
            printString("\nMarcas Disponíveis");
            printString("0) Adicionar nova marca");
            // marcas.display();
            printString("\nSelecione uma marca: ");
            menuMarca = scan.nextInt();
            try {
                if (menuMarca == 0) {
                    printString("\nAdicionar nova marca\n");
                    marcaOk = true;
                    novaMarca = true;
                } else if (menuMarca > 0) {
                    marca = marcas.get(menuMarca-1);
                    marcaOk = true;
                } else {
                    printString("Insira um número válido.");
                }
                
            } catch (Exception e) {
                printString("Pedido inválido. selecione outro número");
            }
        } while (!marcaOk);

        if (novaMarca) {
            printString("\nInsira o nome da marca: ");
            String nomeMarca = scan.nextLine();
            marca = new Marca(nomeMarca);
            marcas.add(marca);
        }
        return marca;
    }
    private static Modelo adicionarModelo(Scanner scan) {
        int menuModelo = 0;
        Modelo modelo = null;
        boolean modeloOk = false;
        boolean novoModelo = false;
        
        
        do {
            printString("/nModelos Disponíveis");
            printString("0) Adicionar novo modelo");
            // modelos.display();
            printString("/nSelecione uma marca: ");
            menuModelo = scan.nextInt();
            try {
                if (menuModelo == 0) {
                    printString("\nAdicionar nova marca\n");
                    modeloOk = true;
                    novoModelo = true;
                } else if (menuModelo > 0) {
                    modelo = modelos.get(menuModelo-1);
                    modeloOk = true;
                } else {
                    printString("Insira um número válido.");
                }
                
            } catch (Exception e) {
                printString("Pedido inválido. selecione outro número");
            }
        } while (modeloOk);

        if (novoModelo) {
            printString("\nInsira o nome do modelo: ");
            String nomeModelo = scan.nextLine();
            modelo = new Modelo(nomeModelo);
            modelos.add(modelo);
        }
        return modelo;
    }
}
