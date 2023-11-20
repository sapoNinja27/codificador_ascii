import java.io.*;
import java.util.Objects;
import java.util.Scanner;

public class Bidimensional {

    public static void bidimensional() {
        Scanner scanner = new Scanner(System.in);
        boolean s = Objects.equals(scanner.nextLine(), "1");
        if (s) {
            System.out.print("Digite a palavra: ");
            String palavra = scanner.nextLine();
            salvarMatrizEmArquivo("matriz.txt", encode(palavra));
        } else {
            boolean integro = validateParity(lerMatrizDoArquivo("matriz.txt"));
            System.out.println("Integro? " + (integro ? "sim" : "não"));
        }
    }

    private static void salvarMatrizEmArquivo(String nomeArquivo, int[][] matriz) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo))) {
            writer.write(matriz.length + " " + matriz[0].length);
            writer.newLine();

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

    private static int[][] lerMatrizDoArquivo(String path) {
        int[][] matriz = null;
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String[] dimensoes = br.readLine().split(" ");
            int linhas = Integer.parseInt(dimensoes[0]);
            int colunas = Integer.parseInt(dimensoes[1]);

            matriz = new int[linhas][colunas];

            for (int i = 0; i < linhas; i++) {
                String[] linha = br.readLine().split(" ");
                for (int j = 0; j < colunas; j++) {
                    matriz[i][j] = Integer.parseInt(linha[j]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return matriz;
    }

    public static boolean validateParity(int[][] matrizComParidade) {
        int linhas = matrizComParidade.length;
        int colunas = matrizComParidade[0].length;

        for (int i = 0; i < linhas - 1; i++) {
            int paridadeLinha = 0;
            for (int j = 0; j < colunas - 1; j++) {
                paridadeLinha ^= matrizComParidade[i][j];
            }
            if (paridadeLinha != matrizComParidade[i][colunas - 1]) {
                System.out.println("Erro de paridade na linha " + i);
                return false;
            }
        }

        for (int j = 0; j < colunas - 1; j++) {
            int paridadeColuna = 0;
            for (int i = 0; i < linhas - 1; i++) {
                paridadeColuna ^= matrizComParidade[i][j];
            }
            if (paridadeColuna != matrizComParidade[linhas - 1][j]) {
                System.out.println("Erro de paridade na coluna " + j);
                return false;
            }
        }

        System.out.println("Paridade verificada com sucesso!");
        return true;
    }

    public static int[][] encode(String palavra) {
        int[][] matrizOriginal = convertToBinaryMatrix(palavra);
        int linhas = matrizOriginal.length;
        int colunas = matrizOriginal[0].length;

        int[][] matrizComParidadeLinha = new int[linhas][colunas + 1];
        for (int i = 0; i < linhas; i++) {
            int paridadeLinha = 0;
            for (int j = 0; j < colunas; j++) {
                matrizComParidadeLinha[i][j] = matrizOriginal[i][j];
                paridadeLinha ^= matrizOriginal[i][j];
            }
            matrizComParidadeLinha[i][colunas] = paridadeLinha;
        }

        int[][] matrizComParidade = new int[linhas + 1][colunas + 1];
        for (int j = 0; j < colunas; j++) {
            int paridadeColuna = 0;
            for (int i = 0; i < linhas; i++) {
                paridadeColuna ^= matrizOriginal[i][j];
            }
            matrizComParidade[linhas][j] = paridadeColuna;
        }

        for (int i = 0; i < linhas; i++) {
            System.arraycopy(matrizComParidadeLinha[i], 0, matrizComParidade[i], 0, colunas + 1);
        }

        return matrizComParidade;
    }

    public static int[][] convertToBinaryMatrix(String palavra) {
        int[][] matriz = new int[palavra.length()][8];
        for (int i = 0; i < palavra.length(); i++) {
            char c = palavra.charAt(i);
            for (int j = 0; j < 8; j++) {
                matriz[i][j] = (c >> (7 - j)) & 1;
            }
        }
        return matriz;
    }


}
