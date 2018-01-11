//
//  SimpleMealUITests.swift
//  SimpleMealUITests
//
//  Created by Fhict on 20-12-17.
//  Copyright © 2017 SimpleMeal. All rights reserved.
//

import XCTest

class SimpleMealUITests: XCTestCase {
    var app:XCUIApplication!

    override func setUp() {
        super.setUp()
        
        // Put setup code here. This method is called before the invocation of each test method in the class.
        
        // In UI tests it is usually best to stop immediately when a failure occurs.
        continueAfterFailure = false
        app = XCUIApplication()
        
        // We send a command line argument to our app,
        // to enable it to reset its state
        app.launchArguments.append("--uitesting")
    }
    
    override func tearDown() {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
        super.tearDown()
    }
    
    func TestMakeMealButton(){
        app.launch()
        app.buttons["buttonMakeMeal"].tap()
        XCTAssert(app.navigationBars["SearchViewController"].exists)
    }
    
   
    
}
