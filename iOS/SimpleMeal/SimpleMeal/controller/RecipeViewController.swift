//
//  RecipeViewController.swift
//  SimpleMeal
//
//  Created by Fhict on 04/01/2018.
//  Copyright © 2018 SimpleMeal. All rights reserved.
//

import UIKit

class RecipeTableViewCell: UITableViewCell {
    @IBOutlet weak var RecipeNameLabel: UILabel!
    @IBOutlet weak var RecipeImageView: UIImageView!
    
    func updateUI(recipe: Recipe){
        RecipeNameLabel.text = recipe.Name;
        
        if let url = URL(string: recipe.ImageUrl){
            RecipeImageView.contentMode = .scaleAspectFit
            downloadImage(url: url)
        }
    }
    
    func getDataFromUrl(url: URL, completion: @escaping (Data?, URLResponse?, Error?) -> ()) {
        URLSession.shared.dataTask(with: url) { data, response, error in
            completion(data, response, error)
            }.resume()
    }
    
    func downloadImage(url: URL) {
        print("Download Started")
        getDataFromUrl(url: url) { data, response, error in
            guard let data = data, error == nil else { return }
            print(response?.suggestedFilename ?? url.lastPathComponent)
            print("Download Finished")
            DispatchQueue.main.async() {
                self.RecipeImageView.image = UIImage(data: data)
            }
        }
    }
}

class RecipeViewController: UIViewController, UITableViewDelegate, UITableViewDataSource {

    var prepTime = ""
    var amountPersons = ""
    var budget = ""
    var recipes = [Recipe]()
    
    @IBOutlet weak var RecipeTableView: UITableView!
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
    }
    
    override func viewDidAppear(_ animated: Bool) {
        let jsonUrlString = "https://i329146.venus.fhict.nl/api/recipes/" + prepTime + "/" + amountPersons + "/" + budget
        guard let url = URL(string: jsonUrlString) else { return }
        
        URLSession.shared.dataTask(with: url) { (data, response, err) in
            guard let data = data else { return }
            do {
                self.recipes = try JSONDecoder().decode([Recipe].self, from: data)
                
                DispatchQueue.main.async {
                    self.RecipeTableView.reloadData()
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
    
    // MARK: - TableView
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return recipes.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        if let cell = tableView.dequeueReusableCell(withIdentifier: "RecipeCell", for: indexPath) as? RecipeTableViewCell{
            var recipe = recipes[indexPath.row] as! Recipe
            cell.updateUI(recipe: recipe)
            return cell
        } else {
            return UITableViewCell()
        }
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath){

        
        let recipeViewController = storyboard?.instantiateViewController(withIdentifier: "RecipeDetailController") as! RecipeDetailController
        navigationController?.pushViewController(recipeViewController, animated: true)
    }


}
