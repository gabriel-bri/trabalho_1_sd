package com.example.Q2;

import java.util.Scanner;

public class TesteEntradaPadrao {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Digite os dados dos aparelhos:");

            int numeroAparelhos = scanner.nextInt();
            scanner.nextLine();

            System.out.println("Número de aparelhos recebidos: " + numeroAparelhos);

            for (int i = 0; i < numeroAparelhos; i++) {
                System.out.println("Digite o modelo do aparelho " + (i + 1) + ":");
                String modelo = scanner.nextLine();

                System.out.println("Digite o preço do aparelho " + (i + 1) + ":");
                String preco = scanner.nextLine();

                System.out.println("Aparelho recebido - Modelo: " + modelo + ", Preço: " + preco);
            }

        }
    }
}
