//
//  Supermarket.swift
//  SimpleMeal
//
//  Created by Fhict on 09-01-18.
//  Copyright © 2018 SimpleMeal. All rights reserved.
//

import Foundation
class Supermarket : Decodable{
    let Id : Int
    let Name : String
    let Latitude : Double
    let Longitude : Double
    let CanDeliver : Bool
    let CanPickUp : Bool
    
    init (Id : Int, Name:String, Latitude: Double, Longitude:Double, CanDeliver:Bool, CanPickUp:Bool){
        self.Id=Id
        self.Name=Name
        self.Latitude=Latitude
        self.Longitude=Longitude
        self.CanDeliver=CanDeliver
        self.CanPickUp=CanPickUp
    }
}
