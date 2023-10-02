package core;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Log {
    private String nickname;
    private Jogo jogo;
    private Path dir;

    public Log(String nickname, Jogo jogo) {
        this.nickname = nickname;
        this.jogo = jogo;
    }

    private String gerarLinha() {
        return String.format("%s;%s;%s;%s;%d\n",
                jogo.getDataHoraPartida().format(jogo.getFormatter()),
                jogo.getHeroi(),
                jogo.isGanhou() ? "GANHOU" : "PERDEU",
                jogo.getMonstro(),
                (jogo.getRodada() - 1));
    }

    private void criarDiretorio() throws IOException {
        this.dir = Paths.get("temp");
        if (!Files.exists(this.dir)) Files.createDirectory(this.dir);
    }

    private Path criarArquivo() throws IOException {
        Path file = this.dir.resolve(Paths.get(this.nickname + ".csv"));
        if (!Files.exists(file)) Files.createFile(file);
        return file;
    }

    private void escreverLinha(Path file) throws IOException {
        Files.write(file, gerarLinha().getBytes(), StandardOpenOption.APPEND);
    }

    public void gerarLog() {
        try {
            criarDiretorio();
            escreverLinha(criarArquivo());
        } catch (IOException ex) {
            System.out.println(ex + " - Erro ao lidar com arquivo.");
        }
    }
}
