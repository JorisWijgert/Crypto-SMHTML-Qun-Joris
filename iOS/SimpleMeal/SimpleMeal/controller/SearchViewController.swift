//
//  SearchViewController.swift
//  SimpleMeal
//
//  Created by Fhict on 04/01/2018.
//  Copyright © 2018 SimpleMeal. All rights reserved.
//

import UIKit

class SearchViewController: UIViewController {
    
    
    @IBOutlet weak var PrepTimeTextField: UITextField!
    @IBOutlet weak var AmountPersonsTextField: UITextField!
    @IBOutlet weak var BudgetTextField: UITextField!
    override func viewDidLoad() {
        super.viewDidLoad()
        
        // Do any additional setup after loading the view.
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    @IBAction func SearchButtonAction(_ sender: Any) {
        
        guard let prepTime = PrepTimeTextField.text, !prepTime.isEmpty else {
            return
        }
        guard let amountPersons = AmountPersonsTextField.text, !amountPersons.isEmpty else {
            return
        }
        guard let budget = BudgetTextField.text, !budget.isEmpty else {
            return
        }
        
        let recipeViewController = storyboard?.instantiateViewController(withIdentifier: "RecipeViewController") as! RecipeViewController
        recipeViewController.prepTime = prepTime;
        recipeViewController.amountPersons = amountPersons;
        recipeViewController.budget = budget;
        navigationController?.pushViewController(recipeViewController, animated: true)
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
