using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace CryptoBackEnd.Models
{
    public class UserValuta
    {
        public int Id { get; set; }
        public double Amount { get; set; }
        public double PurchasePrice { get; set; }
        public virtual Valuta Valuta { get; set; }
    }
}