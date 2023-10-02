package view;

import core.*;
import exceptions.InvalidChoiceException;
import exceptions.PlayerNotFoundException;
import personagens.Personagem;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {
    private void exibirOpcoes() {
        System.out.println("O que você deseja?");
        System.out.println("1- Jogar\n2- Gerar relatório");
    }

    private int escolha(Scanner sc) {
        int escolha = 0;
        do {
            try {
                exibirOpcoes();
                escolha = sc.nextInt();
                if (escolha < 1 || escolha > 2) {
                    escolha = 0;
                    throw new InvalidChoiceException("Opção inválida. Tente novamente.");
                }
            } catch (InvalidChoiceException ex) {
                System.out.println(ex.getMessage());
            } catch (InputMismatchException ex) {
                System.out.println("Informe um valor INTEIRO.");
            } finally {
                sc.nextLine(); // limpando o buffer
            }
        } while (escolha == 0);
        return escolha;
    }

    public void run() {
        Scanner sc = new Scanner(System.in);
        String nickname = GerenciadorNickname.nickname(sc);

        switch (escolha(sc)) {
            case 1 -> {
                Personagem heroi = GerenciadorDePersonagens.criarHeroi(sc);
                Personagem monstro = GerenciadorDePersonagens.criarMonstroAleatorio();
                System.out.printf("Você está contra o monstro \"%s\"\n", monstro);

                Jogo jogo = new Jogo(heroi, monstro);
                jogo.jogar();

                Log log = new Log(nickname, jogo);
                log.gerarLog();
            }
            case 2 -> {
                try {
                    Relatorio gerador = new Relatorio(nickname);
                    gerador.gerarRelatorio();
                } catch (PlayerNotFoundException | IOException ex) {
                    System.out.println(ex.getMessage());
                    run();
                }
            }
        }
    }
}
