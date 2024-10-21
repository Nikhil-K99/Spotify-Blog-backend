package com.example.SpotifySpring.service;

import com.example.SpotifySpring.dto.TopicDTO;
import com.example.SpotifySpring.enums.TopicType;
import com.example.SpotifySpring.exceptions.TopicNotFoundException;
import com.example.SpotifySpring.mapper.TopicMapper;
import com.example.SpotifySpring.model.Topic;
import com.example.SpotifySpring.repository.TopicRepository;
import lombok.AllArgsConstructor;
import org.apache.hc.core5.http.ParseException;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.io.IOException;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class TopicService {

    private final TopicRepository topicRepository;
    private final TopicMapper topicMapper;


    public List<TopicDTO> getAllArtists() {
        return topicRepository.findAllByTopicType(TopicType.ARTIST)
                .stream()
                .map(topic -> {
                    try {
                        return topicMapper.mapToArtist(topic);
                    } catch (IOException | ParseException | SpotifyWebApiException e) {
                        throw new RuntimeException("Error mapping topic to ArtistDTO", e);
                    }
                })
                .collect(toList());
    }

    public List<TopicDTO> getAllAlbums() {
        return topicRepository.findAllByTopicType(TopicType.ALBUM)
                .stream()
                .map(topic -> {
                    try {
                        return topicMapper.mapToAlbum(topic);
                    } catch (IOException | ParseException | SpotifyWebApiException e) {
                        throw new RuntimeException("Error mapping topic to AlbumDTO", e);
                    }
                })
                .collect(toList());
    }

    public List<TopicDTO> getAllTracks() {
        return topicRepository.findAllByTopicType(TopicType.TRACK)
                .stream()
                .map(topic -> {
                    try {
                        return topicMapper.mapToTrack(topic);
                    } catch (IOException | ParseException | SpotifyWebApiException e) {
                        throw new RuntimeException("Error mapping topic to TrackDTO", e);
                    }
                })
                .collect(toList());
    }
    public TopicDTO getArtistById(Long topicId) throws ParseException, SpotifyWebApiException, IOException {
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new TopicNotFoundException(topicId.toString()));

        return topicMapper.mapToArtist(topic);
    }

    public TopicDTO getAlbumById(Long topicId) throws ParseException, SpotifyWebApiException, IOException {
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new TopicNotFoundException(topicId.toString()));

        return topicMapper.mapToAlbum(topic);
    }

    public TopicDTO getTrackById(Long topicId) throws ParseException, SpotifyWebApiException, IOException {
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new TopicNotFoundException(topicId.toString()));

        return topicMapper.mapToTrack(topic);
    }
}
