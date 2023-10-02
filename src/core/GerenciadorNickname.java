package core;

import exceptions.InvalidNicknameException;

import java.util.Scanner;

public class GerenciadorNickname {
    public static String nickname(Scanner sc) {
        String nickname = null;
        do {
            try {
                System.out.print("Informe o seu nickname (entre 3 e 8 caracteres): ");
                nickname = sc.nextLine();
                if (nickname.length() < 3 || nickname.length() > 8) {
                    nickname = null;
                    throw new InvalidNicknameException("Informe um nickname válido.");
                }
            } catch (InvalidNicknameException ex) {
                System.out.println(ex.getMessage());
            }
        } while (nickname == null);
        return nickname.toLowerCase();
    }
}
