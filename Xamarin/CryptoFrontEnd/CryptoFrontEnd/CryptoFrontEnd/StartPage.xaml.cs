using OxyPlot;
using OxyPlot.Axes;
using OxyPlot.Series;
using OxyPlot.Xamarin.Forms;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using Xamarin.Forms;
using Xamarin.Forms.Xaml;

namespace CryptoFrontEnd
{
    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class StartPage : ContentPage
    {
        private int userId = 0;
        public StartPage()
        {
            InitializeComponent();
            FillValutaList();
        }

        private async Task ValutaListView_ItemTapped(object sender, ItemTappedEventArgs e)
        {
            UserData.Rootobject user = await Connector.GetSpecficUserAPIAsync(userId);
            var graphPage = new ContentPage
            {
                Title = user.Username,
                Content = new PlotView
                {
                    Model = CreatePlotModel(user, e.Item),
                    VerticalOptions = LayoutOptions.Fill,
                    HorizontalOptions = LayoutOptions.Fill,
                },
            };

            await Navigation.PushAsync(graphPage);
        }

        public PlotModel CreatePlotModel(UserData.Rootobject loggedinUser, object userValutaItem)
        {
            UserData.Uservaluta userValuta = (UserData.Uservaluta)userValutaItem;

            UserData.Rootobject userData = loggedinUser;
            var plotModel = new PlotModel();

            plotModel.Axes.Add(new LinearAxis { Position = AxisPosition.Left });
            plotModel.Axes.Add(new DateTimeAxis
            {
                Position = AxisPosition.Bottom,
                MinorIntervalType = DateTimeIntervalType.Hours,
                IntervalType = DateTimeIntervalType.Hours,
                StringFormat = "HH-dd"
            });

            var linearPlot = new LineSeries
            {
                MarkerType = MarkerType.Circle,
                MarkerSize = 4,
                MarkerStroke = OxyColors.White
            };

            foreach (UserData.Usergraph userGraph in userData.UserGraphs)
            {
                if (userGraph.Graph.Valuta.Id == userValuta.Valuta.Id)
                {
                    plotModel.Title = userGraph.Graph.Name;
                    foreach (UserData.Graphdata graphPoint in userGraph.Graph.graphData)
                    {
                        double dateTimeX = DateTimeAxis.ToDouble(UnixTimeStampToDateTime(graphPoint.TimeStamp));
                        linearPlot.Points.Add(new DataPoint(dateTimeX, graphPoint.Open));
                    }
                }
            }



            plotModel.Series.Add(linearPlot);

            return plotModel;
        }


        public static DateTime UnixTimeStampToDateTime(double unixTimeStamp)
        {
            // Unix timestamp is seconds past epoch
            System.DateTime dtDateTime = new DateTime(1970, 1, 1, 0, 0, 0, 0, System.DateTimeKind.Utc);
            dtDateTime = dtDateTime.AddSeconds(unixTimeStamp).ToLocalTime();
            return dtDateTime;
        }

        private async Task FillValutaList()
        {
            userId = (int)Application.Current.Properties["userId"];
            UserData.Uservaluta[] userValutas = await Connector.GetValutas(userId);
            ValutaListView.ItemsSource = userValutas;
        }
    }
}