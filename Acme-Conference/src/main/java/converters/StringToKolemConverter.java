
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.KolemRepository;
import domain.Kolem;

@Component
@Transactional
public class StringToKolemConverter implements Converter<String, Kolem> {

	@Autowired
	KolemRepository	kolemRepository;


	@Override
	public Kolem convert(final String text) {
		Kolem result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.kolemRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}

}
