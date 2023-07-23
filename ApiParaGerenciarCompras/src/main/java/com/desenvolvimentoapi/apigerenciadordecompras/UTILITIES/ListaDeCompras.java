package com.desenvolvimentoapi.apigerenciadordecompras.UTILITIES;

import com.desenvolvimentoapi.apigerenciadordecompras.Item;
import com.desenvolvimentoapi.apigerenciadordecompras.ItemComQtd;
import com.desenvolvimentoapi.apigerenciadordecompras.DATABASE.ConexaoDB;

import java.sql.*;
import java.util.Scanner;

public class ListaDeCompras {

    // Atributos da classe
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private Scanner scanner;
    private int nextId;

    // Construtor
    public ListaDeCompras() {
        ConexaoDB conexaoDB = new ConexaoDB();
        connection = conexaoDB.getConnection();
        scanner = new Scanner(System.in);
        nextId = 1;
    }

    // Adicionar item à lista
    public void adicionarItem() {
        try {
            System.out.print("Digite o nome do item: ");
            String nome = scanner.nextLine();
            System.out.println("1 - Item normal");
            System.out.println("2 - Item com quantidade");
            System.out.print("Escolha o tipo de item: ");
            int opcao = Integer.parseInt(scanner.nextLine());

            String id = String.valueOf(nextId++);
            Item item;

            // Verifica se a tabela items existe
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet tables = metaData.getTables(null, null, "items", null);
            if (!tables.next()) {
                // Cria a tabela items se ela não existir
                String createTableSql = "CREATE TABLE items (" +
                        "id VARCHAR(255) NOT NULL," +
                        "nome VARCHAR(255) NOT NULL," +
                        "quantidade INTEGER," +
                        "comprado BOOLEAN DEFAULT FALSE," +
                        "PRIMARY KEY (id)" +
                        ")";
                preparedStatement = connection.prepareStatement(createTableSql);
                preparedStatement.executeUpdate();
            }

            if (opcao == 1) {
                // Caso a opção seja igual a 1, será criado um item com os atributos id e nome.
                item = new Item(id, nome);
            } else if (opcao == 2) {
                System.out.print("Digite a quantidade: ");
                int quantidade = Integer.parseInt(scanner.nextLine());
                // Caso a opção seja igual a 2, será criado um item com os atributos id, nome e quantidade.
                item = new ItemComQtd(id, nome, quantidade);
            } else {
                System.out.println("Opção inválida.");
                return;
            }

            // Armazena o item no banco de dados
            String sql = "INSERT INTO items (id, nome, quantidade) VALUES (?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            preparedStatement.setString(2, nome);
            if (item instanceof ItemComQtd) {
                int quantidade = ((ItemComQtd) item).getQuantidade();
                preparedStatement.setInt(3, quantidade);
            } else {
                preparedStatement.setNull(3, Types.INTEGER);
            }
            preparedStatement.executeUpdate();

            System.out.println("Item adicionado à lista com o ID: " + id);
        } catch (SQLException e) {
            System.out.println("Erro ao adicionar item: " + e.getMessage());
        }
    }

    // Remover item da lista
    public void removerItem() {
        try {
            System.out.print("Digite o ID do item a ser removido: ");
            String id = scanner.nextLine();

            String sql = "DELETE FROM items WHERE id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            int rowsDeleted = preparedStatement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Item removido da lista.");
            } else {
                System.out.println("ID inválido.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao remover item: " + e.getMessage());
        }
    }

    // Editar item da lista
    public void editarItem() {
        try {
            System.out.print("Digite o ID do item a ser editado: ");
            String id = scanner.nextLine();

            String sql = "SELECT * FROM items WHERE id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String nomeAtual = resultSet.getString("nome");
                System.out.println("Nome atual do item: " + nomeAtual);
                System.out.print("Digite o novo nome do item: ");
                String novoNome = scanner.nextLine();

                sql = "UPDATE items SET nome = ? WHERE id = ?";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, novoNome);
                preparedStatement.setString(2, id);
                preparedStatement.executeUpdate();

                System.out.println("Item editado.");
            } else {
                System.out.println("ID inválido.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao editar item: " + e.getMessage());
        }
    }

    // Marcar item como comprado
    public void marcarComoComprado() {
        try {
            System.out.print("Digite o ID do item a ser marcado como comprado: ");
            String id = scanner.nextLine();

            String sql = "SELECT * FROM items WHERE id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                boolean compradoAtual = resultSet.getBoolean("comprado");
                if (compradoAtual) {
                    System.out.println("O item já está marcado como comprado.");
                } else {
                    sql = "UPDATE items SET comprado = true WHERE id = ?";
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, id);
                    preparedStatement.executeUpdate();

                    System.out.println("Item marcado como comprado.");
                }
            } else {
                System.out.println("ID inválido.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao marcar item como comprado: " + e.getMessage());
        }
    }

    // Exibir lista de compras
    public void exibirLista() {
        try {
            String sql = "SELECT * FROM items";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            System.out.println("Lista de Compras:");
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String nome = resultSet.getString("nome");
                int quantidade = resultSet.getInt("quantidade");
                boolean comprado = resultSet.getBoolean("comprado");

                if (quantidade > 0) {
                    Item item = new ItemComQtd(id, nome, quantidade);
                    item.setComprado(comprado);
                    item.exibir();
                } else {
                    Item item = new Item(id, nome);
                    item.setComprado(comprado);
                    item.exibir();
                }
            }
            System.out.println();
        } catch (SQLException e) {
            System.out.println("Erro ao exibir lista de compras: " + e.getMessage());
        }
    }

    // Executa o programa
    public void executar() {
        int opcao = 0; // Contador do loop
        while (opcao != 6) {
            System.out.println("1 - Adicionar item");
            System.out.println("2 - Remover item");
            System.out.println("3 - Editar item");
            System.out.println("4 - Marcar item como comprado");
            System.out.println("5 - Exibir lista de compras");
            System.out.println("6 - Sair");
            System.out.print("Escolha uma opção: ");
            opcao = Integer.parseInt(scanner.nextLine());

            switch (opcao) {
                case 1:
                    adicionarItem();
                    break;
                case 2:
                    removerItem();
                    break;
                case 3:
                    editarItem();
                    break;
                case 4:
                    marcarComoComprado();
                    break;
                case 5:
                    exibirLista();
                    break;
                case 6:
                    System.out.println("Encerrando o programa...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }
}