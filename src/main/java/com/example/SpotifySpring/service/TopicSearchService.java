package com.example.SpotifySpring.service;

import com.example.SpotifySpring.dto.TopicSearchDTO;
import lombok.AllArgsConstructor;
import org.apache.hc.core5.http.ParseException;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.*;
import se.michaelthelin.spotify.requests.data.search.simplified.SearchAlbumsRequest;
import se.michaelthelin.spotify.requests.data.search.simplified.SearchArtistsRequest;
import se.michaelthelin.spotify.requests.data.search.simplified.SearchTracksRequest;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class TopicSearchService {

    private final SpotifyApi spotifyApi;
    private final SpotifyAPIAuthService spotifyAPIAuthService;


    public List<TopicSearchDTO> getSearchedArtist(String artistQuery) throws IOException, ParseException, SpotifyWebApiException {

        spotifyAPIAuthService.checkTokenExpiration();

        SearchArtistsRequest searchArtistsRequest = spotifyApi.searchArtists(artistQuery)
                .limit(5)
                .build();

        Paging<Artist> artistPaging = searchArtistsRequest.execute();
        return Arrays.stream(artistPaging.getItems())
                .map(this::mapArtistToDTO)
                .collect(toList());
    }

    public List<TopicSearchDTO> getSearchedAlbum(String albumQuery) throws IOException, ParseException, SpotifyWebApiException {

        spotifyAPIAuthService.checkTokenExpiration();

        SearchAlbumsRequest searchAlbumsRequest = spotifyApi.searchAlbums(albumQuery)
                .limit(5)
                .build();

        Paging<AlbumSimplified> albumSimplifiedPaging = searchAlbumsRequest.execute();
        return Arrays.stream(albumSimplifiedPaging.getItems())
                .map(this::mapAlbumToDTO)
                .collect(toList());
    }

    public List<TopicSearchDTO> getSearchedTrack(String trackQuery) throws IOException, ParseException, SpotifyWebApiException {

        spotifyAPIAuthService.checkTokenExpiration();

        SearchTracksRequest searchTracksRequest = spotifyApi.searchTracks(trackQuery)
                .limit(5)
                .build();

        Paging<Track> trackPaging = searchTracksRequest.execute();
        return Arrays.stream(trackPaging.getItems())
                .map(this::mapTrackToDTO)
                .collect(toList());
    }

    public TopicSearchDTO mapArtistToDTO(Artist artist) {
        TopicSearchDTO topicSearchDTO = new TopicSearchDTO();

        topicSearchDTO.setName(artist.getName());
        topicSearchDTO.setPictureURL(Arrays.stream(artist.getImages()).findFirst().get().getUrl());
        topicSearchDTO.setSpotifyId(artist.getId());

        return topicSearchDTO;
    }

    public TopicSearchDTO mapAlbumToDTO(AlbumSimplified albumSimplified) {
        TopicSearchDTO topicSearchDTO = new TopicSearchDTO();

        topicSearchDTO.setName(albumSimplified.getName());
        topicSearchDTO.setPictureURL(Arrays.stream(albumSimplified.getImages()).findFirst().get().getUrl());
        topicSearchDTO.setSpotifyId(albumSimplified.getId());

        return topicSearchDTO;
    }

    public TopicSearchDTO mapTrackToDTO(Track track) {
        TopicSearchDTO topicSearchDTO = new TopicSearchDTO();

        topicSearchDTO.setName(track.getName());
        topicSearchDTO.setPictureURL(Arrays.stream(track.getAlbum().getImages()).findFirst().get().getUrl());
        topicSearchDTO.setSpotifyId(track.getId());

        return topicSearchDTO;
    }

}
