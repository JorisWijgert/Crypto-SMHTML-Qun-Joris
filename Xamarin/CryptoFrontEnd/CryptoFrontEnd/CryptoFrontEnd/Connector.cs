using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;

namespace CryptoFrontEnd
{
    public class Connector
    {

        public static async Task<UserData.Rootobject> GetSpecficUserAPIAsync(int userId) {
            string api = $"https://i329146.venus.fhict.nl/api/users/{userId}";
            HttpClient httpClient = new HttpClient();
            var responseText = await httpClient.GetStringAsync(api);
            UserData.Rootobject data = JsonConvert.DeserializeObject<UserData.Rootobject>(responseText);
            return data;
        }

        public static async Task<UserData.Rootobject[]> GetUsersAsync() {
            string api =  "https://i329146.venus.fhict.nl/api/users";
            HttpClient httpClient = new HttpClient();
            var responseText = await httpClient.GetStringAsync(api);
            UserData.Rootobject[] data = JsonConvert.DeserializeObject<UserData.Rootobject[]>(responseText);
            return data;
        }

       
    }
}
