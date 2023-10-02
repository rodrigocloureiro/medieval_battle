package core;

import personagens.Personagem;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Jogo {
    private int rodada;
    private boolean ganhou;
    private LocalDateTime dataHoraPartida;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy_HH:mm:ss"); // hh -> formato 12 horas | HH -> formato 24 horas;
    private Personagem heroi;
    private Personagem monstro;

    public Jogo(Personagem heroi, Personagem monstro) {
        this.rodada = 1;
        this.ganhou = false;
        this.dataHoraPartida = LocalDateTime.now();
        this.heroi = heroi;
        this.monstro = monstro;
    }

    public int getRodada() {
        return rodada;
    }

    public boolean isGanhou() {
        return ganhou;
    }

    public LocalDateTime getDataHoraPartida() {
        return dataHoraPartida;
    }

    public DateTimeFormatter getFormatter() {
        return formatter;
    }

    public Personagem getHeroi() {
        return heroi;
    }

    public Personagem getMonstro() {
        return monstro;
    }

    private void realizarAtaque(Personagem atacante, Personagem defensor) {
        int dano = atacante.calcularDano();
        defensor.receberDano(dano);
        System.out.println("Fator Dano: " + (dano - atacante.getForca()));
        System.out.println("Dano Atacante: " + dano);
    }

    private void realizarRodada() {
        Personagem atacante;
        Personagem defensor;
        int iniciativaHeroi = 0, iniciativaMonstro = 0;

        while (iniciativaHeroi == iniciativaMonstro) {
            iniciativaHeroi = this.heroi.calcularIniciativa();
            iniciativaMonstro = this.monstro.calcularIniciativa();
        }

        System.out.println("Iniciativa Heroi: " + iniciativaHeroi);
        System.out.println("Iniciativa Monstro: " + iniciativaMonstro);

        if (iniciativaHeroi > iniciativaMonstro) {
            atacante = this.heroi;
            defensor = this.monstro;
            System.out.printf("Heroi (%s) é o atacante\n", atacante);
        } else {
            atacante = this.monstro;
            defensor = this.heroi;
            System.out.printf("Monstro (%s) é o atacante\n", atacante);
        }

        int fatorAtaque = atacante.calcularFatorAtaque();
        int fatorDefesa = defensor.calcularFatorDefesa();

        System.out.println("Fator de Ataque: " + fatorAtaque);
        System.out.println("Fator de Defesa: " + fatorDefesa);

        if (fatorAtaque > fatorDefesa) {
            this.realizarAtaque(atacante, defensor);
        }

        System.out.printf("Vida Atacante (%s): %d\n", atacante, atacante.getPontosDeVida());
        System.out.printf("Vida Defensor (%s): %d\n", defensor, defensor.getPontosDeVida());
    }

    public void jogar() {
        for (this.rodada = 1; this.heroi.getPontosDeVida() > 0 && this.monstro.getPontosDeVida() > 0; this.rodada++) {
            System.out.printf("\n***** Rodada: %d *****\n\n", this.rodada);
            this.realizarRodada();
        }
        System.out.println("\n<<<<<<<<<<<<<<< BATALHA ENCERRADA >>>>>>>>>>>>>>>\n");
        this.exibirVencedor();
    }

    private void exibirVencedor() {
        if (this.heroi.getPontosDeVida() > 0) {
            System.out.printf("O herói \"%s\" venceu.\n", this.heroi);
            this.ganhou = true;
        } else System.out.printf("O monstro \"%s\" venceu.\n", this.monstro);
    }
}
