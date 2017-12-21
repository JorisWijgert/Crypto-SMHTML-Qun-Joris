//
//  ViewController.swift
//  SimpleMeal
//
//  Created by Fhict on 20-12-17.
//  Copyright © 2017 SimpleMeal. All rights reserved.
//

import UIKit

class ViewController: UIViewController {

    @IBOutlet weak var tvHistory: UITableView!
    @IBOutlet weak var lblCurrentRecipe: UILabel!
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
        let jsonUrlString = "https://i329146.venus.fhict.nl/api/profiles/1"
        guard let url = URL(string: jsonUrlString) else { return }
        
        URLSession.shared.dataTask(with: url) { (data, response, err) in
            guard let data = data else { return }
            do {
                let profile = try JSONDecoder().decode(Profile.self, from: data)
                print(profile)
//                if(profile.CurrentRecipe != nil){
                DispatchQueue.main.async { // Correct
                    self.lblCurrentRecipe.text = profile.CurrentRecipe.Name
                }
//                }
                
            } catch let jsonErr {
                print("Error serializing json:", jsonErr)
            }
        }.resume()
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

    @IBAction func makeMealButtonClick(_ sender:Any)
    {
        
    }
    
}

