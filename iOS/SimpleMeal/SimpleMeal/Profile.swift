//
//  Profile.swift
//  SimpleMeal
//
//  Created by Fhict on 21/12/2017.
//  Copyright © 2017 SimpleMeal. All rights reserved.
//

import Foundation

class Profile : Decodable{
    let Id: Int
    let CurrentRecipe : Recipe
    let History : [Recipe]
    
    init(Id : Int, CurrentRecipe:Recipe,History: [Recipe]){
        self.Id = Id
        self.CurrentRecipe = CurrentRecipe
        self.History = History
    }
    
}

