package br.com.movies.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

import br.com.movies.model.Movie;
import br.com.movies.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
public class LoadData {

	public static final String SEMICOLON = ";";
	public static final String MOVIELIST_CSV = "movielist.csv";

	@Autowired
	private MovieService service;

	public void loadMovies() {
		List<Movie> movies;
		try {
			ClassPathResource classPathResource = new ClassPathResource(MOVIELIST_CSV);
			InputStream inputStream = classPathResource.getInputStream();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

			movies = bufferedReader.lines().skip(1).map(this::mapToMovie).collect(Collectors.toList());

			bufferedReader.close();
			service.saveAll(movies);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Movie mapToMovie(String line) {
		Movie movie = new Movie();
		String[] split = line.split(SEMICOLON);
		movie.setYear(Integer.parseInt(split[0]));
		movie.setTitle(split[1]);
		movie.setStudios(split[2]);
		movie.setProducers(split[3]);

		if (split.length > 4) {
			movie.setWinner(convertToBoolean(split[4]));
		} else {
			movie.setWinner(false);
		}

		return movie;
	}

	private boolean convertToBoolean(String value) {
		return "yes".equalsIgnoreCase(value);
	}
}
