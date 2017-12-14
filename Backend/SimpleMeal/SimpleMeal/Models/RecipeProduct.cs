using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace SimpleMeal.Models
{
    public class RecipeProduct
    {
        public int Id { get; set; }
        public virtual Product Product { get; set; }
        public double Amount { get; set; }
    }
}