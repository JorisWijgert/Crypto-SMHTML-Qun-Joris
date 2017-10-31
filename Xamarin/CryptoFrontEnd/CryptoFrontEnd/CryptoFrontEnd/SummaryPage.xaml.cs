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
            lblPercentage.Text = $"{(Math.Round(end, 2)).ToString()}%";

            if (end < 0)
                lblPercentage.TextColor = Color.Red;
            else
                lblPercentage.TextColor = Color.Green;
        }
    }
}