using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using Xamarin.Forms;
using Xamarin.Forms.Xaml;

using OxyPlot.Xamarin.Forms;
using System.Net.Http;
using Newtonsoft.Json;
using OxyPlot;
using OxyPlot.Axes;
using OxyPlot.Series;

namespace CryptoFrontEnd
{
    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class Login : ContentPage
    {
        public Login()
        {
            InitializeComponent();
        }

        protected async void LoginUser()
        {
            if (String.IsNullOrWhiteSpace(unEntry.Text) || String.IsNullOrWhiteSpace(pwEntry.Text))
            {
                lblError.Text = "Password or username is empty.";
                lblError.IsVisible = true;
                return;
            }

            UserData.Rootobject loggedinUser = new UserData.Rootobject();

            try
            {
                loggedinUser = await FetchAndCheckUser(unEntry.Text, pwEntry.Text);   
            }
            catch (ArgumentException ex)
            {
                lblError.Text = ex.Message;
                lblError.IsVisible = true;
                return;
            }

            if (Application.Current.Properties.ContainsKey("userId"))
            {
                Application.Current.Properties.Remove("userId");
            }
            Application.Current.Properties["userId"] = loggedinUser.Id;

            StartPage startpage = new StartPage();
            startpage.Title = $"Valutas of {loggedinUser.Username}";
            await Navigation.PushAsync(startpage);


        }

        private async Task<UserData.Rootobject> FetchAndCheckUser(string username, string password)
        {
            UserData.Rootobject[] data = await Connector.GetUsersAsync();
            foreach (UserData.Rootobject user in data)
                if (user.Username.Equals(username) && user.Password.Equals(password))
                    return user;
            throw new ArgumentException("Username or password is incorrect.");
        }
    }
}