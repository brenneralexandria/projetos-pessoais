package com.desenvolvimentoapi.apigerenciadordecompras;

public class Item {

    // Atributos da classe
    protected String id;
    public String nome;
    protected boolean comprado;

    // Construtor
    public Item(String id, String nome) {
        this.id = id;
        this.nome = nome;
        this.comprado = false;
    }

    // Construtor vazio
    public Item() {}

    // Getter e Setter
    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public boolean isComprado() {
        return comprado;
    }

    public void setComprado(boolean comprado) {
        this.comprado = comprado;
    }

    // Exibir o item e seu status atual
    public void exibir() {
        String status = (comprado) ? " [x]" : " [ ]";
        System.out.println(id + ". " + nome + status);
    }
}