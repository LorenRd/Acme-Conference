package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.ActivityCommentRepository;
import domain.ActivityComment;

@Component
@Transactional
public class StringToActivityCommentConverter implements
		Converter<String, ActivityComment> {

	@Autowired
	ActivityCommentRepository activityCommentRepository;

	@Override
	public ActivityComment convert(final String text) {
		ActivityComment result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.activityCommentRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}

}
