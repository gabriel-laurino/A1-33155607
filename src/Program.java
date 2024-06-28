import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Program {
    public static void main(String[] args) {
        String caminhoEntrada = "./src/alunos.csv";
        String caminhoSaida = "./src/resumo.csv";
        List<Aluno> listaDeAlunos = lerAlunos(caminhoEntrada);
        processarDados(listaDeAlunos, caminhoSaida);
    }

    public static List<Aluno> lerAlunos(String caminhoArquivo) {
        List<Aluno> alunos = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            boolean isHeader = true;
            while ((linha = br.readLine()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }
                String[] dados = linha.split(";");
                String matricula = dados[0];
                String nome = dados[1];
                double nota = Double.parseDouble(dados[2].replace(',', '.'));
                alunos.add(new Aluno(matricula, nome, nota));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return alunos;
    }

    public static void processarDados(List<Aluno> alunos, String caminhoSaida) {
        int totalAlunos = alunos.size();
        int aprovados = 0;
        int reprovados = 0;
        double menorNota = Double.MAX_VALUE;
        double maiorNota = Double.MIN_VALUE;
        double somaNotas = 0.0;

        for (Aluno aluno : alunos) {
            double nota = aluno.getNota();
            somaNotas += nota;
            if (nota >= 6.0) {
                aprovados++;
            } else {
                reprovados++;
            }
            if (nota < menorNota) {
                menorNota = nota;
            }
            if (nota > maiorNota) {
                maiorNota = nota;
            }
        }

        double media = somaNotas / totalAlunos;

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoSaida))) {
            bw.write("Total De Alunos;Aprovados;Reprovados;Menor Nota;Maior Nota;Media Geral");
            bw.newLine();
            bw.write(totalAlunos + ";" + aprovados + ";" + reprovados + ";" + menorNota + ";" + maiorNota + ";" + String.format("%.2f", media));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class Aluno {
    private String matricula;
    private String nome;
    private double nota;

    public Aluno(String matricula, String nome, double nota) {
        this.matricula = matricula;
        this.nome = nome;
        this.nota = nota;
    }

    public String getMatricula() {
        return matricula;
    }

    public String getNome() {
        return nome;
    }

    public double getNota() {
        return nota;
    }
}
