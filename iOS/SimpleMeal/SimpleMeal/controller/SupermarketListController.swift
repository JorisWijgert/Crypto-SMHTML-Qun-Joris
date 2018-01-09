//
//  SupermarketListController.swift
//  SimpleMeal
//
//  Created by Fhict on 09-01-18.
//  Copyright © 2018 SimpleMeal. All rights reserved.
//

import UIKit

class SupermarketTableViewCell: UITableViewCell {
    
    @IBOutlet weak var SupermarketNameLabel: UILabel!
    
    func updateUI(supermarket: Supermarket)
    {
        SupermarketNameLabel.text = supermarket.Name
    }
}

class SupermarketListController: UITableViewController{

    var recipe:Recipe?
    var supermarkets=[Supermarket]()
    
    @IBOutlet var SupermarketTableView: UITableView!
    override func viewDidLoad() {
        super.viewDidLoad()

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
            return cell
        } else {
            return UITableViewCell()
        }
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
