package com.example.anime_api.controllers;

import com.example.anime_api.dtos.AnimeRecordDTO;
import com.example.anime_api.models.AnimeModel;
import com.example.anime_api.repositories.AnimeRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class AnimeController {
    @Autowired
    AnimeRepository animeRepository;

    @GetMapping("/anime")
    public ResponseEntity getAllAnimes() {
        List<AnimeModel> listAnimes = animeRepository.findAll();
        return ResponseEntity.ok(listAnimes);
    }

    @PostMapping("/anime")
    public ResponseEntity saveAnime(@RequestBody @Valid AnimeRecordDTO animeRecordDTO) {
        var newAnime = new AnimeModel();
        newAnime.setTitle(animeRecordDTO.title());
        newAnime.setDescription(animeRecordDTO.description());
        animeRepository.save(newAnime);
        return ResponseEntity.ok("Anime save sucessfully");
    }

    @PutMapping("/anime/{id}")
    public ResponseEntity updateAnime(@PathVariable(value="id") UUID id,
                                      @RequestBody @Valid AnimeRecordDTO animeRecordDTO) {
        Optional<AnimeModel> optionalAnime = animeRepository.findById(id);
        if (optionalAnime.isPresent()) {
            AnimeModel anime = optionalAnime.get();
            anime.setTitle(animeRecordDTO.title());
            anime.setDescription(animeRecordDTO.description());
            return ResponseEntity.ok(animeRepository.save(anime));
        }
        return ResponseEntity.notFound().build();
    }

}
