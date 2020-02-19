package io.javabrains.moviecatalogservice.resource;



import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import io.javabrains.moviecatalogservice.model.CatalogItem;
import io.javabrains.moviecatalogservice.model.Movie;
import io.javabrains.moviecatalogservice.model.UserRating;

@RestController
@RequestMapping("/catalog")
public class MovieCatResource {
	@Autowired
    private RestTemplate restTemplate;

	@Autowired
	private WebClient.Builder builder;
	
    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {
       
        UserRating userRating = restTemplate.getForObject("http://MOVIE-RATING-SERVICES/rating/userRating/" + userId, UserRating.class);
        return userRating.getRatings().stream()
                .map(rating -> {
					/*
					 * Movie movie = builder.build().get().uri("http://localhost:8081/movies/" +
					 * rating.getMovieId()) .retrieve().bodyToMono(Movie.class).block();
					 */
                	//MONO: In the future going to give u wat u want
                    Movie movie = restTemplate.getForObject("http://MOVIE-INFO-SERVICES/movies/" + rating.getMovieId(), Movie.class);
                    return new CatalogItem(movie.getName(), "Description", rating.getRating());
                })
                .collect(Collectors.toList());

    }
}
