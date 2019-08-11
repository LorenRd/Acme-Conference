/*
 * ActorToStringConverter.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.CameraReadyPaper;

@Component
@Transactional
public class CameraReadyPaperToStringConverter implements
		Converter<CameraReadyPaper, String> {

	@Override
	public String convert(final CameraReadyPaper cameraReadyPaper) {
		String result;

		if (cameraReadyPaper == null)
			result = null;
		else
			result = String.valueOf(cameraReadyPaper.getId());

		return result;
	}
}
