package org.mimos.sentiment.application.service.mapper;

import org.mimos.sentiment.application.domain.*;
import org.mimos.sentiment.application.service.dto.ClipExtractDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ClipExtract and its DTO ClipExtractDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ClipExtractMapper extends EntityMapper<ClipExtractDTO, ClipExtract> {


}
