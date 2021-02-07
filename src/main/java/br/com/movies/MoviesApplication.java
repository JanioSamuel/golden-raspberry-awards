package br.com.movies;

import br.com.movies.util.LoadData;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class MoviesApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(MoviesApplication.class, args);
		context.getBean(LoadData.class).loadMovies();
	}
}
