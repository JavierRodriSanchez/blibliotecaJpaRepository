package com.castelaofp.biblioteca.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.castelaofp.biblioteca.dto.EjemplarDto;
import com.castelaofp.biblioteca.dto.LibroDto;
import com.castelaofp.biblioteca.mapper.EjemplarMapper;
import com.castelaofp.biblioteca.mapper.LibroMapper;
import com.castelaofp.biblioteca.model.Ejemplar;
import com.castelaofp.biblioteca.model.Libro;
import com.castelaofp.biblioteca.repository.EjemplarRepository;
import com.castelaofp.biblioteca.repository.LibroRepository;
import com.castelaofp.biblioteca.service.exceptions.NotFoundException;

//TODO: A completar por el alumno
@Service
public class BibliotecaService {

	private static Logger LOG = LoggerFactory.getLogger(BibliotecaService.class);

	@Autowired
	private LibroRepository libroRepository;

	@Autowired
	private EjemplarRepository ejemplarRepository;

	/**
	 * Crea un nuevo libro
	 * 
	 * @param libro
	 * @return
	 */
	public LibroDto createLibro(LibroDto libroDto) {
		Libro libro =	LibroMapper.toEntity(libroDto);	
		LibroDto dtoLibro =	LibroMapper.toDto(libroRepository.save(libro));
		return dtoLibro;
	};

	/**
	 * Crea un ejemplar nuevo asociado al libro recibida como argumento
	 * 
	 * Devuelve el ejemplar recien creado
	 * Devuelve una excepcion NotFound sino existe el libro.
	 * @param libroId
	 * @param ejemplarDto
	 * @return
	 * @throws NotFoundException
	 * @throws UploadException
	 */
	public EjemplarDto createEjemplar(Long libroId, EjemplarDto ejemplarDto) throws NotFoundException {
	Ejemplar ejemplar =	EjemplarMapper.toEntity(ejemplarDto);
	EjemplarDto resultado =	 EjemplarMapper.toDto(ejemplarRepository.save(ejemplar)); 
	
	if(libroId != resultado.getId()) {
		throw new NotFoundException("No encontrado el id :"+libroId);
	}
		
		return resultado;

	}

	/**
	 * Devuelve la lista con todos los libros
	 * 
	 * @return
	 */
	public List<Libro> findAllLibros() {
	return	libroRepository.findAll();
	}

	/**
	 * Para el id proporcionado, devuelve el libro si existe
	 * 
	 * @param libroId
	 * @return
	 */
	public Optional<Libro> getById(Long libroId) {
	return	libroRepository.findById(libroId);

	}
}