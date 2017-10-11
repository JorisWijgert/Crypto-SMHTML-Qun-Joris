using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace CryptoBackEnd.Models
{
    public class UserGraph
    {
        public int Id { get; set; }
        public bool Favourite { get; set; }
        public virtual Graph Graph { get; set; }

    }
}