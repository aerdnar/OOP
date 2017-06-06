package diet;

import java.util.LinkedList;
import java.util.List;

//import diet.Recipe.Ingredient;

/**
 * Represents a complete menu. It consist of packaged products and servings of
 * recipes.
 *
 */
public class Menu implements NutritionalElement {
	private String name;
	private Food food;
	private List<Menu.Recipe> recipes = new LinkedList<>();
	private List<String> products = new LinkedList<>();
	private double calories, proteins, carbs, fat;

	private class Recipe {
		private String recipe;
		private double quantity;

		Recipe(String recipe, double quantity) {
			this.recipe = recipe;
			this.quantity = quantity;
		}

		public String getRecipe() {
			return recipe;
		}

		public double getQuantity() {
			return quantity;
		}
	}

	/**
	 * Menu constructor. The reference {@code food} of type {@link Food} must be
	 * used to retrieve the information about recipes and products
	 * 
	 * @param nome
	 *            unique name of the menu
	 * @param food
	 *            object containing the information about products and recipes
	 */
	public Menu(String name, Food food) {
		this.name = name;
		this.food = food;
	}

	/**
	 * Adds a given serving size of a recipe. The recipe is a name of a recipe
	 * defined in the {@code food} argument of the constructor.
	 * 
	 * @param recipe
	 *            the name of the recipe to be used as ingredient
	 * @param quantity
	 *            the amount in grams of the recipe to be used
	 */
	public void addRecipe(String recipe, double quantity) {
		NutritionalElement tmpMaterial;
		recipes.add(new Menu.Recipe(recipe, quantity));
		tmpMaterial = food.getRecipe(recipe);
		calories += quantity / 100.0 * tmpMaterial.getCalories();
		proteins += quantity / 100.0 * tmpMaterial.getProteins();
		carbs += quantity / 100.0 * tmpMaterial.getCarbs();
		fat += quantity / 100.0 * tmpMaterial.getFat();
	}

	/**
	 * Adds a unit of a packaged product. The product is a name of a product
	 * defined in the {@code food} argument of the constructor.
	 * 
	 * @param product
	 *            the name of the product to be used as ingredient
	 */
	public void addProduct(String product) {
		NutritionalElement tmpProduct;
		products.add(product);
		tmpProduct = food.getProduct(product);
		calories += tmpProduct.getCalories();
		proteins += tmpProduct.getProteins();
		carbs += tmpProduct.getCarbs();
		fat += tmpProduct.getFat();
	}

	public String getName() {
		return name;
	}

	public double getCalories() {
		return calories;
	}

	public double getProteins() {
		return proteins;
	}

	public double getCarbs() {
		return carbs;
	}

	public double getFat() {
		return fat;
	}

	public boolean per100g() {
		// nutritional values are provided for the whole menu.
		return false;
	}
}
