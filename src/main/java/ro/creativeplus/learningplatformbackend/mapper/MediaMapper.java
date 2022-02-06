package ro.creativeplus.learningplatformbackend.mapper;

import org.springframework.stereotype.Component;
import ro.creativeplus.learningplatformbackend.dto.MediaMetaDto;
import ro.creativeplus.learningplatformbackend.exception.ObjectNotFoundException;
import ro.creativeplus.learningplatformbackend.model.Media;
import ro.creativeplus.learningplatformbackend.service.MediaService;
import ro.creativeplus.learningplatformbackend.utils.HashIds;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class MediaMapper {

  private final HashIds hashIds;
  private final MediaService mediaService;

  public MediaMapper(HashIds hashIds, MediaService mediaService) {
    this.hashIds = hashIds;
    this.mediaService = mediaService;
  }

  public MediaMetaDto toDto(Media media) {
    MediaMetaDto dto = new MediaMetaDto();
    dto.setId(media.getId());
    dto.setMimeType(media.getMimeType());
    dto.setName(media.getName());
    dto.setUrl(this.toUrl(media));
    return dto;
  }

  public String toUrl(Media media) {
    return "/media/" + this.hashIds.encodeId(media.getId());
  }

  public Media toMedia(String url) {
    Pattern URL_PATTERN = Pattern.compile("/media/([a-zA-Z0-9]*)");
    Matcher m = URL_PATTERN.matcher(url);
    try {
      if (m.find()) {
        String hashId = m.group(1);
        int id = this.hashIds.decodeId(hashId);
        return this.mediaService.getMedia(id);
      }
    } catch (Exception e) {
      throw new ObjectNotFoundException("Media not found.");
    }
    throw new ObjectNotFoundException("Media not found.");
  }
}
