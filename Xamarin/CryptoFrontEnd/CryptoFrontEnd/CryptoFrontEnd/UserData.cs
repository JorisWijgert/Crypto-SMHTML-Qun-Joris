﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CryptoFrontEnd
{
    class UserData
    {
        public class Rootobject
        {
            public Notificationsetting[] NotificationSettings { get; set; }
            public Usergraph[] UserGraphs { get; set; }
            public Uservaluta[] UserValutas { get; set; }
            public int Id { get; set; }
            public string Username { get; set; }
            public string Password { get; set; }
        }

        public class Notificationsetting
        {
            public Valuta Valuta { get; set; }
            public int Id { get; set; }
            public int TargetAmount { get; set; }
            public bool Push { get; set; }
        }

        public class Valuta
        {
            public int Id { get; set; }
            public string Name { get; set; }
            public string ShortName { get; set; }
            public int CurrentPrice { get; set; }
        }

        public class Usergraph
        {
            public int Id { get; set; }
            public bool Favourite { get; set; }
            public Graph Graph { get; set; }
        }

        public class Graph
        {
            public int Id { get; set; }
            public string Name { get; set; }
            public Valuta1 Valuta { get; set; }
            public Graphdata[] graphData { get; set; }
        }

        public class Valuta1
        {
            public int Id { get; set; }
            public string Name { get; set; }
            public string ShortName { get; set; }
            public int CurrentPrice { get; set; }
        }

        public class Graphdata
        {
            public int Id { get; set; }
            public float Low { get; set; }
            public float High { get; set; }
            public float Open { get; set; }
            public int TimeStamp { get; set; }
        }

        public class Uservaluta
        {
            public Valuta2 Valuta { get; set; }
            public int Id { get; set; }
            public int Amount { get; set; }
            public int PurchasePrice { get; set; }
        }

        public class Valuta2
        {
            public int Id { get; set; }
            public string Name { get; set; }
            public string ShortName { get; set; }
            public int CurrentPrice { get; set; }
        }

    }
}
