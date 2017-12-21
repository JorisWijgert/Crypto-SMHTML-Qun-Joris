//
//  RecipeProduct.swift
//  SimpleMeal
//
//  Created by Fhict on 21/12/2017.
//  Copyright © 2017 SimpleMeal. All rights reserved.
//

import Foundation
class RecipeProduct : Decodable{
    let Id: Int
    let Product : Product
    let Amount : Double
    
    init(Id: Int, Product : Product, Amount: Double){
        self.Id = Id;
        self.Product = Product
        self.Amount = Amount
    }
}


