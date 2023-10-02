package util;

import java.time.LocalDateTime;

public class Linha {
    private int rodada;
    private boolean ganhou;
    private LocalDateTime dataHoraPartida;
    private String heroi;
    private String monstro;

    public Linha(LocalDateTime dataHoraPartida, String heroi, boolean ganhou, String monstro, int rodada) {
        this.dataHoraPartida = dataHoraPartida;
        this.heroi = heroi;
        this.ganhou = ganhou;
        this.monstro = monstro;
        this.rodada = rodada;
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

    public String getHeroi() {
        return heroi;
    }

    public String getMonstro() {
        return monstro;
    }
}
