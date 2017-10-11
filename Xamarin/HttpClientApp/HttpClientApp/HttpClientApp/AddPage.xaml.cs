using System;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Threading.Tasks;
using Newtonsoft.Json;
using Xamarin.Forms;

namespace HttpClientApp
{
    public partial class AddPage : ContentPage
    {
        private string Uri = ApplicationVariables.mainUri;
        public AddPage()
        {
            InitializeComponent();
        }

        public async Task AddEmployeeAsync()
        {
            var httpClient = new HttpClient();

            var name = NameEntry.Text;
            var department = DepartmentEntry.Text;

            var employee = new Employee
            {
                Name = name,
                Department = department
            };

            var json = JsonConvert.SerializeObject(employee);

            HttpContent httpContent = new StringContent(json);

            httpContent.Headers.ContentType = new MediaTypeHeaderValue("application/json");

            var result = await httpClient.PostAsync(Uri, httpContent);
        }

        private async void AddEmployee_Clicked(object sender, EventArgs e)
        {
            await AddEmployeeAsync();
        }
    }
}