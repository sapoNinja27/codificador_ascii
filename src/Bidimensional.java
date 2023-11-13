import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class Bidimensional {

    public static void bidimensional() {
        Scanner scanner = new Scanner(System.in);
        boolean s = Objects.equals(scanner.nextLine(), "1");
        if (s) {
            System.out.print("Digite a palavra: ");
            String palavra = scanner.nextLine();
            salvarMatrizEmArquivo("matriz.txt", codificarParidadeASCII(palavra));
        } else {
            boolean integro = verificarIntegridade(lerMatrizDoArquivo("matriz.txt"));
            System.out.println("Integro? " + (integro ? "sim" : "não"));
        }
    }

    private static void salvarMatrizEmArquivo(String nomeArquivo, int[][] matriz) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo))) {
            // Escrever o número de linhas e colunas da matriz
            writer.write(matriz.length + " " + matriz[0].length);
            writer.newLine();

            // Escrever os valores da matriz
            for (int i = 0; i < matriz.length; i++) {
                for (int j = 0; j < matriz[i].length; j++) {
                    writer.write(Integer.toString(matriz[i][j]) + " ");
                }
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int[][] lerMatrizDoArquivo(String nomeArquivo) {
        int[][] matriz = null;

        try  {
            BufferedReader br = new BufferedReader(new FileReader(nomeArquivo));
            String colunaLinha = br.readLine();
            int linhas = Integer.parseInt(String.valueOf(colunaLinha.split(" ")[0]));
            int colunas = Integer.parseInt(String.valueOf(colunaLinha.split(" ")[1]));

            matriz = new int[linhas][colunas];
            for (int i = 0; i < linhas; i++) {
                String linha = br.readLine();
                for (int j = 0; j < colunas; j++) {
                    matriz[i][j] = Character.getNumericValue(linha.charAt(j));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return matriz;
    }

    private static boolean verificarIntegridade(int[][] matrizBits) {
        // Verifica paridade por linha
        for (int i = 0; i < matrizBits.length; i++) {
            int paridadeLinha = calcularParidade(Arrays.copyOfRange(matrizBits[i], 0, matrizBits[i].length - 1));
            if (paridadeLinha != matrizBits[i][matrizBits[i].length - 1]) {
                return false;
            }
        }

        // Verifica paridade por coluna
        for (int j = 0; j < matrizBits[0].length; j++) {
            int[] coluna = new int[matrizBits.length - 1];
            for (int i = 0; i < matrizBits.length - 1; i++) {
                coluna[i] = matrizBits[i][j];
            }
            int paridadeColuna = calcularParidade(coluna);
            if (paridadeColuna != matrizBits[matrizBits.length - 1][j]) {
                return false;
            }
        }

        return true;
    }

    private static int[][] codificarParidadeASCII(String palavra) {
        int[][] matrizBits = converterParaMatrizBits(palavra);
        adicionarParidadeBidimensional(matrizBits);
        System.out.println("\nMatriz Codificada:");
        imprimirMatriz(matrizBits);
        return matrizBits;

    }

    private static int[][] converterParaMatrizBits(String palavra) {
        int[][] matrizBits = new int[palavra.length() + 1][8];

        // Preenche a matriz com os bits da palavra
        for (int i = 0; i < palavra.length(); i++) {
            char caracter = palavra.charAt(i);
            for (int j = 0; j < 8; j++) {
                matrizBits[i][j] = (caracter >> (7 - j)) & 1;
            }
        }

        return matrizBits;
    }

    private static void adicionarParidadeBidimensional(int[][] matrizBits) {
        char[] caracteres = palavra.toCharArray();

        // Calcular o número de bits de paridade necessário
        int numBitsParidade = 0;
        while (Math.pow(2, numBitsParidade) < caracteres.length + numBitsParidade + 1) {
            numBitsParidade++;
        }

        // Adicionar bits de paridade à lista
        StringBuilder palavraComParidade = new StringBuilder();
        int indiceParidade = 0;
        for (int i = 0; i < caracteres.length + numBitsParidade; i++) {
            if ((i + 1) == Math.pow(2, indiceParidade)) {
                palavraComParidade.append('P');
                indiceParidade++;
            } else {
                palavraComParidade.append(caracteres[i - indiceParidade]);
            }
        }

        // Criar uma matriz bidimensional
        int[][] matriz = new int[caracteres.length + numBitsParidade][numBitsParidade];

        // Preencher a matriz com os valores da palavra e bits de paridade
        for (int i = 0; i < matriz.length; i++) {
            String binario = new StringBuilder(Integer.toBinaryString(i + 1))
                    .reverse().toString();
            for (int j = 0; j < numBitsParidade; j++) {
                matriz[i][j] = Integer.parseInt(String.valueOf(binario.charAt(j)));
            }
        }

        return matriz;
    }

    private static int calcularParidade(int[] linha) {
        int soma = 0;
        for (int bit : linha) {
            soma += bit;
        }
        return soma % 2;
    }

    private static int calcularParidadePorColuna(int[][] matrizBits, int coluna) {
        int soma = 0;
        for (int i = 0; i < matrizBits.length - 1; i++) {
            soma += matrizBits[i][coluna];
        }
        return soma % 2;
    }

    private static void imprimirMatriz(int[][] matrizBits) {
        for (int[] matrizBit : matrizBits) {
            for (int j = 0; j < 8; j++) {
                System.out.print(matrizBit[j] + " ");
            }
            System.out.println();
        }
    }
}
