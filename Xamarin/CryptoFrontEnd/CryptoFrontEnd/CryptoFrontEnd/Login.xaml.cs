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

            var graphPage = new ContentPage
            {
                Title = "30 uur Bitcoin",
                Content = new PlotView
                {
                    Model = CreatePlotModel(),
                    VerticalOptions = LayoutOptions.Fill,
                    HorizontalOptions = LayoutOptions.Fill,
                },
            };

            await Navigation.PushAsync(graphPage);

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

        public PlotModel CreatePlotModel()
        {
            UserData.Rootobject userData = Task.Run(FetchUserData).Result;
            var plotModel = new PlotModel { Title = "OxyPlot Demo" };

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