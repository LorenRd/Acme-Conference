package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.PresentationRepository;
import domain.Presentation;

@Component
@Transactional
public class StringToPresentationConverter implements
		Converter<String, Presentation> {

	@Autowired
	PresentationRepository presentationRepository;

	@Override
	public Presentation convert(final String text) {
		Presentation result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.presentationRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}

}