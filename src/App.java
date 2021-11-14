import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

import Classes.*;
import Utils.Utils;

public class App {
    // atributos static são atributos de classe
    private static Carro[] vagas = new Carro[100]; // o estacionamento tem 100 vagas numeradas de 0..99
    private static ArrayList<Marca> marcas = new ArrayList<Marca>();
    private static ArrayList<Modelo> modelos = new ArrayList<Modelo>();
    private static ArrayList<Historico> historicos = new ArrayList<Historico>();
    private static Scanner scan = new Scanner(System.in);
    public static Utils utils = new Utils();

    public static void main(String[] args) {
        setup();
        int menu;
        printString("Seja bem vindo!");
        do {
            printString("\nEstacionamento - Menu");
            printString("1) Registrar chegada de carro");
            printString("2) Registrar saída de carro");
            printString("3) Gerar relatório");
            printString("4) Ver vagas");
            printString("5) Sair do sistema");
            printString("");
            printString("Insira um número para escolher uma ação:");
            menu = Integer.parseInt(scan.nextLine());
            utils.printSpace();
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
                verVagas();
                break;
            case 5:
                printString("Obrigado por utilizar! Até logo.");
                break;

            default:
                printString("Comando inválido, por favor, tente novamente.");
                break;
            }
            scan.reset();
            setarDados();
            utils.printSpace();
        } while (menu != 5);
        scan.close();
    }

    // Carregador dos dados
    private static void setup() {
        marcas = utils.getMarcas();
        modelos = utils.getModelos();
        historicos = utils.getHistorico();
        vagas = utils.getVagas();
        if (vagas.length == 0) {
            vagas = new Carro[100];
            for (int i = 0; i < 100; i++)
                vagas[i] = null;
        }
    }

    // Funções principais dos menus
    private static void setarDados() {
        utils.setDatabase(vagas, "Vagas");
        utils.setDatabase(historicos, "Historico");
        utils.setDatabase(marcas, "Marca");
        utils.setDatabase(modelos, "Modelo");
    }

    private static void entradaCarro() {
        Marca marca = menuMarca();
        Modelo modelo = menuModelo(marca);
        LocalDateTime time = menuHora("entrada");
        printString("\nInsira a placa do carro: ");
        String placa = scan.nextLine();

        if (modelo == null || marca == null) {
            printString("Erro ao definir modelo e/ou marca.");
            return;
        }
        Carro carro = new Carro(placa, modelo, time);
        utils.printSpace();
        printString("Registro de entrada de carro: " + placa + ", " + marca.getNome() + " " + modelo.getNome() + ", "
                + time.toString());
        adicionarCarro(carro);
        registrarHistorico(carro);
    }

    private static void saidaCarro() {
        Carro carro = menuVagas();
        if (carro == null)
            return;
        printString("Estacionamento finalizado: ");
        carro.display();
        LocalDateTime time = menuHora("saída");
        carro.setSaida(time);
        printString("\nEstadia " + carro.getEstadia());
        float preco = carro.getValor();
        printString("\nCusto: " + preco);
        removerCarro(carro);
    }

    private static void gerarRelatorio() {
        // TODO: Finalizar relatório
        if (historicos.isEmpty()) {
            printString("Sem históriocos no momento");
            return;
        }
        printString("\nRelatório");
        printString("Selecione um dia no formato dd/mm/yyyy");
        String periodo = scan.nextLine();
        recuperarDadosPeriodo(periodo);
    }

    private static void verVagas() {
        int badge = 0;
        for (int i = 0; i < vagas.length; i++) {
            if (vagas[i] != null) {
                if (badge > 0) {
                    if (badge == 1)
                        printString(String.valueOf(i + 1) + ") Livre");
                    else {
                        printString(String.valueOf(badge) + " vagas livres em sequência");
                    }
                    badge = 0;
                }
                Carro c = vagas[i];
                printString(String.valueOf(i + 1) + ") " + c.getModelo().getNome() + " - " + c.getPlaca() + " - "
                        + c.getEntradaFormatted(""));
            } else {
                badge++;
            }
        }
        if (badge > 0) {
            String texto2 = badge == 1 ? "livre" : "livres";
            String texto = badge == 1 ? "Vaga" : "Vagas";
            printString(String.valueOf(badge) + " " + texto + " " + texto2);
            badge = 0;
        }
    }

    // Funções auxiliares
    private static void adicionarCarro(Carro carro) {
        if (vagas.length == 0) {
            vagas[0] = carro;
            return;
        }
        for (int i = 0; i < vagas.length; i++) {
            if (vagas[i] == null) {
                vagas[i] = carro;
                return;
            }
        }
    }

    private static Carro removerCarro(Carro carro) {
        for (int i = 0; i < vagas.length; i++) {
            if (vagas[i] != null && vagas[i].getPlaca().equals(carro.getPlaca())) {
                Carro carroExclude = vagas[i];
                vagas[i] = null;
                return carroExclude;
            }
        }
        return null;
    }

    private static boolean estacionamentoVazio() {
        for (int i = 0; i < vagas.length; i++) {
            if (vagas[i] != null) {
                return false;
            }
        }
        return true;
    }

    private static void registrarHistorico(Carro carro) {
        if (historicos == null)
            historicos = new ArrayList<Historico>();
        historicos.add(new Historico(carro));
    }

    private static void printString(String str) {
        System.out.println(str);
    }

    private static void recuperarDadosPeriodo(String periodo) {
        ArrayList<Historico> hists = new ArrayList<>();
        for (Historico h : historicos) {
            String data = h.getCarro().getEntradaFormatted("dd/MM/YYYY");
            if (data.equals(periodo))
                hists.add(h);
        }
        if (hists.size() == 0) {
            printString("Sem estacionamentos nesse dia!");
            return;
        }
        for (Historico h : hists) {
            Carro c = h.getCarro();
            printString(c.getMarca().getNome() + " " + c.getModelo().getNome() + " " + c.getPlaca() + "\n"
                    + c.getEntradaFormatted("HH:mm:ss") + " até " + c.getSaidaFormatted("HH:mm:ss") + "\n"
                    + "Valor pago: " + c.getValor());
        }
    }

    // Menus de visualização, "modularizados"
    private static Marca menuMarca() {
        int menuMarca = 0;
        Marca marca = null;
        boolean marcaOk = false;
        boolean novaMarca = false;

        do {
            printString("\nMarcas Disponíveis");
            printString("0) Adicionar nova marca");
            for (Marca m : marcas) {
                printString(String.valueOf(marcas.indexOf(m) + 1) + ") " + m.getNome());
            }
            printString("\nSelecione uma marca: ");
            menuMarca = Integer.parseInt(scan.nextLine());
            try {
                if (menuMarca == 0) {
                    marcaOk = true;
                    novaMarca = true;
                } else if (menuMarca > 0) {
                    marca = marcas.get(menuMarca - 1);
                    marcaOk = true;
                } else {
                    printString("Insira um número válido.");
                }
            } catch (Exception e) {
                printString("Pedido inválido. selecione outro número");
            }
        } while (!marcaOk);

        if (novaMarca) {
            utils.printSpace();
            printString("\nInsira o nome da nova marca: ");
            String nomeMarca = scan.nextLine();
            marca = new Marca(nomeMarca);
            marcas.add(marca);
        }
        utils.printSpace();
        printString("\nMarca selecionada: " + marca.getNome());
        utils.printSpace();
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
                printString(String.valueOf(mlist.indexOf(m) + 1) + ") " + m.getNome());
            }
            printString("\nSelecione um modelo: ");
            menuModelo = Integer.parseInt(scan.nextLine());
            try {
                if (menuModelo == 0) {
                    modeloOk = true;
                    novoModelo = true;
                } else if (menuModelo > 0) {
                    modelo = mlist.get(menuModelo - 1);
                    modeloOk = true;
                } else {
                    printString("Insira um número válido.");
                }

            } catch (Exception e) {
                printString("Pedido inválido. Selecione outro número");
            }
        } while (!modeloOk);

        if (novoModelo) {
            utils.printSpace();
            printString("\nInsira o nome do novo modelo: ");
            String nomeModelo = scan.nextLine();
            modelo = new Modelo(nomeModelo);
            modelos.add(modelo);
            marca.addModelo(modelo);
        }
        utils.printSpace();
        printString("\nModelo selecionado: " + modelo.getNome());
        utils.printSpace();
        return modelo;
    }

    private static Carro menuVagas() {
        int menuVaga = 0;
        Carro vaga = null;
        boolean vagaOk = false;
        if (estacionamentoVazio()) {
            printString("Nenhuma vaga ocupada");
            return null;
        }
        do {
            verVagas();
            utils.printSpace();
            printString("\nSelecione uma vaga para sair: ");
            menuVaga = Integer.parseInt(scan.nextLine());
            try {
                if (menuVaga > 0) {
                    int index = menuVaga - 1;
                    vaga = removerCarro(vagas[index]);
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

    private static LocalDateTime menuHora(String type) {
        printString("\nHorário de " + type);
        printString("1) Agora");
        printString("2) Outro Horário específico");
        printString("\nInsira o horário ou selecione uma opção: ");
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
                utils.printSpace();
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
            utils.printSpace();
            printString("Hora de " + type + ": " + time.toLocalTime());
            utils.printSpace();
        }
        return time;
    }

}
