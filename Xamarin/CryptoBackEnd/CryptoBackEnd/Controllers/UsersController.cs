using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.Data.Entity.Infrastructure;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using System.Web.Http.Description;
using CryptoBackEnd.Models;
using System.Net.Http.Headers;
using Newtonsoft.Json;
using CryptoBackEnd.JSONData;
using System.Threading.Tasks;

namespace CryptoBackEnd.Controllers
{
    public class UsersController : ApiController
    {
        private const string URL = "https://min-api.cryptocompare.com/data/";
        private DataModel db = new DataModel();

        // GET: api/Users
        public List<User> GetUser()
        {
            List<User> users = db.User.ToList();
            return users;
        }

        // GET: api/Users/5
        [ResponseType(typeof(User))]
        public async Task<IHttpActionResult> GetUserAsync(int id)
        {
            User user = db.User.Find(id);
            if (user == null)
            {
                return NotFound();
            }
            await GetGraphData(user);

            return Ok(user);
        }

        private async Task GetGraphData(User user)
        {
            List<HistoricalValutaHours.Rootobject> cryptodataList = new List<HistoricalValutaHours.Rootobject>();
            var httpClient = new HttpClient();
            List<Valuta> valutas = db.Valuta.ToList();
            foreach (Valuta valuta in valutas)
            {
                var json = await httpClient.GetStringAsync($"{URL}histohour?fsym={valuta.ShortName}&tsym=USD&limit=30");
                HistoricalValutaHours.Rootobject valutaHours = JsonConvert.DeserializeObject<HistoricalValutaHours.Rootobject>(json);

                Graph graph = new Graph { Name = $"{valuta.Name} {user.Username}", Valuta = valuta };
                foreach (HistoricalValutaHours.Datum datum in valutaHours.Data)
                {
                    GraphData graphData = new GraphData { Low = datum.low, High = datum.high, Open = datum.open, Close = datum.close, TimeStamp = datum.time };
                    graph.graphData.Add(graphData);
                }
                UserGraph userGraph = new UserGraph { Favourite = false, Graph = graph };
                user.UserGraphs.Add(userGraph);
            }
        }

        // POST: api/Users
        [ResponseType(typeof(User))]
        public IHttpActionResult PostUser(User user)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.User.Add(user);
            db.SaveChanges();

            return CreatedAtRoute("DefaultApi", new { id = user.Id }, user);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool UserExists(int id)
        {
            return db.User.Count(e => e.Id == id) > 0;
        }
    }
}