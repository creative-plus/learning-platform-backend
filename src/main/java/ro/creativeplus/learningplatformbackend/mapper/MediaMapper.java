package ro.creativeplus.learningplatformbackend.mapper;

import org.springframework.stereotype.Component;
import ro.creativeplus.learningplatformbackend.dto.MediaMetaDto;
import ro.creativeplus.learningplatformbackend.model.Media;
import ro.creativeplus.learningplatformbackend.utils.HashIds;

@Component
public class MediaMapper {

  private final HashIds hashIds;

  public MediaMapper(HashIds hashIds) {
    this.hashIds = hashIds;
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
}
