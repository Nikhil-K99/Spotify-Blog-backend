package com.example.SpotifySpring.mapper;


import com.example.SpotifySpring.dto.TopicDTO;
import com.example.SpotifySpring.enums.TopicType;
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


    public TopicDTO mapToArtist(Topic topic) throws IOException, ParseException, SpotifyWebApiException {
        TopicDTO topicDTO = new TopicDTO();
        topicDTO.setTopicId(topic.getTopicId());
        topicDTO.setTopicType(TopicType.ARTIST);

        GetArtistRequest getArtistRequest = spotifyApi.getArtist(topic.getTopicSpotifyId()).build();
        Artist artist = getArtistRequest.execute();

        topicDTO.setArtistName(artist.getName());
        topicDTO.setPictureUrl(Arrays.stream(artist.getImages()).findFirst().get().getUrl());
        topicDTO.setSpotifyId(artist.getId());

        return topicDTO;
    }

    public TopicDTO mapToAlbum(Topic topic) throws IOException, ParseException, SpotifyWebApiException {
        TopicDTO topicDTO = new TopicDTO();
        topicDTO.setTopicId(topic.getTopicId());
        topicDTO.setTopicType(TopicType.ALBUM);

        GetAlbumRequest getAlbumRequest = spotifyApi.getAlbum(topic.getTopicSpotifyId()).build();
        Album album = getAlbumRequest.execute();

        topicDTO.setAlbumName(album.getName());
        topicDTO.setArtistName(Arrays.stream(album.getArtists()).findFirst().get().getName());
        topicDTO.setPictureUrl(Arrays.stream(album.getImages()).findFirst().get().getUrl());
        topicDTO.setSpotifyId(album.getId());

        return topicDTO;
    }

    public TopicDTO mapToTrack(Topic topic) throws IOException, ParseException, SpotifyWebApiException {
        TopicDTO topicDTO = new TopicDTO();
        topicDTO.setTopicId(topic.getTopicId());
        topicDTO.setTopicType(TopicType.TRACK);

        GetTrackRequest getTrackRequest = spotifyApi.getTrack(topic.getTopicSpotifyId()).build();
        Track track = getTrackRequest.execute();

        topicDTO.setTrackName(track.getName());
        topicDTO.setArtistName(Arrays.stream(track.getArtists()).findFirst().get().getName());
        topicDTO.setAlbumName(track.getAlbum().getName());
        topicDTO.setPictureUrl(Arrays.stream(track.getAlbum().getImages()).findFirst().get().getUrl());
        topicDTO.setSpotifyId(track.getId());

        return topicDTO;
    }

    public TopicDTO mapToDTO(Topic topic) throws ParseException, SpotifyWebApiException, IOException {
        TopicType topicType = topic.getTopicType();
        switch (topicType) {
            case ARTIST:
               return mapToArtist(topic);
            case ALBUM:
                return mapToAlbum(topic);
            case TRACK:
               return mapToTrack(topic);
            default:
                throw new IllegalArgumentException("Unknown topic type: " + topicType);
        }
    }

}
