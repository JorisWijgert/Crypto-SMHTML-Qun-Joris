using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace CryptoBackEnd.Models
{

    public class User
    {
        public User()
        {
            UserGraphs = new HashSet<UserGraph>();
            UserValutas = new HashSet<UserValuta>();
            NotificationSettings = new HashSet<NotificationSetting>();
        }

        public int Id { get; set; }
        public string Username { get; set; }
        public string Password { get; set; }
        public virtual ICollection<UserGraph> UserGraphs { get; set; }
        public virtual ICollection<UserValuta> UserValutas { get; set; }
        public virtual ICollection<NotificationSetting> NotificationSettings { get; set; }
    }
}