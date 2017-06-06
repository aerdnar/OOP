package diet;

import java.util.LinkedList;
import java.util.List;

/**
 * Represent a recipe of the diet.
 * 
 * A recipe consists of a a set of ingredients that are given amounts of raw
 * materials. The overall nutritional values of a recipe can be computed on the
 * basis of the ingredients' values and are expressed per 100g
 *  
 *
 */
public class Recipe implements NutritionalElement {
	private String name;
	private Food food;
	private List<Ingredient> ingredients = new LinkedList<>();
	private double totQuantity;
	private double calories, proteins, carbs, fat;

	private class Ingredient {
		private String material;
		private double quantity;

		Ingredient(String material, double quantity) {
			this.material = material;
			this.quantity = quantity;
		}

		public String getMaterial() {
			return material;
		}

		public double getQuantity() {
			return quantity;
		}
	}

	/**
	 * Recipe constructor. The reference {@code food} of type {@link Food} must
	 * be used to retrieve the information about ingredients.
	 * 
	 * @param nome
	 *            unique name of the recipe
	 * @param food
	 *            object containing the information about ingredients
	 */
	public Recipe(String name, Food food) {
		this.name = name;
		this.food = food;
		food.defineRecipe(name, this);
	}

	/**
	 * Adds a given quantity of an ingredient to the recipe. The ingredient is a
	 * raw material defined with the {@code food} argument of the constructor.
	 * 
	 * @param material
	 *            the name of the raw material to be used as ingredient
	 * @param quantity
	 *            the amount in grams of the raw material to be used
	 */
	public void addIngredient(String material, double quantity) {
		NutritionalElement tmpMaterial;
		ingredients.add(new Ingredient(material, quantity));
		tmpMaterial = food.getRawMaterial(material);
		totQuantity += quantity;
		calories += quantity / 100.0 * tmpMaterial.getCalories();
		proteins += quantity / 100.0 * tmpMaterial.getProteins();
		carbs += quantity / 100.0 * tmpMaterial.getCarbs();
		fat += quantity / 100.0 * tmpMaterial.getFat();
	}

	public String getName() {
		return name;
	}

	public double getCalories() {
		return 100.0 / totQuantity * calories;
	}

	public double getProteins() {
		return 100.0 / totQuantity * proteins;
	}

	public double getCarbs() {
		return 100.0 / totQuantity * carbs;
	}

	public double getFat() {
		return 100.0 / totQuantity * fat;
	}

	public boolean per100g() {
		// a recipe expressed nutritional values per 100g
		return true;
	}

}
