using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace SimpleMeal.Models
{
    public class SupermarketProduct
    {
        public int Id { get; set; }
        public double Price { get; set; }
        public virtual Product Product { get; set; }

    }
}