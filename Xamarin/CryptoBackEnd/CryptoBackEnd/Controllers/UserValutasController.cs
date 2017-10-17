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
    public class UserValutasController : ApiController
    {
        private DataModel db = new DataModel();

        // PUT: api/UserValutas/5
        [ResponseType(typeof(void))]
        public async Task<IHttpActionResult> PutUserValuta(int id, UserValutaJson userValuta)
        {
            if (!ModelState.IsValid)
                return BadRequest(ModelState);

            if (id != userValuta.Id)
                return BadRequest();

            User user = db.User.SingleOrDefault(u => u.Id == userValuta.UserId);
            UserValuta userValutaOfUser = user.UserValutas.Where(uv => uv.Id == userValuta.Id).FirstOrDefault();
            if (userValutaOfUser.Amount != userValuta.Amount)
                userValutaOfUser.Amount = userValuta.Amount;

            if (userValutaOfUser.PurchasePrice != userValuta.PurchasePrice)
                userValutaOfUser.PurchasePrice = userValuta.PurchasePrice;

            try
            {
                await db.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!UserValutaExists(id))
                    return NotFound();
                else
                    throw;
            }
            return StatusCode(HttpStatusCode.NoContent);
        }

        // POST: api/UserValutas
        [ResponseType(typeof(UserValuta))]
        public async Task<IHttpActionResult> PostUserValuta(UserValutaJson userValuta)
        {
            if (!ModelState.IsValid)
                return BadRequest(ModelState);

            User user = db.User.Find(userValuta.UserId);
            Valuta valuta = db.Valuta.Find(userValuta.ValutaId);
            UserValuta newUserValuta = new UserValuta { Amount = userValuta.Amount, PurchasePrice = userValuta.PurchasePrice, Valuta = valuta };

            user.UserValutas.Add(newUserValuta);
            await db.SaveChangesAsync();
            return CreatedAtRoute("DefaultApi", new { id = newUserValuta.Id }, newUserValuta);
        }

        private bool UserValutaExists(int id)
        {
            bool returnValue = false;
            foreach (User user in db.User)
                foreach (UserValuta uv in user.UserValutas)
                    if (uv.Id == id)
                        returnValue = true;

            return returnValue;
        }
    }
}
