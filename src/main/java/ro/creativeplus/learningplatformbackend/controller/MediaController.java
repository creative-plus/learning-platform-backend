package ro.creativeplus.learningplatformbackend.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ro.creativeplus.learningplatformbackend.dto.MediaMetaDto;
import ro.creativeplus.learningplatformbackend.mapper.MediaMapper;
import ro.creativeplus.learningplatformbackend.model.Media;
import ro.creativeplus.learningplatformbackend.service.MediaService;
import ro.creativeplus.learningplatformbackend.utils.HashIds;

import javax.annotation.security.RolesAllowed;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/media")
public class MediaController {

  private final MediaService mediaService;
  private final HashIds hashIds;
  private final MediaMapper mediaMapper;

  public MediaController(MediaService mediaService, HashIds hashIds, MediaMapper mediaMapper) {
    this.mediaService = mediaService;
    this.hashIds = hashIds;
    this.mediaMapper = mediaMapper;
  }

  @RolesAllowed("TRAINER")
  @PostMapping()
  public ResponseEntity<MediaMetaDto> uploadFile(@RequestParam("file") MultipartFile file) {
    Media media = this.mediaService.storeFile(file);
    return ResponseEntity.ok().body(this.mediaMapper.toDto(media));
  }

  @GetMapping("/{hashId}")
  public ResponseEntity<byte[]> getFile(@PathVariable String hashId) {
    int id = this.hashIds.decodeId(hashId);
    Media media = this.mediaService.getMedia(id);
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + media.getName() + "\"")
        .body(media.getContent());
  }

  @RolesAllowed("TRAINER")
  @GetMapping("/{hashId}/meta")
  public ResponseEntity<MediaMetaDto> getFileMeta(@PathVariable String hashId) {
    int id = this.hashIds.decodeId(hashId);
    Media media = this.mediaService.getMedia(id);
    return ResponseEntity.ok().body(this.mediaMapper.toDto(media));
  }

  @RolesAllowed("TRAINER")
  @GetMapping()
  public ResponseEntity<List<MediaMetaDto>> getFiles() {
    return ResponseEntity.ok().body(
        this.mediaService.getMedias().stream()
            .map(this.mediaMapper::toDto)
            .collect(Collectors.toList())
    );
  }


}
