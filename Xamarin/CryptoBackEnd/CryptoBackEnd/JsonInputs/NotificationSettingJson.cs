using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace CryptoBackEnd.JsonInputs
{
    public class NotificationSettingJson
    {
        public int Id { get; set; }
        public double TargetAmount { get; set; }
        public bool Push { get; set; }
        public int ValutaId { get; set; }
        public int UserId { get; set; }
    }
}