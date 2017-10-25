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
        public StartPage()
        {
            InitializeComponent();
            FillValutaList();
        }

        private void ValutaListView_ItemTapped(object sender, ItemTappedEventArgs e)
        {

        }

        private void FillValutaList()
        {
            UserData.Rootobject user = (UserData.Rootobject) Application.Current.Properties["user"];
            ValutaListView.ItemsSource = user.UserValutas;
        } 
    }
}