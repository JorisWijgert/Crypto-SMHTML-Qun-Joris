using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace SimpleMeal.Models
{
    public class Supermarket
    {
        public int Id { get; set; }
        public string Name { get; set; }
        public double Latitude { get; set; }
        public double Longitude { get; set; }
        public virtual ICollection<SupermarketProduct> SupermarketProduct { get; set; }
        public bool CanDeliver { get; set; }
        public bool CanPickUp { get; set; }
    }
}