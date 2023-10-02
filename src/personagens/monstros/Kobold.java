package personagens.monstros;

import personagens.Personagem;
import util.Dado;

public class Kobold extends Personagem {
    public Kobold(int pontosDeVida, int forca, int defesa, int agilidade) {
        super(pontosDeVida, forca, defesa, agilidade);
    }

    private int calcularFatorDeDano() {
        return Dado.rolarD2() + Dado.rolarD2() + Dado.rolarD2();
    }

    @Override
    public int calcularDano() {
        return calcularFatorDeDano() + getForca();
    }

    @Override
    public String toString() {
        return "Kobold";
    }
}
