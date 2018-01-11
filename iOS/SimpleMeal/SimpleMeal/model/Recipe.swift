//
//  Recipe.swift
//  SimpleMeal
//
//  Created by Fhict on 21/12/2017.
//  Copyright © 2017 SimpleMeal. All rights reserved.
//

import Foundation

class Recipe : Decodable{
    var Id: Int
    var Name: String
    var Description : String
    var PersonAmount : Int
    var TimeMin : Int
    var ImageUrl : String
    var RecipeProducts : [RecipeProduct]

    init(Id : Int, Name:String, Description : String, PersonAmount : Int, TimeMin : Int, ImageUrl : String, RecipeProducts : [RecipeProduct]){
        self.Id = Id
        self.Name = Name
        self.Description = Description
        self.PersonAmount = PersonAmount
        self.TimeMin = TimeMin
        self.ImageUrl = ImageUrl 
        self.RecipeProducts = RecipeProducts
    }
    
    func addProductToRecipe(recipeProduct:RecipeProduct){
        self.RecipeProducts.append(recipeProduct)
    }
    
    func editTime(OffsetTime : Int)->Int{
        if((self.TimeMin+OffsetTime)>0){
            self.TimeMin += OffsetTime
        }
        return self.TimeMin
    }
    
    func editPerson(OffsetPersons : Int)->Int{
        if(OffsetPersons+self.PersonAmount>0){
            self.PersonAmount += OffsetPersons
        }
        return self.PersonAmount
    }
    
    
}

