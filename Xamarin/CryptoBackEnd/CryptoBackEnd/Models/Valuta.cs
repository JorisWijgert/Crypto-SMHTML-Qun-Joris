using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace CryptoBackEnd.Models
{
    public class Valuta
    {
        public int Id { get; set; }
        public string Name { get; set; }
        public string ShortName { get; set; }
        public double CurrentPrice { get; set; }

    }
}