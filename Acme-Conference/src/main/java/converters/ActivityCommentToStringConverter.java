package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.ActivityComment;

@Component
@Transactional
public class ActivityCommentToStringConverter implements
		Converter<ActivityComment, String> {

	@Override
	public String convert(final ActivityComment activityComment) {
		String result;

		if (activityComment == null)
			result = null;
		else
			result = String.valueOf(activityComment.getId());

		return result;
	}

}
