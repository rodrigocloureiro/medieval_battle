package core;

import exceptions.InvalidChoiceException;
import personagens.Personagem;
import personagens.herois.*;
import personagens.monstros.*;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class GerenciadorDePersonagens {
    private static void exibirOpcoes() {
        System.out.println("Qual classe de herói deseja jogar?");
        System.out.println("1- Guerreiro");
        System.out.println("2- Bárbaro");
        System.out.println("3- Paladino");
    }

    private static int escolhaHeroi(Scanner sc) {
        int escolhaHeroi = 0;
        do {
            try {
                exibirOpcoes();
                escolhaHeroi = sc.nextInt();
                if (escolhaHeroi < 1 || escolhaHeroi > 3) {
                    escolhaHeroi = 0;
                    throw new InvalidChoiceException("Opção inválida. Tente novamente.");
                }
            } catch (InvalidChoiceException ex) {
                System.out.println(ex.getMessage());
            } catch (InputMismatchException ex) {
                System.out.println("Informe um valor INTEIRO.");
            } finally {
                sc.nextLine(); // limpando o buffer
            }
        } while (escolhaHeroi == 0);

        return escolhaHeroi;
    }

    public static Personagem criarHeroi(Scanner sc) {
        Personagem p = null;
        switch (escolhaHeroi(sc)) {
            case 1 -> p = new Guerreiro(12, 4, 3, 3);
            case 2 -> p = new Barbaro(13, 6, 1, 3);
            case 3 -> p = new Paladino(15, 2, 5, 1);
        }
        return p;
    }

    public static Personagem criarMonstroAleatorio() {
        Personagem p = null;
        switch (new Random().nextInt(3) + 1) {
            case 1 -> p = new MortoVivo(25, 4, 0, 1);
            case 2 -> p = new Orc(20, 6, 2, 2);
            case 3 -> p = new Kobold(20, 4, 2, 4);
        }
        return p;
    }
}
