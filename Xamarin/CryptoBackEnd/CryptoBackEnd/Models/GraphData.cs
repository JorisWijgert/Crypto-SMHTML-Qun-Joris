using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace CryptoBackEnd.Models
{
    public class GraphData
    {
        public int Id { get; set; }
        public double Low { get; set; }
        public double High { get; set; }
        public double Open { get; set; }
        public long TimeStamp { get; set; }
    }
}