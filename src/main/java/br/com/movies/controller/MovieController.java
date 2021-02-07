package br.com.movies.controller;

import java.util.List;

import br.com.movies.model.Movie;
import br.com.movies.model.WinnerResponse;
import br.com.movies.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

	@Autowired
	private MovieService service;

	@GetMapping(value = "")
	public @ResponseBody List<Movie> findAllValues() {
		return service.findAll();
	}

	@GetMapping(value = "/producers")
	public @ResponseBody WinnerResponse mostAndMinorIntervals() {
		return service.findMinimumAndMaximumValues();
	}
}
