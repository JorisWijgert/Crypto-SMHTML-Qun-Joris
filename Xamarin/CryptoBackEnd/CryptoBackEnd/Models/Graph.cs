using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace CryptoBackEnd.Models
{
    public class Graph
    {
        public int Id { get; set; }
        public string Name { get; set; }
        public double Low { get; set; }
        public double High { get; set; }
        public int Volume { get; set; }
        public double Last_Price { get; set; }
        public int StartValue { get; set; }
        public DateTime TimeStamp { get; set; }
        public double CurrentBid { get; set; }
        public double CurrentAsk { get; set; }
        public DateTime StartDate { get; set; }
        public virtual Valuta Valuta { get; set; }

    }
}