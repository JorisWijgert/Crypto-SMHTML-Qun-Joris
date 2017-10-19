using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace CryptoBackEnd.Models
{
    public class Graph
    {
        public Graph()
        {
            graphData = new HashSet<GraphData>();
        }
        public int Id { get; set; }
        public string Name { get; set; }
        public virtual Valuta Valuta { get; set; }
        public virtual ICollection<GraphData> graphData { get; set; }

    }
}