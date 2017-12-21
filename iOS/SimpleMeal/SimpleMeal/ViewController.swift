//
//  ViewController.swift
//  SimpleMeal
//
//  Created by Fhict on 20-12-17.
//  Copyright © 2017 SimpleMeal. All rights reserved.
//

import UIKit

class ViewController: UIViewController, UITableViewDelegate, UITableViewDataSource {
    
   
    

    @IBOutlet weak var tvHistory: UITableView!
    @IBOutlet weak var lblCurrentRecipe: UILabel!
    
    var historyArray = [Recipe]()
    
    override func viewDidLoad() {
        super.viewDidLoad()
//        // Do any additional setup after loading the view, typically from a nib.
//        let jsonUrlString = "https://i329146.venus.fhict.nl/api/profiles/1"
//        guard let url = URL(string: jsonUrlString) else { return }
//        
//        URLSession.shared.dataTask(with: url) { (data, response, err) in
//            guard let data = data else { return }
//            do {
//                let profile = try JSONDecoder().decode(Profile.self, from: data)
//                print(profile)
////                if(profile.CurrentRecipe != nil){
//                DispatchQueue.main.async { // Correct
//                    self.lblCurrentRecipe.text = profile.CurrentRecipe.Name
//                }
////                }
//                
//            } catch let jsonErr {
//                print("Error serializing json:", jsonErr)
//            }
//        }.resume()
    }
    
    override func viewDidAppear(_ animated: Bool) {
        // Do any additional setup after loading the view, typically from a nib.
        let jsonUrlString = "https://i329146.venus.fhict.nl/api/profiles/1"
        guard let url = URL(string: jsonUrlString) else { return }
        
        URLSession.shared.dataTask(with: url) { (data, response, err) in
            guard let data = data else { return }
            do {
                let profile = try JSONDecoder().decode(Profile.self, from: data)
                self.historyArray = profile.History
                
                print(profile)
                //                if(profile.CurrentRecipe != nil){
                DispatchQueue.main.async { // Correct
                    self.lblCurrentRecipe.text = profile.CurrentRecipe.Name
                    self.tvHistory.reloadData()
                }
                //                }
                
            } catch let jsonErr {
                print("Error serializing json:", jsonErr)
            }
            }.resume()
    }
    
    // MARK: - TableView
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return historyArray.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        if let cell = tableView.dequeueReusableCell(withIdentifier: "HistoryCell", for: indexPath) as? HistoryCell {
            
            var recipe = historyArray[indexPath.row] as! Recipe
            cell.updateUI(recipe: recipe)
            return cell
        }else{
            return UITableViewCell()
        }
    }

    @IBAction func makeMealButtonClick(_ sender:Any)
    {
        
    }
    
}

