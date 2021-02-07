package br.com.movies.repository;

import java.util.List;

import br.com.movies.model.Movie;
import org.springframework.data.repository.CrudRepository;

public interface MovieRepository extends CrudRepository<Movie, Integer> {

	<Mov extends Movie> List<Mov> saveAll(Iterable<Mov> movie);

	List<Movie> findAll();
}
