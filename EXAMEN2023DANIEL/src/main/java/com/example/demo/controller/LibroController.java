package com.example.demo.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.example.demo.entity.*;
import com.example.demo.repository.AutorRepository;
import com.example.demo.repository.EditorialRepostory;
import com.example.demo.repository.LibroRepository;

import dto.LibroDto;
import dto.LibroRequest;
@Controller
public class LibroController {

	@Autowired
	private LibroRepository libroRepository;
	
	@Autowired
	private AutorRepository autorRepository;
	
	@Autowired
	private EditorialRepostory editorialRepository;

	@QueryMapping
	public Iterable<Libro> listarLibros() {
		return libroRepository.findAll();
	}

	@QueryMapping
	public Libro obtenerLibro(@Argument("id") Long id) {
		return libroRepository.findById(id).orElse(null);
	}

	@MutationMapping
	public Libro guardarLibro(@Argument("libro") LibroDto libroInput) {
		Libro libro = new Libro();
		libro.setTitulo(libroInput.getTitulo());
		libro.setDescripcion(libroInput.getDescripcion());
		libro.setPaginas(libroInput.getPaginas());
		libro.setEdicion(libroInput.getEdicion());

		Autor autor = autorRepository.findById(libroInput.getIdautor()).orElse(null);
		Editorial editorial = editorialRepository.findById(libroInput.getIdeditorial()).orElse(null);

		libro.setAutor(autor);
		libro.setEditorial(editorial);

		return libroRepository.save(libro);
	}

	@MutationMapping
	public Libro actualizarLibro(@Argument("id") Long id, @Argument("libro") LibroDto libroInput) {
		Optional<Libro> optionalLibro = libroRepository.findById(id);
		if (optionalLibro.isPresent()) {
			Libro libro = optionalLibro.get();
			libro.setTitulo(libroInput.getTitulo());
			libro.setDescripcion(libroInput.getDescripcion());
			libro.setPaginas(libroInput.getPaginas());
			libro.setEdicion(libroInput.getEdicion());

			Autor autor = autorRepository.findById(libroInput.getIdautor()).orElse(null);
			Editorial editorial = editorialRepository.findById(libroInput.getIdeditorial()).orElse(null);

			libro.setAutor(autor);
			libro.setEditorial(editorial);

			return libroRepository.save(libro);
		}
		return null;
	}

	@MutationMapping
	public boolean eliminarLibro(@Argument("id") Long id) {
		libroRepository.deleteById(id);
		return true;
	}
}