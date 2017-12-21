//
//  Recipe.swift
//  SimpleMeal
//
//  Created by Fhict on 21/12/2017.
//  Copyright © 2017 SimpleMeal. All rights reserved.
//

import Foundation

class Recipe : Decodable{
    let Id: Int
    let Name: String
    let Description : String
    let PersonAmount : Int
    let TimeMin : Int
    let ImageUrl : String
    let RecipeProducts : [RecipeProduct]

    init(Id : Int, Name:String, Description : String, PersonAmount : Int, TimeMin : Int, ImageUrl : String, RecipeProducts : [RecipeProduct]){
        self.Id = Id
        self.Name = Name
        self.Description = Description
        self.PersonAmount = PersonAmount
        self.TimeMin = TimeMin
        self.ImageUrl = ImageUrl 
        self.RecipeProducts = RecipeProducts
    }
//    init(json: [String:Any]){
//        id = json["Id"] as? Int ?? -1
//        name = json["Name"] as? String ?? ""
//        description = json["Description"] as? String ?? ""
//        personAmount = json["PersonAmount"] as? Int ?? -1
//        timeMin = json["TimeMin"] as? Int ?? -1
//        imageUrl = json["imageUrl"] as? String ?? ""
//        recipeProduct = json["recipeProduct"] as? RecipeProduct ?? NSObject!
//    }
}
