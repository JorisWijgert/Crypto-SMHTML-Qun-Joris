using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.Data.Entity.Infrastructure;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using System.Web.Http.Description;
using SimpleMeal.Models;

namespace SimpleMeal.Controllers
{
    public class RecipesController : ApiController
    {
        private SimpleMealModel db = new SimpleMealModel();

        // GET: api/Recipes
        public List<Recipe> GetRecipe()
        {
            return db.Recipe.ToList();
        }

        // GET: api/Recipes/5
        [ResponseType(typeof(Recipe))]
        public IHttpActionResult GetRecipe(int id)
        {
            Recipe recipe = db.Recipe.Find(id);
            if (recipe == null)
            {
                return NotFound();
            }

            return Ok(recipe);
        }

        // GET: api/Recipes?time=a&persons=b&budget=c
        [Route("api/recipes/{time}/{persons}/{budget}")]
        [HttpGet]
        public List<Recipe> FindRecipes(int time, int persons, double budget)
        {
            Dictionary<Recipe, List<Product>> foundRecipeDictionary = new Dictionary<Recipe, List<Product>>();
            foreach (Recipe recipe in db.Recipe.ToList())
            {
                if (recipe.TimeMin <= time && recipe.PersonAmount == persons)
                {
                    List<Product> products = new List<Product>();
                    foreach (RecipeProduct recipeProduct in recipe.RecipeProducts)
                    {
                        products.Add(recipeProduct.Product);
                    }
                    foundRecipeDictionary.Add(recipe, products);
                }
            }

            List<Recipe> foundRecipes = new List<Recipe>();


            foreach (KeyValuePair<Recipe, List<Product>> entry in foundRecipeDictionary)
            {
                double price = 0;
                bool free = false;
                bool found = false;
                foreach (Supermarket supermarket in db.Supermarket.ToList())
                {
                    List<SupermarketProduct> products = CheckIfSupermarketContainsProducts(supermarket.SupermarketProduct, entry.Value);
                    if (products != null)
                    {
                        found = true;
                        double innerPrice = 0;
                        foreach (SupermarketProduct supermarketProduct in products)
                            innerPrice += supermarketProduct.Price;
                        if (innerPrice == 0)
                            free = true;
                        if ((!free && price == 0)||price>innerPrice)
                            price = innerPrice;
                    }
                }
                if (found & price <= budget)
                    foundRecipes.Add(entry.Key);
            }


            return foundRecipes;
        }

        public static List<SupermarketProduct> CheckIfSupermarketContainsProducts(ICollection<SupermarketProduct> supermarketProduct, List<Product> products)
        {
            List<SupermarketProduct> foundProducts = new List<SupermarketProduct>();
            foreach (SupermarketProduct supermarketProductItem in supermarketProduct)
                foreach (Product product in products)
                    if (supermarketProductItem.Product.Id == product.Id)
                        foundProducts.Add(supermarketProductItem);

            if (foundProducts.Count == products.Count)
                return foundProducts;
            else
                return null;

        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool RecipeExists(int id)
        {
            return db.Recipe.Count(e => e.Id == id) > 0;
        }
    }
}