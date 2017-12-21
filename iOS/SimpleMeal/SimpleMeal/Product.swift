//
//  Product.swift
//  SimpleMeal
//
//  Created by Fhict on 21/12/2017.
//  Copyright © 2017 SimpleMeal. All rights reserved.
//

import Foundation
class Product : Decodable{
    let Id : Int
    let Name : String
    let Amount : Int
    
    init(Id : Int, Name:String, Amount:Int){
        self.Id = Id
        self.Name = Name
        self.Amount = Amount
    }
}
