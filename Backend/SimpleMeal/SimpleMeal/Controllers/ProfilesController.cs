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
using SimpleMeal.Models;
using SimpleMeal.JsonInputs;

namespace SimpleMeal.Controllers
{
    public class ProfilesController : ApiController
    {
        private SimpleMealModel db = new SimpleMealModel();

        // GET: api/Profiles
        public List<Profile> GetProfile()
        {
            return db.Profile.ToList();
        }

        // GET: api/Profiles/5
        [ResponseType(typeof(Profile))]
        public IHttpActionResult GetProfile(int id)
        {
            Profile profile = db.Profile.Find(id);
            if (profile == null)
            {
                return NotFound();
            }

            return Ok(profile);
        }

        // PUT: api/Profiles/5
        [ResponseType(typeof(void))]
        public IHttpActionResult PutProfile(int id)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            Profile profile = db.Profile.Find(id);
            profile.History.Add(profile.CurrentRecipe);
            profile.CurrentRecipe = null;

            db.Entry(profile).State = EntityState.Modified;

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!ProfileExists(id))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return StatusCode(HttpStatusCode.NoContent);
        }

        // POST: api/Profiles
        [ResponseType(typeof(Profile))]
        public IHttpActionResult PostProfile(ProfileRecipeJson profileRecipeJson)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            Profile profile = db.Profile.Find(profileRecipeJson.ProfileId);
            Recipe recipe = db.Recipe.Find(profileRecipeJson.RecipeId);

            profile.CurrentRecipe = recipe;
            db.SaveChanges();

            return CreatedAtRoute("DefaultApi", new { id = profile.Id }, profile);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool ProfileExists(int id)
        {
            return db.Profile.Count(e => e.Id == id) > 0;
        }
    }
}