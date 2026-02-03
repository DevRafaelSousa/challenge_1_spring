package com.aluracursos.literalura.principal;

import com.aluracursos.literalura.model.*;
import com.aluracursos.literalura.repository.AutorRepository;
import com.aluracursos.literalura.repository.LivroRepository;
import com.aluracursos.literalura.service.ConsumoApi;
import com.aluracursos.literalura.service.ConverteDados;

import java.util.Scanner;

public class Principal {
    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private final String ENDERECO = "https://gutendex.com/books/?search=";

    private LivroRepository repositoryLivro;
    private AutorRepository repositoryAutor;

    public Principal(LivroRepository repositoryLivro, AutorRepository repositoryAutor) {
        this.repositoryLivro = repositoryLivro;
        this.repositoryAutor = repositoryAutor;
    }

    public void exibeMenu() {
        var opcao = -1;
        while (opcao != 0) {
            var menu = """
                    ***************************************************
                    1 - Buscar livro pelo título
                    2 - Listar livros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos em um determinado ano
                    5 - Listar livros em um determinado idioma
                    0 - Sair
                    ***************************************************
                    """;

            System.out.println(menu);
            try {
                opcao = leitura.nextInt();
                leitura.nextLine();
            } catch (Exception e) {
                System.out.println("Opção inválida.");
                leitura.nextLine();
                continue;
            }

            switch (opcao) {
                case 1 -> buscarLivroWeb();
                case 2 -> listarLivrosRegistrados();
                case 3 -> listarAutoresRegistrados();
                case 4 -> listarAutoresVivosNoAno();
                case 5 -> listarLivrosPorIdioma();
                case 0 -> System.out.println("Encerrando...");
                default -> System.out.println("Opção inválida!");
            }
        }
    }

    private void buscarLivroWeb() {
        System.out.println("Digite o título do livro:");
        var nomeLivro = leitura.nextLine();
        var json = consumo.obterDados(ENDERECO + nomeLivro.replace(" ", "%20"));
        DadosResposta dados = conversor.obterDados(json, DadosResposta.class);

        if (dados.resultado() != null && !dados.resultado().isEmpty()) {
            DadosLivro dadosLivro = dados.resultado().get(0);

            if (repositoryLivro.findByTituloContainingIgnoreCase(dadosLivro.titulo()).isPresent()) {
                System.out.println("Livro já registrado.");
                return;
            }

            DadosAutor dadosAutor = dadosLivro.autores().get(0);
            Autor autor = repositoryAutor.findByNomeContainingIgnoreCase(dadosAutor.nome())
                    .orElseGet(() -> new Autor(dadosAutor.nome(),
                            dadosAutor.anoNascimento(),
                            dadosAutor.anoFalecimento()));

            Livro livro = new Livro(dadosLivro);
            livro.setAutor(autor);

            repositoryAutor.save(autor);
            repositoryLivro.save(livro);

            System.out.println(livro);
        } else {
            System.out.println("Livro não encontrado.");
        }
    }

    private void listarLivrosRegistrados() {
        repositoryLivro.findAll().forEach(System.out::println);
    }

    private void listarAutoresRegistrados() {
        repositoryAutor.findAll().forEach(System.out::println);
    }

    private void listarAutoresVivosNoAno() {
        System.out.println("Digite o ano:");
        var ano = leitura.nextInt();
        leitura.nextLine();
        repositoryAutor.buscarAutoresVivosNoAno(ano).forEach(System.out::println);
    }

    private void listarLivrosPorIdioma() {
        System.out.println("Idiomas disponíveis: es, en, fr, pt");
        var idioma = leitura.nextLine();
        repositoryLivro.findByIdioma(idioma).forEach(System.out::println);
    }
}