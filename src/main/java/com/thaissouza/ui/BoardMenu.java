package com.thaissouza.ui;

import com.thaissouza.persistence.entity.BoardEntity;
import lombok.AllArgsConstructor;

import java.util.Scanner;

@AllArgsConstructor
public class BoardMenu {

    private final BoardEntity entity;

    private final Scanner scanner = new Scanner(System.in).useDelimiter("\n");

    public void execute() {
        System.out.printf("Bem vindo ao quadro %s, escolha a opção desejada", entity.getId());
        var option = -1;
        while (option != 9) {
            System.out.println("1 - Criar um novo card");
            System.out.println("2 - Mover um card");
            System.out.println("3 - Bloquear um card");
            System.out.println("4 - Desbloquear um card");
            System.out.println("5 - Cancelar um card");
            System.out.println("6 - Ver quadro");
            System.out.println("7 - Ver coluna com cards");
            System.out.println("8 - Ver card");
            System.out.println("9 - Voltar para o menu anterior");
            System.out.println("10 - Sair");

            option = scanner.nextInt();
            switch (option) {
                case 1 -> createCard();
                case 2 -> moveCard();
                case 3 -> blockCard();
                case 4 -> unblockCard();
                case 5 -> cancelCard();
                case 6 -> showComluns();
                case 7 -> showColumn();
                case 8 -> showCard();
                case 9 -> System.out.println("Voltando para o menu anterior");
                case 10 -> System.exit(0);
                default -> System.out.println("Opção inválida, informe uma opção do menu");
            }
        }
    }

    private void createCard() {
    }

    private void moveCard() {
    }

    private void blockCard() {
    }

    private void unblockCard() {
    }

    private void cancelCard() {
    }

    private void showComluns() {
    }

    private void showColumn() {
    }

    private void showCard() {
    }
}
