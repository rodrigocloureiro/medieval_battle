package core;

import exceptions.PlayerNotFoundException;
import util.Linha;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class Relatorio {
    private List<Linha> logs;
    private String nickname;

    public Relatorio(String nickname) throws IOException, PlayerNotFoundException {
        this.logs = logJogador(nickname);
        this.nickname = nickname;
    }

    private HashMap<String, Integer> mapear(List<Linha> logs, Function<Linha, String> keyExtractor) {
        HashMap<String, Integer> map = new HashMap<>();
        for (Linha log : logs) {
            String key = keyExtractor.apply(log);
            map.put(key, map.getOrDefault(key, 0) + 1);
        }
        return map;
    }

    private String formatarAparicoes(String str) {
        int ultimaOcorrencia = str.lastIndexOf(" e ");
        if (ultimaOcorrencia != -1) {
            String prev = str.substring(0, ultimaOcorrencia).replace(" e ", ", ");
            String next = str.substring(ultimaOcorrencia);
            return prev + next;
        } else {
            return str;
        }
    }

    private String maisAparicoes(HashMap<String, Integer> map) {
        String personagem = null;
        int count = 0;
        /*for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (entry.getValue() > count) {
                personagem = entry.getKey();
                count = entry.getValue();
            } else if (entry.getValue() == count) {
                personagem = String.format("%s e %s", personagem, entry.getKey());
            }
        }*/
        for (String key : map.keySet()) {
            if (map.get(key) > count) {
                personagem = key;
                count = map.get(key);
            } else if (map.get(key) == count) {
                personagem = String.format("%s e %s", personagem, key);
            }
        }
        return formatarAparicoes(personagem);
    }

    private String heroiMaisJogado() {
        return maisAparicoes(mapear(this.logs, Linha::getHeroi));
    }

    private String monstroMaisEnfrentado() {
        return maisAparicoes(mapear(this.logs, Linha::getMonstro));
    }

    private int calcularPontuacao() {
        int pontuacao = 0;
        for (Linha linha : this.logs) {
            if (linha.isGanhou()) pontuacao += 100 - linha.getRodada();
        }
        return pontuacao;
    }

    private int batalhaMaisLonga() {
        int numRodadas = 0;
        for (Linha linha : this.logs) {
            if (linha.getRodada() > numRodadas) numRodadas = linha.getRodada();
        }
        return numRodadas;
    }

    private int batalhaMaisCurta() {
        int numRodadas = Integer.MAX_VALUE;
        for (Linha linha : this.logs) {
            if (linha.getRodada() < numRodadas) numRodadas = linha.getRodada();
        }
        return numRodadas;
    }

    private HashMap<LocalDateTime, Boolean> mapearVitorias() {
        HashMap<LocalDateTime, Boolean> map = new HashMap<>();
        for (Linha log : logs) {
            map.put(log.getDataHoraPartida(), log.isGanhou());
        }
        return map;
    }

    private int numeroVitorias() {
        int vitorias = 0;
        /*for (Map.Entry<LocalDateTime, Boolean> entry : mapearVitorias().entrySet()) {
            if (entry.getValue()) vitorias++;
        }*/
        var map = mapearVitorias();
        for (LocalDateTime key : map.keySet()) {
            if (map.get(key)) vitorias++;
        }
        return vitorias;
    }

    private double calcularAproveitamento() {
        return (double) numeroVitorias() / this.logs.size() * 100;
    }

    private List<Linha> logJogador(String nickname) throws IOException, PlayerNotFoundException {
        Path programDir = Paths.get(System.getProperty("user.dir"), "/temp");
        Path file = programDir.resolve(Paths.get(nickname + ".csv"));

        if (Files.exists(file)) {
            List<String> linhas = Files.readAllLines(file);
            List<Linha> logs = new ArrayList<>();

            for (String linha : linhas) {
                String[] parte = linha.split(";");
                logs.add(new Linha(
                        LocalDateTime.parse(parte[0], DateTimeFormatter.ofPattern("dd/MM/yyyy_HH:mm:ss")),
                        parte[1],
                        parte[2].equals("GANHOU"),
                        parte[3],
                        Integer.parseInt(parte[4])
                ));
            }
            return logs;
        } else throw new PlayerNotFoundException("O jogador não existe.");
    }

    public void gerarRelatorio() {
        System.out.printf("\n<<<<< Relatório: %s >>>>>\n", this.nickname);
        System.out.printf("Herói mais jogado: %s\n", heroiMaisJogado());
        System.out.printf("Monstro mais enfrentado: %s\n", monstroMaisEnfrentado());
        System.out.printf("Pontuação total: %d\n", calcularPontuacao());
        System.out.printf("Batalha mais longa: %d rodadas\n", batalhaMaisLonga());
        System.out.printf("Batalha mais curta: %d rodadas\n", batalhaMaisCurta());
        System.out.printf("Número de partidas: %d\n", this.logs.size());
        System.out.printf("Número de vitórias: %d\n", numeroVitorias());
        System.out.printf("Aproveitamento: %.1f%%\n", calcularAproveitamento());
    }
}
