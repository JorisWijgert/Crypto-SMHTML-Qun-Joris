using Newtonsoft.Json;
using OxyPlot;
using OxyPlot.Axes;
using OxyPlot.Series;
using OxyPlot.Xamarin.Forms;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;
using Xamarin.Forms;

namespace CryptoFrontEnd
{
    public partial class App : Application
    {
        public App()
        {
            InitializeComponent();

            if (Application.Current.Properties.ContainsKey("userId") && (int)Application.Current.Properties["userId"] > 0)
            {
                UserData.Rootobject loggedinUser = Task.Run(() => Connector.GetSpecficUserAPIAsync((int)Application.Current.Properties["userId"])).Result;

                ContentPage page = new CryptoFrontEnd.StartPage();
                page.Title = $"Valutas of {loggedinUser.Username}";
                MainPage = new NavigationPage(page);
            }
            else
            {
                ContentPage page = new CryptoFrontEnd.Login();
                page.Title = "Login";
                MainPage = new NavigationPage(page);
            }
        }      

        protected override void OnStart()
        {
            // Handle when your app starts
        }

        protected override void OnSleep()
        {
            // Handle when your app sleeps
        }

        protected override void OnResume()
        {
            // Handle when your app resumes
        }
    }
}
