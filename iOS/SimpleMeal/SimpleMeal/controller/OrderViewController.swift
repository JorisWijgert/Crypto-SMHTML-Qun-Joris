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
    
    func updateUI(product: Product){
        ProductNameLabel.text = product.Name
    }
}

class OrderViewController: UIViewController, UITableViewDelegate, UITableViewDataSource {
    @IBOutlet weak var SupermarketLabel: UILabel!
    @IBOutlet weak var RecipeNameLabel: UILabel!
    
    var recipe:Recipe?
    var supermarket:Supermarket?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        SupermarketLabel.text = supermarket?.Name
        RecipeNameLabel.text = recipe?.Name
        // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
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
