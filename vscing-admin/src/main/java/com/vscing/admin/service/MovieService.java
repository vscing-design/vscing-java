package com.vscing.admin.service;

import com.vscing.model.entity.Movie;
import com.vscing.model.entity.MovieProducer;

import java.util.List;

/**
 * MovieService
 *
 * @author vscing
 * @date 2024/12/28 19:18
 */
public interface MovieService {

  boolean initMovie(Movie movie, List<MovieProducer> movieProducers);

}
