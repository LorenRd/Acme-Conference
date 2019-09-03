
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CategoryRepository;
import domain.Administrator;
import domain.Category;

@Service
@Transactional
public class CategoryService {

	// Managed Repository
	@Autowired
	private CategoryRepository		categoryRepository;

	// Supporting services

	@Autowired
	private AdministratorService	administratorService;


	// Simple CRUD methods

	public Category create(final Category parent) {
		Category result;
		Administrator principal;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		result = new Category();
		Assert.notNull(result);
		result.setParentCategory(parent);

		return result;

	}

	public Collection<Category> findAll() {
		Collection<Category> result;
		result = this.categoryRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Category findOne(final int categoryId) {
		Category result;

		result = this.categoryRepository.findOne(categoryId);

		Assert.notNull(result);

		return result;
	}

	public Category findRootCategory() {
		Category result;

		result = this.categoryRepository.findRootCategory();

		Assert.notNull(result);

		return result;
	}

	public Category save(final Category category) {
		Category result;
		Administrator principal;
		Category parentCategory;
		Collection<Category> childCategories;
		Category root;
		Boolean isNameUniqueRoot;

		Assert.notNull(category);
		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);
		root = this.findRootCategory();
		Assert.isTrue(category.getId() != root.getId());

		result = this.categoryRepository.save(category);
		Assert.notNull(result);

		Collection<Category> forbiddenNamesCategories = new ArrayList<Category>();
		parentCategory = result.getParentCategory();
		forbiddenNamesCategories = this.findChildsFromParentId(parentCategory.getId());
		forbiddenNamesCategories.remove(result);

		isNameUniqueRoot = true;

		for (final Category c : forbiddenNamesCategories)
			if (c.getEnglishName().equals(result.getEnglishName()) || c.getSpanishName().equals(result.getSpanishName())) {
				isNameUniqueRoot = false;
				break;
			}

		Assert.isTrue(isNameUniqueRoot);

		childCategories = new ArrayList<Category>(this.findChildsFromParentId(result.getId()));
		childCategories = this.findChildsFromParentId(result.getId());
		for (final Category c : childCategories) {
			parentCategory = c.getParentCategory();
			if (result.getId() != 0)
				parentCategory = result;
			c.setParentCategory(parentCategory);
		}

		return result;

	}

	public void delete(final Category category) {
		Administrator principal;
		Category root;

		Assert.notNull(category);
		Assert.isTrue(category.getId() != 0);

		principal = this.administratorService.findByPrincipal();

		Assert.notNull(principal);

		root = this.findRootCategory();

		Assert.isTrue(category.getId() != root.getId());

		final Collection<Category> childCategories = new ArrayList<Category>(this.findChildsFromParentId(category.getId()));
		for (final Category child : childCategories) {
			Category parent;
			parent = category.getParentCategory();
			child.setParentCategory(parent);
		}

		this.categoryRepository.delete(category);
	}

	// Other business methods
	public Collection<Category> findChildsFromParentId(final int categoryId) {
		Collection<Category> result;
		result = this.categoryRepository.findChildsFromParentId(categoryId);
		Assert.notNull(result);
		return result;
	}

	public Category findParentFromChildId(final int categoryId) {
		Category result;
		result = this.categoryRepository.findParentFromChildId(categoryId);
		Assert.notNull(result);
		return result;
	}
}
