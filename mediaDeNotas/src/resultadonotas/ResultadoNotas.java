
package resultadonotas;

import javax.swing.JOptionPane;

public class ResultadoNotas {

    public static void main(String[] args) {
        
        // Vetor de alunos.
        String[] alunos = {"Brenner", "Robert", "Ivan"};
        
        // Matriz de notas.
        float[][] notas = new float[3][4];
        
        float somaNotas, mediaAluno;
        String statusAluno;
        
        for(int i = 0; i < 3; i++){
            System.out.println("Aluno: " + alunos[i]);
            
            somaNotas = 0;
            mediaAluno = 0;
            
            for(int j = 0; j < 4; j++) {
                notas[i][j] = Float.parseFloat(JOptionPane.showInputDialog("Informe a nota n° " + (j+1) + " do aluno " + alunos[i]));              
                somaNotas = somaNotas + notas[i][j];
            }
            mediaAluno = somaNotas/4;
            if(mediaAluno < 5.5) {
                statusAluno = "Reprovado";
            } else if (mediaAluno >= 5.5 && mediaAluno <= 7.5) {
                statusAluno = "Recuperação";
            } else {
                statusAluno = "APROVADO";
            }
            
            System.out.println("Média " + mediaAluno + " Status: " + statusAluno);
        }

    }
    
}
