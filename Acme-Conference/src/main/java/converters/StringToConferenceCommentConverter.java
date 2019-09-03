package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.ConferenceCommentRepository;
import domain.ConferenceComment;

@Component
@Transactional
public class StringToConferenceCommentConverter implements
		Converter<String, ConferenceComment> {

	@Autowired
	ConferenceCommentRepository conferenceCommentRepository;

	@Override
	public ConferenceComment convert(final String text) {
		ConferenceComment result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.conferenceCommentRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}

}
