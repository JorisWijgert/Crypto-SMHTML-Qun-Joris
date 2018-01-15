//
//  RecipeDetailController.swift
//  SimpleMeal
//
//  Created by Fhict on 05-01-18.
//  Copyright © 2018 SimpleMeal. All rights reserved.
//

import UIKit
import AVKit
import AVFoundation
import Social

class RecipeDetailController: UIViewController {

    var recipe:Recipe?
   
    @IBOutlet weak var RecipeImage: UIImageView!
    @IBOutlet weak var PrepTimeLabel: UILabel!
    @IBOutlet weak var BudgetLabel: UILabel!
    @IBOutlet weak var RecipeDescLabel: UILabel!
    @IBOutlet weak var Scrollview: UIScrollView!
    
    @objc func tapDetected(){
        let videoURL = URL(string: "https://flvpd.vtm.be/videocms/vmma/2015/10/15/201510151531051010034065084005056B7305C0000003648B00000D0F064314.mp4")
        let player = AVPlayer(url: videoURL!)
        let playerViewController = VideoPlayer()
        playerViewController.player = player
        self.present(playerViewController, animated: true) {
            playerViewController.player!.play()
        }
    }
    
    @IBAction func ShareButtonClicked(_ sender: Any) {
        //Alert
        let alert = UIAlertController(title: "Delen", message: "Deel recept: " + recipe!.Name, preferredStyle: .actionSheet)
        //First action
        let actionOne = UIAlertAction(title: "Deel op Facebook", style: .default) { (action) in
            
            //Checking if user is connected to Facebook
            if SLComposeViewController.isAvailable(forServiceType: SLServiceTypeFacebook)
            {
                let post = SLComposeViewController(forServiceType: SLServiceTypeFacebook)!
                
                post.setInitialText("Ik ga het recept " + self.recipe!.Name + " maken!")
                post.add(UIImage(named: "img.png"))
                
                self.present(post, animated: true, completion: nil)
                
            } else {self.showAlert(service: "Facebook")}
        }
        
        //Second action
        let actionTwo = UIAlertAction(title: "Deel op Twitter", style: .default) { (action) in
            
            //Checking if user is connected to Facebook
            if SLComposeViewController.isAvailable(forServiceType: SLServiceTypeTwitter)
            {
                let post = SLComposeViewController(forServiceType: SLServiceTypeTwitter)!
                
                post.setInitialText("Ik ga het recept " + self.recipe!.Name + " maken!")
                post.add(UIImage(named: "img.png"))
                
                self.present(post, animated: true, completion: nil)
                
            } else {self.showAlert(service: "Twitter")}
        }
        
        let actionThree = UIAlertAction(title: "Annuleren", style: .cancel, handler: nil)
        
        //Add action to action sheet
        alert.addAction(actionOne)
        alert.addAction(actionTwo)
        alert.addAction(actionThree)
        
        //Present alert
        self.present(alert, animated: true, completion: nil)
    }
    
    func showAlert(service:String)
    {
        let alert = UIAlertController(title: "Error", message: "You are not connected to \(service)", preferredStyle: .alert)
        let action = UIAlertAction(title: "Dismiss", style: .cancel, handler: nil)
        
        alert.addAction(action)
        present(alert, animated: true, completion: nil)
    }
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        let singleTap = UITapGestureRecognizer(target: self, action: Selector("tapDetected"))
        RecipeImage.isUserInteractionEnabled = true
        RecipeImage.addGestureRecognizer(singleTap)
        if let url = URL(string: recipe!.ImageUrl){
            RecipeImage.contentMode = .scaleAspectFit
            downloadImage(url: url)
        }
        
        let jsonUrlString = String(format:"https://i329146.venus.fhict.nl/api/recipes/lowestprice/%d",recipe!.Id)
        guard let url = URL(string: jsonUrlString) else { return }
        
        URLSession.shared.dataTask(with: url) { (data, response, err) in
            guard let data = data else { return }
            do {
                DispatchQueue.main.async {
                    guard let returnData = String(data: data, encoding: .utf8) else {return}
                    do {
                        self.BudgetLabel.text = "€ " + returnData
                        
                    }
                }
            } catch let jsonErr {
                print("Error serializing json:", jsonErr)
            }
            }.resume()
        
        PrepTimeLabel.text = String(format: "%d min.", recipe!.TimeMin)
        // TODO: call budget
        RecipeDescLabel.text = recipe!.Description
        RecipeDescLabel.numberOfLines=0
        RecipeDescLabel.sizeToFit()
        self.navigationItem.title=recipe!.Name
        // Do any additional setup after loading the view.
        let maxLabelWidth: CGFloat = 228
        let neededSize = RecipeDescLabel.sizeThatFits(CGSize(width: maxLabelWidth, height: CGFloat.greatestFiniteMagnitude))
                Scrollview.contentSize = CGSize(width: self.view.frame.width, height: self.view.frame.height+neededSize.height)
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
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
                self.RecipeImage.image = UIImage(data: data)
            }
        }
    }
    
    @IBAction func SelectClick(_ sender: Any) {
        let supermarketController = storyboard?.instantiateViewController(withIdentifier: "SupermarketListController") as! SupermarketListController
        supermarketController.recipe = self.recipe!
        navigationController?.pushViewController(supermarketController, animated: true)
    }
    
    
    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

}
