package br.com.movies.model;

import java.util.List;

public class WinnerResponse {

	List<WinnerData> min;
	List<WinnerData> max;

	public List<WinnerData> getMin() {
		return min;
	}

	public void setMin(List<WinnerData> min) {
		this.min = min;
	}

	public List<WinnerData> getMax() {
		return max;
	}

	public void setMax(List<WinnerData> max) {
		this.max = max;
	}
}
