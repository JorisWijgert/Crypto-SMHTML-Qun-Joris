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

class RecipeDetailController: UIViewController {

    var recipe:Recipe?
   
    
    @IBOutlet weak var RecipeImage: UIImageView!
    
    @objc func tapDetected(){
        let videoURL = URL(string: "https://flvpd.vtm.be/videocms/vmma/2015/10/15/201510151531051010034065084005056B7305C0000003648B00000D0F064314.mp4")
        let player = AVPlayer(url: videoURL!)
        let playerViewController = VideoPlayer()
        playerViewController.player = player
        self.present(playerViewController, animated: true) {
            playerViewController.player!.play()
        }
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
        // Do any additional setup after loading the view.
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
    

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

}
