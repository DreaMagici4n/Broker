package pack;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.xml.transform.Source;

public class QuebraSenhaHPC {

    public static void main(String[] args) {
        JFileChooser janela = new JFileChooser();

        //monitora a ação do usuário na árvore de diretório do sistema
        int operacao = janela.showOpenDialog(null);
        if(operacao == JFileChooser.APPROVE_OPTION){
            
            //ref. do arquivo selecionado pelo user...
            File arquivo = janela.getSelectedFile();
            
            //será que o arquivo é ext. .zip???
            if(!arquivo.getAbsolutePath().contains(".zip")){
                JOptionPane.showMessageDialog(null, "O arquivo selecionado deve ter ext. do tipo .zip", "Arquivo incorreto", JOptionPane.WARNING_MESSAGE);
                System.exit(0);
            }
            
            //------------------Agora é com vocês --------------------
            
            int length = 5; // comprimento da string
            int asciiStart = 33; // valor ASCII inicial
            int asciiEnd = 127; // valor ASCII final
            int numThreads = 12; // número de threads
        
            Thread previousThread = null;

            //"Chaveiro(s)" começa a trabalhar aqui ;)
            ChaveiroArquivo trab = new ChaveiroArquivo(arquivo);

            for (int i = 1; i <= numThreads; i++) {
                AchaSenha t = new AchaSenha("", length, previousThread, trab);
                Thread thread = new Thread(t);
                thread.start();
                previousThread = thread;
            }
            
        }else{
            JOptionPane.showMessageDialog(null, "O arquivo não foi selecioando", "Arquivo???", JOptionPane.WARNING_MESSAGE);
        }
    }
}