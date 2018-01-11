//
//  OrderViewController.swift
//  SimpleMeal
//
//  Created by Fhict on 11-01-18.
//  Copyright © 2018 SimpleMeal. All rights reserved.
//

import UIKit

class ProductCell : UITableViewCell {
    @IBOutlet weak var ProductNameLabel: UILabel!
    
    func updateUI(recipeProduct: RecipeProduct){
        ProductNameLabel.text = recipeProduct.Product.Name
    }
}

class OrderViewController: UIViewController, UITableViewDelegate, UITableViewDataSource {
    @IBOutlet weak var SupermarketLabel: UILabel!
    @IBOutlet weak var RecipeNameLabel: UILabel!
    @IBOutlet weak var ProductTableView: UITableView!
    
    var recipe:Recipe?
    var supermarket:Supermarket?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        SupermarketLabel.text = supermarket?.Name
        RecipeNameLabel.text = recipe?.Name
        ProductTableView.delegate = self
        ProductTableView.dataSource = self
        // Do any additional setup after loading the view.
    }
    
    override func viewDidAppear(_ animated: Bool) {
        DispatchQueue.main.async {
            self.ProductTableView.reloadData()
        }
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return (recipe?.RecipeProducts.count)!
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        if let cell = tableView.dequeueReusableCell(withIdentifier: "ProductCell", for: indexPath) as? ProductCell{
            var recipeProduct = recipe?.RecipeProducts[indexPath.row] as! RecipeProduct
            cell.updateUI(recipeProduct: recipeProduct)
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
