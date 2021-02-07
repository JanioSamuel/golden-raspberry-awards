package br.com.movies.service;

import java.util.List;

import br.com.movies.model.Movie;
import br.com.movies.model.WinnerResponse;

public interface MovieService {

	void saveAll(List<Movie> movies);

	List<Movie> findAll();

	WinnerResponse findMinimumAndMaximumValues();
}
