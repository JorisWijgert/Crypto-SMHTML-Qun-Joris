using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace SimpleMeal.Models
{
    public class Profile
    {
        public int Id { get; set; }
        public virtual ICollection<Recipe> History { get; set; }
        public virtual Recipe CurrentRecipe { get; set; }
    }
}