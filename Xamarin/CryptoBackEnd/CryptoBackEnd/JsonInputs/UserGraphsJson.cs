using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace CryptoBackEnd.JsonInputs
{
    public class UserGraphsJson
    {
        public int Id { get; set; }
        public bool Favourite { get; set; }
        public int GraphId { get; set; }
        public int UserId { get; set; }
    }
}