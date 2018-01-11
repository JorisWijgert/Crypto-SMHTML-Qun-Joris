//
//  SimpleMealTests.swift
//  SimpleMealTests
//
//  Created by Fhict on 20-12-17.
//  Copyright © 2017 SimpleMeal. All rights reserved.
//

import XCTest
@testable import SimpleMeal

class SimpleMealTests: XCTestCase {
    var RecipeTomatoSoup : Recipe!
    
    override func setUp() {
        super.setUp()
        var RecipeProductsList = [RecipeProduct]()
        RecipeProductsList.append(RecipeProduct(Id:1,Product:Product(Id: 1, Name:"Blik Soep", Amount:50),Amount:10.4))
        RecipeProductsList.append(RecipeProduct(Id:1,Product:Product(Id: 2, Name:"Kruiden", Amount:50),Amount:10.4))
        RecipeProductsList.append(RecipeProduct(Id:1,Product:Product(Id: 3, Name:"Zout", Amount:50),Amount:10.4))
        RecipeTomatoSoup = Recipe(Id:1,Name:"Tomatensoep",Description:"Stap1:Pak blik soep /n Stap2:Verwarm",PersonAmount:2,TimeMin:10,ImageUrl:"",RecipeProducts:RecipeProductsList)
    }

    override func tearDown() {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
        super.tearDown()
    }

    func testAddProduct() {
        let InitialSizeRecipeProductsList = RecipeTomatoSoup.RecipeProducts.count
        RecipeTomatoSoup.addProductToRecipe(recipeProduct: RecipeProduct(Id:1,Product:Product(Id:4,Name:"Wortels",Amount:10),Amount:10))
        let EditedSizeRecipeProductsList = RecipeTomatoSoup.RecipeProducts.count
        XCTAssertEqual(InitialSizeRecipeProductsList+1, EditedSizeRecipeProductsList)
        
    }
    
    func testTime() {
        let Duration = RecipeTomatoSoup.TimeMin
        RecipeTomatoSoup.editTime(OffsetTime: 10)
        let EditedDuration = RecipeTomatoSoup.TimeMin
        XCTAssertEqual(Duration+10, EditedDuration)
    }

    func testPersons() {
        let Persons = RecipeTomatoSoup.PersonAmount
        RecipeTomatoSoup.editPerson(OffsetPersons: 1)
        let EditedPersons = RecipeTomatoSoup.PersonAmount
        XCTAssertEqual(Persons+1, EditedPersons)
    }
}

