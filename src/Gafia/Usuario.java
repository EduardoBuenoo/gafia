package Gafia;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Random;
import Vehicles.Veiculo;

public class Usuario {
    private int id;
    private String nome;
    private String sobrenome;
    private String cpf;
    private String dtnasc;
    private String email;
    private String telefone;
    private String senha;
    private int estadoId;

public Usuario(){
    
       }
       
    public Usuario(String nome, String sobrenome, String cpf, String nascimento,
                    String email, String phone, String senha, int estadoId){
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.cpf = cpf;
        this.dtnasc = nascimento;
        this.email = email;
        this.telefone = phone;
        this.senha = senha;
        this.estadoId = estadoId;
    }
    
    public Usuario(int id, String nome, String sobrenome, String cpf, String nascimento,
                    String email, String phone, String senha, int estadoId){
        this.id = id;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.cpf = cpf;
        this.dtnasc = nascimento;
        this.email = email;
        this.telefone = phone;
        this.senha = senha;
        this.estadoId = estadoId;
    }
       

    public int getId(){
        return id;
    }
    public String getNome(){
        return nome;
    }
    public String getSobrenome(){
        return sobrenome;
    }
    public String getCPF(){
           return cpf;
    }
    public String getDataNasc(){
           return dtnasc;
    }
    public String getEmail(){
           return email;
    }
    public String getTelefone(){
           return telefone;
    }
    public String getSenha(){
        return senha;
    }
    public int getEstadoId() {
        return estadoId;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    public void estadoId(int estadoId) {
        this.estadoId = estadoId;
    }
    
    public void exibirUsuario(){
        System.out.println("--------------------------------------------------------------------");
        System.out.println("ID: " + id);
        System.out.println("Nome: " + nome);
        System.out.println("Sobrenome: " + sobrenome);
        System.out.println("CPF: " + cpf);
        System.out.println("Data de Nascimento: " + dtnasc);
        System.out.println("Email: " + email);
        System.out.println("Telefone: " + telefone);
        System.out.println("--------------------------------------------------------------------");
    }

    private List<Veiculo> veiculosEscolhidos = new ArrayList<>();

    public void adicionarVeiculo(Veiculo v) {
        veiculosEscolhidos.add(v);
    }

    public void listarVeiculos() {
        if (veiculosEscolhidos.isEmpty()) {
            System.out.println("Nenhum veículo cadastrado.");
        } else {
            veiculosEscolhidos.forEach(v -> System.out.println(v));
        }
    }
    
    public List<Veiculo> getVeiculos() {
        return veiculosEscolhidos;
    }

}

    

