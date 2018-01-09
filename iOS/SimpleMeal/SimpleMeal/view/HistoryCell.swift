//
//  HistoryCell.swift
//  SimpleMeal
//
//  Created by Fhict on 21-12-17.
//  Copyright © 2017 SimpleMeal. All rights reserved.
//

import UIKit

class HistoryCell: UITableViewCell {

    @IBOutlet weak var titleLabel: UILabel!
    @IBOutlet weak var historyImageView: UIImageView!
    
    func updateUI(recipe: Recipe) {
        titleLabel.text = recipe.Name
        if let url = URL(string: recipe.ImageUrl){
            historyImageView.contentMode = .scaleAspectFit
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
                self.historyImageView.image = UIImage(data: data)
            }
        }
    }
}
