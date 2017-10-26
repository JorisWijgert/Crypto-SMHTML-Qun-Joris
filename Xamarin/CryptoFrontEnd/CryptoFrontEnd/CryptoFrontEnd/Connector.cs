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
        private static string baseUrl = "https://i329146.venus.fhict.nl/api/";
        public static async Task<UserData.Rootobject> GetSpecficUserAPIAsync(int userId) {
            string api = $"{baseUrl}users/{userId}";
            HttpClient httpClient = new HttpClient();
            var responseText = await httpClient.GetStringAsync(api);
            UserData.Rootobject data = JsonConvert.DeserializeObject<UserData.Rootobject>(responseText);
            return data;
        }

        public static async Task<UserData.Rootobject[]> GetUsersAsync() {
            string api = $"{baseUrl}users";
            HttpClient httpClient = new HttpClient();
            var responseText = await httpClient.GetStringAsync(api);
            UserData.Rootobject[] data = JsonConvert.DeserializeObject<UserData.Rootobject[]>(responseText);
            return data;
        }

        public static async Task<UserData.Uservaluta[]> GetValutas(int userId)
        {
            string api = $"{baseUrl}users/{userId}";
            HttpClient httpClient = new HttpClient();
            var responseText = await httpClient.GetStringAsync(api);
            UserData.Rootobject data = JsonConvert.DeserializeObject<UserData.Rootobject>(responseText);


            return data.UserValutas;
        }

        public static async Task<UserData.Valuta[]> GetAllValutas()
        {
            string api = $"{baseUrl}valutas";
            HttpClient httpClient = new HttpClient();
            var responseText = await httpClient.GetStringAsync(api);
            UserData.Valuta[] data = JsonConvert.DeserializeObject<UserData.Valuta[]>(responseText);
            return data;
        }

        public static async Task PostUserValuta(UserValutaJson userValuta)
        {
            string api = $"{baseUrl}/uservalutas";
            HttpClient httpClient = new HttpClient();
            string data = JsonConvert.SerializeObject(userValuta);
            StringContent stringContent = new StringContent(data, Encoding.UTF8, "application/json");

            var returnData = await httpClient.PostAsync(api, stringContent);
        }


    }
}
