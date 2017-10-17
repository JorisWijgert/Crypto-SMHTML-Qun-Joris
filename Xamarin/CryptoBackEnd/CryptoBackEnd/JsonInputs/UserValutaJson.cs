using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace CryptoBackEnd.JsonInputs
{
    public class UserValutaJson
    {
        public int Id { get; set; }
        public double Amount { get; set; }
        public double PurchasePrice { get; set; }
        public int ValutaId { get; set; }
        public int UserId { get; set; }
    }
}