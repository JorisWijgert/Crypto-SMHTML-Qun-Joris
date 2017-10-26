using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CryptoFrontEnd
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
