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
    public class SupermarketsController : ApiController
    {
        private SimpleMealModel db = new SimpleMealModel();

        // GET: api/Supermarkets
        public IQueryable<Supermarket> GetSupermarket()
        {
            return db.Supermarket;
        }

        // GET: api/Supermarkets/5
        [ResponseType(typeof(Supermarket))]
        public List<Supermarket> GetSupermarketByRecipe(int id)
        {
            Recipe recipe = db.Recipe.Find(id);
            if (recipe == null)
            {
                return null;
            }
            List<Product> products = new List<Product>();
            foreach (RecipeProduct recipeProduct in recipe.RecipeProducts)
            {
                products.Add(recipeProduct.Product);
            }
            List<Supermarket> foundSupermarkets = new List<Supermarket>();
            List<Supermarket> superMarkets = db.Supermarket.ToList();
            foreach (Supermarket superMarket in superMarkets)
            {
                if (RecipesController.CheckIfSupermarketContainsProducts(superMarket.SupermarketProduct, products) != null)
                {
                    foundSupermarkets.Add(superMarket);
                }
            }
            return foundSupermarkets;
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool SupermarketExists(int id)
        {
            return db.Supermarket.Count(e => e.Id == id) > 0;
        }
    }
}