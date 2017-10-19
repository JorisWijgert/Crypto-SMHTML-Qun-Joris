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
    public class NotificationSettingsController : ApiController
    {
        private DataModel db = new DataModel();

        // POST: api/NotificationSettings
        [ResponseType(typeof(NotificationSetting))]
        public async Task<IHttpActionResult> PostNotificationSettings(NotificationSettingJson notificationSetting)
        {
            if (!ModelState.IsValid)
                return BadRequest(ModelState);

            User user = db.User.Find(notificationSetting.UserId);
            Valuta valuta = db.Valuta.Find(notificationSetting.ValutaId);
            NotificationSetting newNotificationSetting = new NotificationSetting { Push = notificationSetting.Push, TargetAmount = notificationSetting.TargetAmount, Valuta = valuta };

            user.NotificationSettings.Add(newNotificationSetting);
            await db.SaveChangesAsync();
            return CreatedAtRoute("DefaultApi", new { id = newNotificationSetting.Id }, newNotificationSetting);
        }

        // PUT: api/NotificationSettings/5
        [ResponseType(typeof(void))]
        public async Task<IHttpActionResult> PutNotificationSettings(int id, NotificationSettingJson notificationSetting)
        {
            if (!ModelState.IsValid)
                return BadRequest(ModelState);

            if (id != notificationSetting.Id)
                return BadRequest();

            User user = db.User.SingleOrDefault(u => u.Id == notificationSetting.UserId);
            NotificationSetting notificationSettingOfUser = user.NotificationSettings.Where(ns => ns.Id == notificationSetting.Id).FirstOrDefault();
            if (notificationSettingOfUser.TargetAmount != notificationSetting.TargetAmount)
                notificationSettingOfUser.TargetAmount = notificationSetting.TargetAmount;

            if (notificationSettingOfUser.Push != notificationSetting.Push)
                notificationSettingOfUser.Push = notificationSetting.Push;

            try
            {
                await db.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!NotificationSettingExists(id))
                    return NotFound();
                else
                    throw;
            }
            return StatusCode(HttpStatusCode.NoContent);
        }

        // DELETE: api/NotificationSettings/5
        [ResponseType(typeof(void))]
        public async Task<IHttpActionResult> DeleteNotificationSetting(int id)
        {
            foreach (User user in db.User.ToList())
                foreach (NotificationSetting ns in user.NotificationSettings)
                    if (ns.Id == id)
                    {
                        user.NotificationSettings.Remove(ns);
                        await db.SaveChangesAsync();

                        return StatusCode(HttpStatusCode.NoContent);
                    }
            return StatusCode(HttpStatusCode.NotFound);
        }

        private bool NotificationSettingExists(int id)
        {
            bool returnValue = false;
            foreach (User user in db.User)
                foreach (NotificationSetting ns in user.NotificationSettings)
                    if (ns.Id == id)
                        returnValue = true;

            return returnValue;
        }
    }
}
