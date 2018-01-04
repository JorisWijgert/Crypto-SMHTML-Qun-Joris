//
//  RecipeViewController.swift
//  SimpleMeal
//
//  Created by Fhict on 04/01/2018.
//  Copyright © 2018 SimpleMeal. All rights reserved.
//

import UIKit

class RecipeViewController: UIViewController {

    var prepTime = ""
    var amountPersons = ""
    var budget = ""
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Time/Persons/Budget
        
        let jsonUrlString = "https://i329146.venus.fhict.nl/api/recipes/" + prepTime + "/" + amountPersons + "/" + budget
        guard let url = URL(string: jsonUrlString) else { return }
        
        URLSession.shared.dataTask(with: url) { (data, response, err) in
            guard let data = data else { return }
            do {
                let recipes = try JSONDecoder().decode([Recipe].self, from: data)
                print(recipes)
                DispatchQueue.main.async {
                }
                
            } catch let jsonErr {
                print("Error serializing json:", jsonErr)
            }
            }.resume()
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
