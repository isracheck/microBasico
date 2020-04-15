package com.example.curso.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.curso.entity.Lenguaje;
import com.example.curso.entity.Profesor;
import com.example.curso.model.MProfesorLenguaje;
import com.example.curso.service.ILenguajeService;
import com.example.curso.service.IProfesorService;

@RestController
@RequestMapping("/api")
public class ProfesorLenguajeRestController {

	@Autowired
	private ILenguajeService lenguajeService;
	
	@Autowired
	private IProfesorService profesorService;
	
	@PostMapping("/lenguajes/profesor")
	public ResponseEntity<?> listaLenguajesProfesor(@RequestBody Profesor profesor){
		Profesor profesorDb = profesorService.findById(profesor.getId());
		
		if (profesorDb!= null) {
			Collection<Lenguaje> listaLenguajes = profesorDb.getLenguajes();
			if (listaLenguajes!=null){
				return new ResponseEntity<>(listaLenguajes, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@PostMapping("/saveLenguajeProfesor")
	public ResponseEntity<?> saveLenguajeProfesor(@RequestBody MProfesorLenguaje mProfesorLenguaje){
		Profesor profesorDb = profesorService.findById(mProfesorLenguaje.getProfesor().getId());
		
		if (profesorDb!=null) {
			Lenguaje lenguajeDb = lenguajeService.findLenguajeById(mProfesorLenguaje.getLenguaje().getId());
			profesorDb.addLenguaje(lenguajeDb);
			profesorService.save(profesorDb);
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	
}
