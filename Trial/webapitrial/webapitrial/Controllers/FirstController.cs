using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace webapitrial.Controllers
{
    public class FirstController : ApiController
    {
        [HttpGet]
        public string returnThings()
        {
            return "Hallo Qun";
        }

        [HttpPost]
        public string postAndReturn()
        {
            return "Hey hey";
        }
    }
}
