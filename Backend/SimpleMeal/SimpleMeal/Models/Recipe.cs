using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace SimpleMeal.Models
{
    public class Recipe
    {
        public int Id { get; set; }
        public string Name { get; set; }
        public string Description { get; set; }
        public int PersonAmount { get; set; }
        public int TimeMin { get; set; }
        public string ImageUrl { get; set; }
        public virtual ICollection<RecipeProduct> RecipeProducts { get; set; }
    }
}