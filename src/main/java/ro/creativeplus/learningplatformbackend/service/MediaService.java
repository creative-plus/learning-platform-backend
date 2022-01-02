package ro.creativeplus.learningplatformbackend.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ro.creativeplus.learningplatformbackend.exception.NotAllowedException;
import ro.creativeplus.learningplatformbackend.exception.ObjectAlreadyExistsException;
import ro.creativeplus.learningplatformbackend.exception.ObjectNotFoundException;
import ro.creativeplus.learningplatformbackend.model.Media;
import ro.creativeplus.learningplatformbackend.repository.MediaRepository;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class MediaService {

  private final MediaRepository mediaRepository;

  public MediaService(MediaRepository mediaRepository) {
    this.mediaRepository = mediaRepository;
  }

  public Media getMedia(int id) {
    Optional<Media> mediaOptional = this.mediaRepository.findById(id);
    if(mediaOptional.isEmpty()) {
      throw new ObjectNotFoundException("Media not found.");
    }
    return mediaOptional.get();
  }

  public List<Media> getMedias() {
    return this.mediaRepository.findAll();
  }

  public Media storeFile(MultipartFile file) {
    try {
      String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
      Media FileDB = new Media(fileName, file.getContentType(), file.getBytes());
      return this.mediaRepository.save(FileDB);
    } catch (Exception e) {
      throw new NotAllowedException("Could not upload file.");
    }
  }

  public Media addMedia(Media media) {
    if(media.getId() > 0) {
      Optional<Media> mediaOptional = this.mediaRepository.findById(media.getId());
      if (mediaOptional.isPresent()) {
        throw new ObjectAlreadyExistsException("Media already exists.");
      }
    }
    return this.mediaRepository.save(media);
  }
}
