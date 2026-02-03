package com.aluracursos.literalura.repository;

import com.aluracursos.literalura.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface LivroRepository extends JpaRepository<Livro, Long> {

    Optional<Livro> findByTituloContainingIgnoreCase(String titulo);

    List<Livro> findByIdioma(String idioma);
}