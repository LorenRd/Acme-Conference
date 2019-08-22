
package controllers.any;

import java.util.Arrays;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AuthorService;
import services.CustomisationService;
import controllers.AbstractController;
import domain.Author;

@Controller
@RequestMapping("/author")
public class AuthorController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private AuthorService			authorService;

	@Autowired
	private CustomisationService	customisationService;


	// Display ---------------------------------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam(required = false) final Integer authorId) {
		final ModelAndView result;
		Author author = new Author();

		if (authorId == null)
			author = this.authorService.findByPrincipal();
		else
			author = this.authorService.findOne(authorId);

		result = new ModelAndView("author/display");
		result.addObject("author", author);

		return result;

	}

	// Register ---------------------------------------------------------------

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Author author;

		author = this.authorService.create();
		result = this.createEditModelAndView(author);

		return result;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "register")
	public ModelAndView register(@ModelAttribute("author") @Valid Author author, final BindingResult binding) {
		ModelAndView result;

		try {
			if (binding.hasErrors()) {
				for (final ObjectError e : binding.getAllErrors())
					System.out.println(e.getObjectName() + " error [" + e.getDefaultMessage() + "] " + Arrays.toString(e.getCodes()));
				result = this.createEditModelAndView(author);
			} else {
				author = this.authorService.save(author);
				result = new ModelAndView("welcome/index");
			}
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(author, "author.commit.error");
		}

		return result;
	}

	// Edit ---------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		Author author;

		author = this.authorService.findByPrincipal();
		Assert.notNull(author);
		result = this.editModelAndView(author);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Author author, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = this.editModelAndView(author);
			for (final ObjectError e : binding.getAllErrors())
				System.out.println(e.getObjectName() + " error [" + e.getDefaultMessage() + "] " + Arrays.toString(e.getCodes()));
		} else
			try {
				this.authorService.save(author);
				result = new ModelAndView("redirect:/author/display.do");
			} catch (final Throwable oops) {
				result = this.editModelAndView(author, "author.commit.error");
			}
		return result;
	}

	// Ancillary methods ------------------------------------------------------

	private ModelAndView editModelAndView(final Author author) {
		ModelAndView result;
		result = this.editModelAndView(author, null);
		return result;
	}

	private ModelAndView editModelAndView(final Author author, final String messageCode) {
		ModelAndView result;
		String countryCode;

		countryCode = this.customisationService.find().getCountryCode();

		result = new ModelAndView("author/edit");
		result.addObject("author", author);
		result.addObject("countryCode", countryCode);
		result.addObject("message", messageCode);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Author author) {
		ModelAndView result;
		result = this.createEditModelAndView(author, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Author author, final String message) {
		ModelAndView result;
		String countryCode;

		countryCode = this.customisationService.find().getCountryCode();
		result = new ModelAndView("author/register");

		result.addObject("author", author);
		result.addObject("actionURI", "author/edit.do");
		result.addObject("redirectURI", "welcome/index.do");
		result.addObject("countryCode", countryCode);

		result.addObject("message", message);

		return result;
	}

}
