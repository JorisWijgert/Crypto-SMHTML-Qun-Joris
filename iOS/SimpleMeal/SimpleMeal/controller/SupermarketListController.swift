//
//  SupermarketListController.swift
//  SimpleMeal
//
//  Created by Fhict on 09-01-18.
//  Copyright © 2018 SimpleMeal. All rights reserved.
//

import UIKit
import GoogleMobileAds


class SupermarketTableViewCell: UITableViewCell {
    
    @IBOutlet weak var DeliverButton: UIButton!
    @IBOutlet weak var PickupButton: UIButton!
    @IBOutlet weak var ShoplistButton: UIButton!
    
    @IBOutlet weak var SupermarketNameLabel: UILabel!
    
    var deliverTapped : (() -> Void)? = nil
    var pickupTapped : (() -> Void)? = nil
    var shoplistTapped : (() -> Void)? = nil
    
    func updateUI(supermarket: Supermarket)
    {
        SupermarketNameLabel.text = supermarket.Name
        if(supermarket.CanDeliver)
        {
            DeliverButton.isHidden = false
        } else{
            DeliverButton.isHidden = true
        }
        
        if(supermarket.CanPickUp){
            PickupButton.isHidden = false
        } else{
            PickupButton.isHidden = true
        }
    }
    
    @IBAction func deliverClicked(_ sender: Any) {
        if let deliverTapped = self.deliverTapped {
            deliverTapped()
        }
    }
    
    @IBAction func pickupClicked(_ sender: Any) {
        if let pickupTapped = self.pickupTapped {
            pickupTapped()
        }
    }
    
    @IBAction func shoplistClicked(_ sender: Any) {
        if let shoplistTapped = self.shoplistTapped {
            shoplistTapped()
        }
    }
    
    
}

class SupermarketListController: UITableViewController{

    var recipe:Recipe?
    var supermarkets=[Supermarket]()
    var interstitial: GADInterstitial!
    var supermarketGlobal : Supermarket!

    @IBOutlet var SupermarketTableView: UITableView!
    override func viewDidLoad() {
        super.viewDidLoad()
        
        interstitial = createAndLoadInterstitial()
        let request = GADRequest()
        interstitial.load(request)
        // Do any additional setup after loading the view.
    }
    
    override func viewDidAppear(_ animated: Bool) {
        let jsonUrlString = "https://i329146.venus.fhict.nl/api/supermarkets/" + String(recipe!.Id)
        guard let url = URL(string: jsonUrlString) else {return}
        
        URLSession.shared.dataTask(with: url) { (data, response, err) in
            guard let data = data else { return }
            do {
                self.supermarkets = try JSONDecoder().decode([Supermarket].self, from: data)
                
                DispatchQueue.main.async {
                self.SupermarketTableView.reloadData()
                }
                
            } catch let jsonErr {
                print("Error serializing json:", jsonErr)
            }
            }.resume()
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return supermarkets.count
    }
    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        if let cell = tableView.dequeueReusableCell(withIdentifier: "SupermarketTableViewCell", for: indexPath) as? SupermarketTableViewCell{
            var supermarket = supermarkets[indexPath.row] as! Supermarket
            cell.updateUI(supermarket: supermarket)
            cell.deliverTapped = {
                let orderViewController = self.storyboard?.instantiateViewController(withIdentifier: "OrderViewController") as! OrderViewController
                orderViewController.recipe = self.recipe
                orderViewController.supermarket = supermarket
                self.navigationController?.pushViewController(orderViewController, animated: true)
            }
            cell.pickupTapped={
                let orderViewController = self.storyboard?.instantiateViewController(withIdentifier: "OrderViewController") as! OrderViewController
                orderViewController.recipe = self.recipe
                orderViewController.supermarket = supermarket
                self.navigationController?.pushViewController(orderViewController, animated: true)
            }
            cell.shoplistTapped={
                self.supermarketGlobal = supermarket
                self.showAdd()

                
            }
            return cell
        } else {
            return UITableViewCell()
        }
        
        
        
    }
    
    func showAdd(){
        if interstitial.isReady {
            interstitial.present(fromRootViewController: self)
        } else {
            print("Ad wasn’t ready")
        }
        interstitial = createAndLoadInterstitial()
    }
    
    func interstitialDidDismissScreen(_ ad: GADInterstitial) {
        let shoplistViewController = self.storyboard?.instantiateViewController(withIdentifier: "ShoplistViewController") as! ShoplistViewController
        shoplistViewController.recipe = self.recipe
        shoplistViewController.supermarket = self.supermarketGlobal
        self.navigationController?.pushViewController(shoplistViewController, animated: true)
    }
    
    func createAndLoadInterstitial() -> GADInterstitial {
        let interstitial = GADInterstitial(adUnitID: "ca-app-pub-3940256099942544/4411468910")
        interstitial.delegate = self as? GADInterstitialDelegate
        interstitial.load(GADRequest())
        return interstitial
    }
    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

}
