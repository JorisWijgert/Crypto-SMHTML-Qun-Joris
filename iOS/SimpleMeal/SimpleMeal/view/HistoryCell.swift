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
    
    func updateUI(recipe: Recipe) {
        titleLabel.text = recipe.Name
    }
}
