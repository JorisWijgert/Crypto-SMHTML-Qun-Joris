using CryptoBackEnd.JsonInputs;
using CryptoBackEnd.Models;
using System;
using System.Collections.Generic;
using System.Data.Entity.Infrastructure;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Threading.Tasks;
using System.Web.Http;
using System.Web.Http.Description;

namespace CryptoBackEnd.Controllers
{
    public class UserGraphsController : ApiController
    {
        private DataModel db = new DataModel();

        // PUT api/UserGraphs/5  
        // PUT: api/UserValutas/5
        [ResponseType(typeof(void))]
        public async Task<IHttpActionResult> PutUserGraphs(int id, UserGraphsJson userGraph)
        {
            if (!ModelState.IsValid)
                return BadRequest(ModelState);

            if (id != userGraph.Id)
                return BadRequest();

            User user = db.User.SingleOrDefault(u => u.Id == userGraph.UserId);

            UserGraph foundUserGraph = user.UserGraphs.Where(ug => ug.Id == userGraph.Id).FirstOrDefault();

            foundUserGraph.Favourite = userGraph.Favourite;

            try
            {
                await db.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!UserGraphExists(id))
                    return NotFound();
                else
                    throw;
            }
            return StatusCode(HttpStatusCode.NoContent);
        }

        private bool UserGraphExists(int id)
        {
            bool returnValue = false;
            foreach (User user in db.User)
                foreach (UserGraph ug in user.UserGraphs)
                    if (ug.Id == id)
                        returnValue = true;

            return returnValue;
        }
    }
}