using OxyPlot;
using OxyPlot.Series;
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
    public partial class SummaryPage : ContentPage
    {
        protected Model oxyPlotModel;

        public SummaryPage(UserData.Rootobject user, object item)
        {
            InitializeComponent();
            Title = "Summary";

            UserData.Uservaluta userValuta = (UserData.Uservaluta)item;
            lblShortName.Text = userValuta.Valuta.ShortName;
            lblTotal.Text = (userValuta.Amount * userValuta.Valuta.CurrentPrice).ToString();
            lblAmount.Text = (userValuta.Amount).ToString();
            lblPrice.Text = (userValuta.PurchasePrice).ToString();
            lblCurrent.Text = (userValuta.Valuta.CurrentPrice).ToString();
            double n = userValuta.Amount * userValuta.Valuta.CurrentPrice;
            double o = userValuta.Amount * userValuta.PurchasePrice;
            double upper = n - o;
            double lower = o;
            double left = upper / lower;
            double end = left * 100;
            lblPercentage.Text = (Math.Round(end, 2)).ToString();

            GenerateOxyPlotModel();
        }

        public void GenerateOxyPlotModel()
        {
            var PlotModel = new PlotModel { Title = "Pie Sample1" };

            dynamic seriesP1 = new PieSeries { StrokeThickness = 2.0, InsideLabelPosition = 0.8, AngleSpan = 360, StartAngle = 0 };

            seriesP1.Slices.Add(new PieSlice("Africa", 1030) { IsExploded = false, Fill = OxyColors.PaleVioletRed });
            seriesP1.Slices.Add(new PieSlice("Americas", 929) { IsExploded = true });
            seriesP1.Slices.Add(new PieSlice("Asia", 4157) { IsExploded = true });
            seriesP1.Slices.Add(new PieSlice("Europe", 739) { IsExploded = true });
            seriesP1.Slices.Add(new PieSlice("Oceania", 35) { IsExploded = true });

            PlotModel.Series.Add(seriesP1);
            this.BindingContext = PlotModel;
        }
    }
}