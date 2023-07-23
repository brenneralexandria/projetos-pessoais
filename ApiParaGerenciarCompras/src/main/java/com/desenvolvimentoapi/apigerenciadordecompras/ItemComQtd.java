package com.desenvolvimentoapi.apigerenciadordecompras;

// Classe para itens que têm quantidade (subclasse de Item)
public class ItemComQtd extends Item {

    // Atributos da classe
    private int quantidade;

    // Construtor utilizando os atributos da classe Item
    public ItemComQtd(String id, String nome, int quantidade) {
        super(id, nome);
        this.quantidade = quantidade;
    }

    // Construtor vazio
    public ItemComQtd() {}

    // Getter de quantidade
    public int getQuantidade() {
        return quantidade;
    }

    // Sobrescrevendo o método exibir
    @Override
    public void exibir() {
        String status = (comprado) ? " [x]" : " [ ]";
        System.out.println(id + ". " + nome + " (x" + quantidade + ")" + status);
    }
}