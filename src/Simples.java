import java.util.Objects;
import java.util.Scanner;

public class Simples {

    public static void simples(){
        Scanner scanner = new Scanner(System.in);
        boolean s = Objects.equals(scanner.nextLine(), "1");
        System.out.print("Digite a palavra: ");
        String palavra = scanner.nextLine();
        if(s){

            String palavraCodificada = codificarParidadeASCII(palavra);

            System.out.println("Palavra codificada: " + palavraCodificada);
        }
        else{

            boolean integro = verificarIntegridade(palavra);

            System.out.println("Integro? " + (integro ? "sim" : "não"));
        }
    }

    public static boolean verificarIntegridade(String bits) {
        int contadorBitsUm = 0;
        for (int i = 0; i < bits.length() - 1; i++) {
            if (bits.charAt(i) == '1') {
                contadorBitsUm++;
            }
        }
        int bitParidade = Character.getNumericValue(bits.charAt(bits.length() - 1));
        return (contadorBitsUm % 2 == 0 && bitParidade == 0) || (contadorBitsUm % 2 != 0 && bitParidade == 1);
    }

    public static String codificarParidadeASCII(String palavra) {
        StringBuilder palavraCodificada = new StringBuilder();

        for (char c : palavra.toCharArray()) {
            String bits = Integer.toBinaryString(c);

            int contadorBitsUm = contarBitsUm(bits);

            if (contadorBitsUm % 2 == 0) {
                bits = "0" + bits;
            } else {
                bits = "1" + bits;
            }

            palavraCodificada.append(bits).append(" ");
        }

        return palavraCodificada.toString();
    }

    public static int contarBitsUm(String bits) {
        int contador = 0;
        for (int i = 0; i < bits.length(); i++) {
            if (bits.charAt(i) == '1') {
                contador++;
            }
        }
        return contador;
    }
}