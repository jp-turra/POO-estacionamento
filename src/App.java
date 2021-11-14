import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

import Classes.*;
import Utils.Utils;

public class App {
    // atributos static são atributos de classe
	private static ArrayList<Carro> vagas = new ArrayList<Carro>(); // o estacionamento tem 100 vagas numeradas de 0..99
	private static ArrayList<Marca> marcas = new ArrayList<Marca>();
	private static ArrayList<Modelo> modelos = new ArrayList<Modelo>();
	private static ArrayList<Historico> historicos = new ArrayList<Historico>();
    private static Scanner scan = new Scanner(System.in);
	public static Utils utils = new Utils();
	// eventualmente outros atributos static
	
	public static void main(String[] args) {
        setup();
		// outras variaveis locais
        int menu;
        
		// men
        printString("Seja bem vindo!");
        do {
            printString("\nEstacionamento - Menu");
            printString("1) Registrar chegada de carro");
            printString("2) Registrar saída de carro");
            printString("3) Gerar relatório");
            printString("4) Sair do sistema");
            printString("5) Ver vagas");
            printString("");
            printString("Insira um número para escolher uma ação:");
            menu = Integer.parseInt(scan.nextLine());
            switch (menu) {
                case 1:
                    entradaCarro();
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
            scan.reset();
        } while (menu != 4);
        scan.close();
		// opcao
		// chamar metodos static que correspondam as opcoes de menu
	}
	
	private static void entradaCarro() {
		// criar o carro e cadastra-lo no vetor na posicao correta
        Marca marca = menuMarca();
        Modelo modelo = menuModelo(marca);
        LocalDateTime time = menuHora();
        printString("\nInsira a placa do carro: ");
        String placa = scan.nextLine();

        if (modelo == null || marca == null) {
            printString("Erro ao definir modelo e/ou marca.");
            return;
        }
        Carro carro = new Carro(placa, modelo, time);
        printString("Registro de entrada de carro: " + placa + ", " + marca.getNome() + " " + modelo.getNome() + ", " + now.toString());
        vagas.add(carro);
	}
	
	private static float saidaCarro() {
        // logica para calcular preco do estacionamento e coloca-lo no historico
        Carro carro = menuVagas();
        
        printString("Estacionamento finalizado: ");
        carro.display();
        carro.setSaida(LocalDateTime.now());
        printString("\nEstadia " + carro.getEstadia());
        float preco = carro.getValor();
        printString("\nCusto: " + preco);
        registrarHistorico(carro);
		return preco;
	}

    private static void gerarRelatorio() {
        printString("Relatório");
    }
	
    private static void registrarHistorico (Carro carro) {
        if (historicos == null) historicos = new ArrayList<Historico>();
        historicos.add(new Historico(carro));
        utils.setDatabase(historicos, "Historico");
    }
	// outros m�todos static conforme especificacao do trabalho e necessidades de implementacao
    private static void printString(String str) {
        System.out.println(str);
    }
    
    private static Marca menuMarca () {        
        int menuMarca = 0;
        Marca marca = null;
        boolean marcaOk = false;
        boolean novaMarca = false;

        do {
            printString("\nMarcas Disponíveis");
            printString("0) Adicionar nova marca");
            for (Marca m : marcas) {
                printString(String.valueOf(marcas.indexOf(m)+1) + ") " + m.getNome());
            }
            printString("\nSelecione uma marca: ");
            menuMarca = Integer.parseInt(scan.nextLine());
            try {
                if (menuMarca == 0) {
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
            printString("\nInsira o nome da nova marca: ");
            String nomeMarca = scan.nextLine();
            marca = new Marca(nomeMarca);
            marcas.add(marca);
            utils.setDatabase(marcas, "Marca");
        }
        
        printString("\nMarca selecionada: " + marca.getNome());
        return marca;
    }
    
    private static Modelo menuModelo(Marca marca) {
        int menuModelo = 0;
        Modelo modelo = null;
        boolean modeloOk = false;
        boolean novoModelo = false;
        ArrayList<Modelo> mlist = new ArrayList<Modelo>();
        for (Modelo m : modelos) {
            if (Boolean.TRUE.equals(marca.temModelo(m)))
                mlist.add(m);
        }
        
        do {
            printString("\nModelos Disponíveis");
            printString("0) Adicionar novo modelo");
            for (Modelo m : mlist) {
                printString(String.valueOf(mlist.indexOf(m)+1) + ") " + m.getNome());
            }
            printString("\nSelecione um modelo: ");
            menuModelo = Integer.parseInt(scan.nextLine());
            try {
                if (menuModelo == 0) {
                    modeloOk = true;
                    novoModelo = true;
                } else if (menuModelo > 0) {
                    modelo = mlist.get(menuModelo-1);
                    modeloOk = true;
                } else {
                    printString("Insira um número válido.");
                }
                
            } catch (Exception e) {
                printString("Pedido inválido. Selecione outro número");
            }
        } while (!modeloOk);

        if (novoModelo) {
            printString("\nInsira o nome do novo modelo: ");
            String nomeModelo = scan.nextLine();
            modelo = new Modelo(nomeModelo);
            modelos.add(modelo);
            marca.addModelo(modelo);
            utils.setDatabase(marcas, "Marca");
            utils.setDatabase(modelos, "Modelo");
        }
        
        printString("\nModelo selecionado: " + modelo.getNome());
        return modelo;
    }

    private static Carro menuVagas () {        
        int menuVaga = 0;
        Carro vaga = null;
        boolean vagaOk = false;
        do {
            for (Carro c : vagas) {
                printString(String.valueOf(vagas.indexOf(c)+1) + ") " + c.getModelo().getNome() + " - " + c.getPlaca());
            }
            printString("\nSelecione uma vaga para sair: ");
            menuVaga = Integer.parseInt(scan.nextLine());
            try {
                if (menuVaga > 0) {
                    int index = menuVaga-1;
                    vaga = vagas.remove(index);
                    vagaOk = true;
                } else {
                    printString("Insira um número válido.");
                }
                
            } catch (Exception e) {
                printString("Pedido inválido. selecione outro número");
            }
        } while (!vagaOk);
        return vaga;
    }

    private static LocalDateTime menuHora() {
        printString("\nInsira o horário ou selecione uma opção: ");
        printString("1) Agora");
        printString("2) Horário específico");
        int menu = Integer.parseInt(scan.nextLine());
        LocalDateTime time = LocalDateTime.now();
        boolean menuOk = false;

        while (!menuOk) {
            time = LocalDateTime.now();
            switch (menu) {
                case 1:
                    menuOk = true;
                    break;
                case 2:
                    printString("Insira o dia:");
                    int dia = Integer.parseInt(scan.nextLine());
                    printString("Insira a hora:");
                    int hora = Integer.parseInt(scan.nextLine());
                    printString("Insira o minuto:");
                    int minuto = Integer.parseInt(scan.nextLine());
                    time = LocalDateTime.of(time.getYear(), time.getMonth(), dia, hora, minuto, 0);
                    menuOk = true;
                    break;
                default:
                    printString("Opção inválida");
                    break;
            }
            printString("Hora de entrada: " + time.toLocalTime());
        }
        return time;
    }

    private static void setup() {
        marcas = utils.getMarcas();
        modelos = utils.getModelos();
        historicos = utils.getHistorico();
    }

}
