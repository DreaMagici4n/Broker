package pack;

import java.io.File;
import java.util.Stack;

// import javax.print.attribute.PrintServiceAttribute;

// public class AchaSenha implements Runnable {
//     private String prefix;
//     private int length;
//     private int asciiStart;
//     private int asciiEnd;
//     private String senha = "AB";
//     private Boolean status = false;
//     private Thread previousThread;

//     public AchaSenha (String prefix, int length, int asciiStart, int asciiEnd) {
//         this.prefix = prefix;
//         this.length = length;
//         this.asciiStart = asciiStart;
//         this.asciiEnd = asciiEnd;
//     }

//     public AchaSenha (Thread previousThread) {
//         this.previousThread = previousThread;
//     }
//     @Override
//     public void run() {
//         try {
//             if (previousThread != null) {
//                 previousThread.join();
//                 generateSequences(prefix, length, asciiStart, asciiEnd);
//             }
//             // Faça algo aqui
//         } catch (InterruptedException e) {
//             e.printStackTrace();
//         }
//     }

//     private void generateSequences(String prefix, int length, int asciiStart, int asciiEnd) {
//         if (length == 0) {
//             if (prefix.equals(this.senha)) {
//                 this.status = true;
//                 System.out.print("ACHEI");
//                 return;
//             } 
//             // sequência completa gerada, faça algo com ela aqui
//             System.out.println(prefix);
//             return;
//         }

//         for (int i = asciiStart; i <= asciiEnd; i++) {
//             if (this.status)
//                 break;
//             String newPrefix = prefix + (char) i;
//             generateSequences(newPrefix, length - 1, asciiStart, asciiEnd);
//         }
//     }
// }

public class AchaSenha implements Runnable {
    private String prefix;
    private int maxLength;
    private Thread previousThread;
    private static Boolean status = false;
    private ChaveiroArquivo trab;

    public AchaSenha(String prefix, int maxLength, Thread previousThread, ChaveiroArquivo chaveiro) {
        this.prefix = prefix;
        this.maxLength = maxLength;
        this.previousThread = previousThread;
        this.trab = chaveiro;
    }

    @Override
    public void run() {
        try {
            if (previousThread != null) {
                previousThread.join();
            }

            Stack<String> stack = new Stack<>();
            stack.push(prefix);
            while (!stack.isEmpty() && !this.status) {
                String current = stack.pop();
                System.out.println(current);
                if (current.length() == maxLength) {
                    //executa se a senha estiver correta (a senha tem tamanho de 5 caracteres)
                    if(trab.tentaSenha(current)){
                        this.status = true;
                        System.out.println("Senha correta");
                    }
                } else {
                    // Testar todas as possibilidades
                    for (int i = 32; i < 127; i++) {
                        String next = current + (char) i;
                        stack.push(next);
                    }
                }    
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}