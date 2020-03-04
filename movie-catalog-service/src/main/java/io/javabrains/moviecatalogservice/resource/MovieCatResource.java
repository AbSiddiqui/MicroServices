package io.javabrains.moviecatalogservice.resource;



import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import io.javabrains.moviecatalogservice.model.CatalogItem;
import io.javabrains.moviecatalogservice.model.Movie;
import io.javabrains.moviecatalogservice.model.UserRating;

@RestController
@RequestMapping("/catalog")
public class MovieCatResource {
	@Autowired
	private RestTemplate restTemplate;
	
	@HystrixCommand(fallbackMethod = "getFallbackCatalog")
	@RequestMapping("/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {
		UserRating userRating = restTemplate.getForObject("http://MOVIE-RATING-SERVICES/rating/userRating/" + userId,
				UserRating.class);
		return userRating.getRatings().stream().map(rating -> {
			Movie movie = restTemplate.getForObject("http://MOVIE-INFO-SERVICES/movies/" + rating.getMovieId(),
					Movie.class);
			return new CatalogItem(movie.getName(), movie.getDescription(), rating.getRating());
		}).collect(Collectors.toList());
	}
	public List<CatalogItem> getFallbackCatalog(@PathVariable("userId") String userId) {
		return Arrays.asList(new CatalogItem("No Movie", "", 0));
	}
}
