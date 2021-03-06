package de.klaut.backend.controller;

import de.klaut.backend.model.Media;
import de.klaut.backend.service.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/media")
public class MediaController {

    @Autowired
    private MediaService mediaService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Media>> findAll() {
        return ResponseEntity.ok(mediaService.findAll());
    }

    @RequestMapping(value = "/{id}/base64", method = RequestMethod.GET)
    public ResponseEntity<String> loadFile(@PathVariable Long id) {
        Optional<Media> mediaOptional = mediaService.findById(id);
        if (mediaOptional.isPresent()) {

            Media media = mediaOptional.get();
            String encoded = Base64.getEncoder().encodeToString(media.getFile());

            return ResponseEntity.ok(encoded);
        }
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Media> findById(@PathVariable Long id) {
        Optional<Media> mediaOptional = mediaService.findById(id);
        return mediaOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
