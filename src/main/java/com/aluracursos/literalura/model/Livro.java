package com.aluracursos.literalura.model;

import jakarta.persistence.*;

@Entity
@Table(name = "livros")
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String titulo;
    private String idioma;
    private Double numeroDownloads;

    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Autor autor;

    public Livro() {}

    public Livro(DadosLivro dadosLivro) {
        this.titulo = dadosLivro.titulo();
        this.idioma = dadosLivro.idiomas().get(0); // Pega apenas o primeiro idioma
        this.numeroDownloads = dadosLivro.numeroDownloads();
    }

    public String getTitulo() { return titulo; }
    public void setAutor(Autor autor) { this.autor = autor; }

    @Override
    public String toString() {
        return String.format("----- LIVRO -----\nTítulo: %s\nAutor: %s\nIdioma: %s\nDownloads: %s\n-----------------",
                titulo, (autor != null ? autor.getNome() : "Desconhecido"), idioma, numeroDownloads);
    }
}