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
        public HttpClient httpClient { get; set; }

        public Connector()
        {
            httpClient = new HttpClient();
        }


        public async Task<UserData.Rootobject> getSpecficUserAPIAsync(string userID) {
            string api = "https://i329146.venus.fhict.nl/api/users/" + userID;
            var responseText = await httpClient.GetStringAsync(api);
            UserData.Rootobject data = JsonConvert.DeserializeObject<UserData.Rootobject>(responseText);
            return data;
        }

        public async Task<UserData.Rootobject[]> getUsersAsync() {
            string api =  "https://i329146.venus.fhict.nl/api/users";
            var responseText = await httpClient.GetStringAsync(api);
            UserData.Rootobject[] data = JsonConvert.DeserializeObject<UserData.Rootobject[]>(responseText);
            return data;
        }

       
    }
}
