package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.CameraReadyPaperRepository;
import domain.CameraReadyPaper;

@Component
@Transactional
public class StringToCameraReadyPaperConverter implements
		Converter<String, CameraReadyPaper> {

	@Autowired
	CameraReadyPaperRepository cameraReadyPaperRepository;

	@Override
	public CameraReadyPaper convert(final String text) {
		CameraReadyPaper result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.cameraReadyPaperRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}

}
