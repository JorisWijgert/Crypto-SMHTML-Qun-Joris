//
//  ShoplistViewController.swift
//  SimpleMeal
//
//  Created by Fhict on 11-01-18.
//  Copyright © 2018 SimpleMeal. All rights reserved.
//

import UIKit

class ShoplistViewController: UITableViewController {

    var recipe:Recipe?
    var supermarket:Supermarket?
    
    
    @IBOutlet var ShoplistTableView: UITableView!
    override func viewDidLoad() {
        super.viewDidLoad()
        ShoplistTableView.delegate = self
        ShoplistTableView.dataSource = self
       
        // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    override func viewDidAppear(_ animated: Bool) {
        DispatchQueue.main.async {
            self.ShoplistTableView.reloadData()
        }
        

    }
    
    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return (recipe?.RecipeProducts.count)!
    }
    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        if let cell = tableView.dequeueReusableCell(withIdentifier: "ShopProductCell", for: indexPath) as? ProductCell{
            var recipeProduct = recipe?.RecipeProducts[indexPath.row] as! RecipeProduct
            cell.updateUI(recipeProduct: recipeProduct)
            return cell
        } else {
            return UITableViewCell()
        }
    }
    
    
}
