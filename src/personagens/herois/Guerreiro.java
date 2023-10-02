package personagens.herois;

import personagens.Personagem;
import util.Dado;

public class Guerreiro extends Personagem {
    public Guerreiro(int pontosDeVida, int forca, int defesa, int agilidade) {
        super(pontosDeVida, forca, defesa, agilidade);
    }

    private int calcularFatorDeDano() {
        return Dado.rolarD4() + Dado.rolarD4() + getForca();
    }

    @Override
    public int calcularDano() {
        return calcularFatorDeDano() + getForca();
    }

    @Override
    public String toString() {
        return "Guerreiro";
    }
}
