package personagens;

import util.Dado;

public abstract class Personagem {
    private int pontosDeVida;
    private int forca;
    private int defesa;
    private int agilidade;

    public Personagem(int pontosDeVida, int forca, int defesa, int agilidade) {
        this.pontosDeVida = pontosDeVida;
        this.forca = forca;
        this.defesa = defesa;
        this.agilidade = agilidade;
    }

    public int getPontosDeVida() {
        return pontosDeVida;
    }

    public int getForca() {
        return forca;
    }

    public int getDefesa() {
        return defesa;
    }

    public int getAgilidade() {
        return agilidade;
    }

    public int calcularIniciativa() {
        return Dado.rolarD10() + this.getAgilidade();
    }

    public int calcularFatorAtaque() {
        return Dado.rolarD10() + this.getAgilidade() + this.getForca();
    }

    public int calcularFatorDefesa() {
        return Dado.rolarD10() + this.getAgilidade() + this.getDefesa();
    }

    public abstract int calcularDano();

    public void receberDano(int dano) {
        this.pontosDeVida -= dano;
    }
}
