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

        public async void LoginUser()
        {
            if (String.IsNullOrWhiteSpace(unEntry.Text) || String.IsNullOrWhiteSpace(pwEntry.Text))
            {
                lblError.Text = "Password or username is empty.";
                lblError.IsVisible = true;
                return;
            }

            UserData.Rootobject loggedinUser = null;

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

            var graphPage = new ContentPage
            {
                Title = loggedinUser.Username,
                Content = new PlotView
                {
                    Model = CreatePlotModel(loggedinUser),
                    VerticalOptions = LayoutOptions.Fill,
                    HorizontalOptions = LayoutOptions.Fill,
                },
            };
            await Navigation.PushAsync(graphPage);


        }

        private async Task<UserData.Rootobject> FetchAndCheckUser(string username, string password)
        {
            UserData.Rootobject[] data = await Connector.GetUsersAsync();
            foreach (UserData.Rootobject user in data)
                if (user.Username.Equals(username) && user.Password.Equals(password))
                    return user;
            throw new ArgumentException("Username or password is incorrect.");
        }

        // Gets weather data from the passed URL.
        private async Task<UserData.Rootobject> FetchUserData()
        {
            string API = "https://i329146.venus.fhict.nl/api/users/1";
            var httpClient = new HttpClient();
            var responseText = await httpClient.GetStringAsync(API);
            UserData.Rootobject data = JsonConvert.DeserializeObject<UserData.Rootobject>(responseText);
            return data;

        }

        public PlotModel CreatePlotModel(UserData.Rootobject loggedinUser)
        {
            UserData.Rootobject userData = Task.Run(() => Connector.GetSpecficUserAPIAsync(loggedinUser.Id)).Result;
            var plotModel = new PlotModel { Title = $"{loggedinUser.Username} Bitcoin" };

            plotModel.Axes.Add(new LinearAxis { Position = AxisPosition.Left });
            plotModel.Axes.Add(new DateTimeAxis
            {
                Position = AxisPosition.Bottom,
                MinorIntervalType = DateTimeIntervalType.Hours,
                IntervalType = DateTimeIntervalType.Hours,
                StringFormat = "HH-dd"
            });

            var series1 = new LineSeries
            {
                MarkerType = MarkerType.Circle,
                MarkerSize = 4,
                MarkerStroke = OxyColors.White
            };

            foreach (UserData.Graphdata graphPoint in userData.UserGraphs[0].Graph.graphData)
            {
                double dateTimeX = DateTimeAxis.ToDouble(UnixTimeStampToDateTime(graphPoint.TimeStamp));
                series1.Points.Add(new DataPoint(dateTimeX, graphPoint.Open));
            }
            //series1.Points.Add(new DataPoint(0.0, 6.0));
            //series1.Points.Add(new DataPoint(1.4, 2.1));
            //series1.Points.Add(new DataPoint(2.0, 4.2));
            //series1.Points.Add(new DataPoint(3.3, 2.3));
            //series1.Points.Add(new DataPoint(4.7, 7.4));
            //series1.Points.Add(new DataPoint(6.0, 6.2));
            //series1.Points.Add(new DataPoint(8.9, 8.9));

            plotModel.Series.Add(series1);

            return plotModel;
        }


        public static DateTime UnixTimeStampToDateTime(double unixTimeStamp)
        {
            // Unix timestamp is seconds past epoch
            System.DateTime dtDateTime = new DateTime(1970, 1, 1, 0, 0, 0, 0, System.DateTimeKind.Utc);
            dtDateTime = dtDateTime.AddSeconds(unixTimeStamp).ToLocalTime();
            return dtDateTime;
        }
    }
}