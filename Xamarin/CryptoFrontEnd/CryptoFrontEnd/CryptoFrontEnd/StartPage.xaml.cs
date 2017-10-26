using OxyPlot;
using OxyPlot.Axes;
using OxyPlot.Series;
using OxyPlot.Xamarin.Forms;
using System;
using System.Collections.Generic;
using System.Diagnostics;
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
            var graphPage = new TabbedPage
            {
                Children =
                {
                    new ContentPage{
                        Title = "CandlePlot",
                        Content = new PlotView
                        {
                            Model = CreateCandlePlotModel(user, e.Item),
                            VerticalOptions = LayoutOptions.Fill,
                            HorizontalOptions = LayoutOptions.Fill,
                        }
                    },
                    new ContentPage {
                        Title = "LinearPlot",
                        Content = new PlotView
                        {
                            Model = CreatePlotModel(user, e.Item),
                            VerticalOptions = LayoutOptions.Fill,
                            HorizontalOptions = LayoutOptions.Fill,
                        },
                    },
                     new ContentPage{
                        Title = "BarPlot",
                        Content = new PlotView
                        {
                            Model = CreateBarPlotModel(user, e.Item),
                            VerticalOptions = LayoutOptions.Fill,
                            HorizontalOptions = LayoutOptions.Fill,
                        }
                    }
                },
                Title = "Graphs"
            };

            await Navigation.PushAsync(graphPage);
        }

        private PlotModel CreateCandlePlotModel(UserData.Rootobject user, object item)
        {
            var model = new PlotModel { Title = "CandleStickSeries"} ;

           

            var s1 = new CandleStickSeries()
            {
                Color = OxyColors.Black,
            };
            UserData.Uservaluta userValuta = (UserData.Uservaluta)item;
            List<UserData.Graphdata> currencyList = Task.Run(() => GetCurrencyCrypto(userValuta.Id)).Result;
            foreach (UserData.Graphdata dataPoint in currencyList)
            {
                s1.Items.Add(new HighLowItem(DateTimeAxis.ToDouble(UnixTimeStampToDateTime(dataPoint.TimeStamp)),dataPoint.High, dataPoint.Low, dataPoint.Open, dataPoint.Close));

            }
           
            model.Series.Add(s1);
            model.Axes.Add(new LinearAxis() { MaximumPadding = 0.3, MinimumPadding = 0.3 });
            model.Axes.Add(new DateTimeAxis
            {
                Position = AxisPosition.Bottom,
                MinorIntervalType = DateTimeIntervalType.Hours,
                IntervalType = DateTimeIntervalType.Hours,
                StringFormat = "HH-dd"
            });

            return model;
        }

        private PlotModel CreateBarPlotModel(UserData.Rootobject user, object item)
        {
            UserData.Uservaluta userValuta = (UserData.Uservaluta)item;
            var model = new PlotModel { Title = "Currencies" };

            List<UserData.Graphdata> currencyList = Task.Run(() => GetCurrencyCrypto(userValuta.Id)).Result;
            List<BarItem> values = new List<BarItem>();
            List<DateTime> timeStamps = new List<DateTime>();
            foreach (UserData.Graphdata dataPoint in currencyList) {
                values.Add(
                     new BarItem { Value = (dataPoint.Open) });
                timeStamps.Add(UnixTimeStampToDateTime(dataPoint.TimeStamp));
            }

            model.Axes.Add(new CategoryAxis
            {
                Position = AxisPosition.Left,
                Key = "CurrencyAxis",
                ItemsSource = timeStamps
            });

            var barSeries = new BarSeries
            {
                ItemsSource = values,
                LabelPlacement = LabelPlacement.Outside,
            };

            model.Series.Add(barSeries);
            return model;
        }

        private async Task<List<UserData.Graphdata>> GetCurrencyCrypto(int valutaId)
        {
            float sum = Task.Run(() => GetSumCrypto()).Result;
            List<UserData.Graphdata> currencyList = new List<UserData.Graphdata>();
            UserData.Rootobject user = await Connector.GetSpecficUserAPIAsync((int)Application.Current.Properties["userId"]);
            foreach (UserData.Usergraph userGraph in user.UserGraphs)
            {
                if (userGraph.Graph.Valuta.Id == valutaId) {
                    currencyList = userGraph.Graph.graphData.ToList();
                }
                
            }
            return currencyList;
        }

        private async Task<float> GetSumCrypto()
        {
            float sum = 0;
            UserData.Rootobject user = await Connector.GetSpecficUserAPIAsync((int)Application.Current.Properties["userId"]);
            foreach (UserData.Uservaluta userValuta in user.UserValutas) {
                sum += (userValuta.Amount * userValuta.Valuta.CurrentPrice);
            }

            return sum;
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

        protected override void OnAppearing()
        {
            FillValutaList();
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

        private async void AddGraph_Clicked(object sender, EventArgs e)
        {
            AddUserValutaPage addValutaPage = new AddUserValutaPage();
            addValutaPage.Title = "Add valuta";
            await Navigation.PushAsync(addValutaPage);
        }
    }
}