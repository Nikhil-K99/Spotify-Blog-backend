package com.example.SpotifySpring.mapper;


import com.example.SpotifySpring.dto.AlbumDTO;
import com.example.SpotifySpring.dto.ArtistDTO;
import com.example.SpotifySpring.dto.TrackDTO;
import com.example.SpotifySpring.model.Topic;
import com.example.SpotifySpring.repository.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.apache.hc.core5.http.ParseException;
import org.springframework.stereotype.Component;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Album;
import se.michaelthelin.spotify.model_objects.specification.Artist;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.data.albums.GetAlbumRequest;
import se.michaelthelin.spotify.requests.data.artists.GetArtistRequest;
import se.michaelthelin.spotify.requests.data.tracks.GetTrackRequest;

import java.io.IOException;
import java.util.Arrays;

@RequiredArgsConstructor
@Component
public class TopicMapper {

    private final SpotifyApi spotifyApi;
    private final TopicRepository topicRepository;


    public ArtistDTO mapToArtist(Topic topic) throws IOException, ParseException, SpotifyWebApiException {
        ArtistDTO artistDTO = new ArtistDTO();
        artistDTO.setTopicId(topic.getTopicId());

        GetArtistRequest getArtistRequest = spotifyApi.getArtist(topic.getTopicSpotifyId()).build();
        Artist artist = getArtistRequest.execute();

        artistDTO.setName(artist.getName());
        artistDTO.setPictureUrl(Arrays.stream(artist.getImages()).findFirst().get().getUrl());
        artistDTO.setSpotifyId(artist.getId());

        return artistDTO;
    }

    public AlbumDTO mapToAlbum(Topic topic) throws IOException, ParseException, SpotifyWebApiException {
        AlbumDTO albumDTO = new AlbumDTO();
        albumDTO.setTopicId(topic.getTopicId());

        GetAlbumRequest getAlbumRequest = spotifyApi.getAlbum(topic.getTopicSpotifyId()).build();
        Album album = getAlbumRequest.execute();

        albumDTO.setName(album.getName());
        albumDTO.setArtistName(Arrays.stream(album.getArtists()).findFirst().get().getName());
        albumDTO.setPictureUrl(Arrays.stream(album.getImages()).findFirst().get().getUrl());
        albumDTO.setSpotifyId(album.getId());

        return albumDTO;
    }

    public TrackDTO mapToTrack(Topic topic) throws IOException, ParseException, SpotifyWebApiException {
        TrackDTO trackDTO = new TrackDTO();
        trackDTO.setTopicId(topic.getTopicId());

        GetTrackRequest getTrackRequest = spotifyApi.getTrack(topic.getTopicSpotifyId()).build();
        Track track = getTrackRequest.execute();

        trackDTO.setName(track.getName());
        trackDTO.setArtistName(Arrays.stream(track.getArtists()).findFirst().get().getName());
        trackDTO.setAlbumName(track.getAlbum().getName());
        trackDTO.setPictureUrl(Arrays.stream(track.getAlbum().getImages()).findFirst().get().getUrl());
        trackDTO.setSpotifyId(track.getId());

        return trackDTO;
    }


}
