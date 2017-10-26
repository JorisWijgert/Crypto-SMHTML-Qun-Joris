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
    public partial class AddUserValutaPage : ContentPage
    {
        public AddUserValutaPage()
        {
            InitializeComponent();
            FillPicker();
        }

        private async Task FillPicker()
        {
            CurrencyPicker.ItemsSource = await Connector.GetAllValutas();
        }

        private async Task btnAdd_Clicked(object sender, EventArgs e)
        {
            lblError.IsVisible = false;
            lblError.Text = "";

            float price = 0.0F;
            float amount = 0.0F;
            try
            {
                price = float.Parse(priceEntry.Text);
                amount = float.Parse(amountEntry.Text);
            } catch (System.Exception ex)
            {
                lblError.IsVisible = true;
                lblError.Text = "Price or amount is in the wrong format, only numbers are allowed!";
                return;
            }

            if (CurrencyPicker.SelectedIndex < 0)
            {
                lblError.IsVisible = true;
                lblError.Text = "No currency is selected!";
                return;
            }

            UserValutaJson userValuta = new UserValutaJson
            {
                Amount = amount,
                PurchasePrice = price,
                UserId = (int)Application.Current.Properties["userId"],
                ValutaId = ((UserData.Valuta)CurrencyPicker.SelectedItem).Id
            };

            await Connector.PostUserValuta(userValuta);

            await Navigation.PopAsync();
        }
    }
}