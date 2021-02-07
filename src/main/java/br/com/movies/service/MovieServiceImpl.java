package br.com.movies.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;

import br.com.movies.model.Movie;
import br.com.movies.model.WinnerData;
import br.com.movies.model.WinnerResponse;
import br.com.movies.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieServiceImpl implements MovieService {

	public static final String COMMA = ",";
	public static final String AND = " and ";
	private final MovieRepository repository;

	EntityManager entityManager;

	@Autowired
	public MovieServiceImpl(MovieRepository repository, EntityManager entityManager) {
		this.repository = repository;
		this.entityManager = entityManager;
	}

	@Override public void saveAll(List<Movie> movies) {
		repository.saveAll(movies);
	}

	@Override public List<Movie> findAll() {
		return repository.findAll();
	}

	@Override public WinnerResponse findMinimumAndMaximumValues() {
		Map<String, List<Integer>> separatedNames = new HashMap<>();
		List<WinnerData> winners = new ArrayList<>();
		List<Movie> movies = entityManager.createQuery("select m from Movie m where m.winner = true order by m.producers").getResultList();
		this.separateProducersNames(separatedNames, movies);

		this.setValidIntervals(separatedNames, winners);

		WinnerData minIntervalWinner = winners.stream().min(Comparator.comparing(WinnerData::getInterval)).orElse(new WinnerData());
		WinnerData maxIntervalWinner = winners.stream().max(Comparator.comparing(WinnerData::getInterval)).orElse(new WinnerData());

		List<WinnerData> minWinnerDataList = new ArrayList<>();
		List<WinnerData> maxWinnerDataList = new ArrayList<>();
		this.checkMultipleMinimumAndMaximumValues(winners, minIntervalWinner, maxIntervalWinner, minWinnerDataList, maxWinnerDataList);

		WinnerResponse response = new WinnerResponse();

		response.setMin(minWinnerDataList);
		response.setMax(maxWinnerDataList);

		return response;
	}

	private void checkMultipleMinimumAndMaximumValues(List<WinnerData> winners, WinnerData minIntervalWinner, WinnerData maxIntervalWinner, List<WinnerData> minWinnerDataList, List<WinnerData> maxWinnerDataList) {
		winners.forEach(w -> {
			if (w.getInterval().equals(minIntervalWinner.getInterval())) {
				minWinnerDataList.add(w);
			}
			if (w.getInterval().equals(maxIntervalWinner.getInterval())) {
				maxWinnerDataList.add(w);
			}
		});
	}

	private void setValidIntervals(Map<String, List<Integer>> separatedNames, List<WinnerData> winners) {
		for (Map.Entry<String, List<Integer>> values : separatedNames.entrySet()) {
			if (values.getValue().size() >= 2) {
				WinnerData winnerData = new WinnerData();
				winnerData.setProducer(values.getKey());
				winnerData.setPreviousWin(Collections.min(values.getValue()));
				winnerData.setFollowingWin(Collections.max(values.getValue()));
				winnerData.setInterval(winnerData.getFollowingWin() - winnerData.getPreviousWin());
				winners.add(winnerData);
			}
		}
	}

	private void separateProducersNames(Map<String, List<Integer>> separatedNames, List<Movie> movies) {
		movies.forEach(movie -> {
			if(movie.getProducers().contains(COMMA)) {
				String[] commaSplitProducer = movie.getProducers().trim().split(COMMA);
				for (String s : commaSplitProducer) {
					separateWithAndKeyword(separatedNames, movie, s);
				}
			} else {
				separateWithAndKeyword(separatedNames, movie, movie.getProducers());
			}
		});
	}

	private void separateWithAndKeyword(Map<String, List<Integer>> separatedNames, Movie movie, String producers) {
		if (producers.contains(AND)) {
			String[] andSplitProducer = producers.split(AND);
			for (String s : andSplitProducer) {
				movie.setProducers(s);
				addToSeparatedNames(separatedNames, movie);
			}
		} else {
			movie.setProducers(producers);
			addToSeparatedNames(separatedNames, movie);
		}
	}

	private void addToSeparatedNames(Map<String, List<Integer>> separatedNames, Movie movie) {
		if(separatedNames.containsKey(movie.getProducers())) {
			separatedNames.get(movie.getProducers().trim()).add(movie.getYear());
		} else {
			List<Integer> i = new ArrayList<>();
			i.add(movie.getYear());
			separatedNames.put(movie.getProducers().trim(), i);
		}
	}
}
