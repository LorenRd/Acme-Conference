package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.HusitRepository;
import domain.Husit;

@Component
@Transactional
public class StringToHusitConverter implements Converter<String, Husit> {

	@Autowired
	HusitRepository husitRepository;

	@Override
	public Husit convert(final String text) {
		Husit result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.husitRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}

}