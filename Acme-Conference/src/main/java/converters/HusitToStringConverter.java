package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Husit;

@Component
@Transactional
public class HusitToStringConverter implements Converter<Husit, String> {

	@Override
	public String convert(final Husit husit) {
		String result;

		if (husit == null)
			result = null;
		else
			result = String.valueOf(husit.getId());

		return result;
	}
}