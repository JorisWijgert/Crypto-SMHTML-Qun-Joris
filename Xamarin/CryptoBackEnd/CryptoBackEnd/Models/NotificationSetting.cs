using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace CryptoBackEnd.Models
{
    public class NotificationSetting
    {
        public int Id { get; set; }
        public double TargetAmount { get; set; }
        public bool Push { get; set; }
        public virtual Valuta Valuta { get; set; }

    }
}