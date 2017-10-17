using CryptoBackEnd.Models;
using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Data.Entity.Infrastructure;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Threading.Tasks;
using System.Web.Http;
using System.Web.Http.Description;

namespace CryptoBackEnd.Controllers
{
    public class ValutasController : ApiController
    {
        private DataModel db = new DataModel();


        // POST: api/Valutas
        [ResponseType(typeof(Valuta))]
        public async Task<IHttpActionResult> PostValuta(Valuta valuta)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.Valuta.Add(valuta);
            await db.SaveChangesAsync();

            return CreatedAtRoute("DefaultApi", new { id = valuta.Id }, valuta);
        }

        // GET: api/Valutas
        public List<Valuta> GetValutas()
        {
            return db.Valuta.ToList();
        }

        // GET: api/Valutas/5
        [ResponseType(typeof(Valuta))]
        public async Task<IHttpActionResult> GetValuta(int id)
        {
            Valuta valuta = await db.Valuta.FindAsync(id);
            if (valuta == null)
            {
                return NotFound();
            }

            return Ok(valuta);
        }

        // PUT: api/Valutas/5
        [ResponseType(typeof(void))]
        public async Task<IHttpActionResult> PutValuta(int id, Valuta valuta)
        {
            if (!ModelState.IsValid)
                return BadRequest(ModelState);

            if (id != valuta.Id)
                return BadRequest();

            db.Entry(valuta).State = EntityState.Modified;

            try
            {
                await db.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!ValutaExists(id))
                    return NotFound();
                else
                    throw;
            }
            return StatusCode(HttpStatusCode.NoContent);
        }

        private bool ValutaExists(int id)
        {
            return db.Valuta.Count(e => e.Id == id) > 0;
        }
    }
}
