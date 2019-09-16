(ns ocr-test.core
  (:import [java.awt Graphics2D Color Font]
           [java.awt.image BufferedImage]
           [javax.imageio ImageIO]
           [java.io File]
           [net.sourceforge.tess4j Tesseract])
  (:require 
            [clojure.string :as string]
            [clojure.java.io :as io]))


;; settings
(def tesseract-data-dir "./resources")
(def language "eng")
(def test-file "./resources/test/UHAPDMB3ZYTCADBDA.png")


(defn prepare-tesseract [data-path]
  (let [t (new Tesseract)]
    (.setDatapath t data-path)
    t))


(defn ocr [t lang img]
  (.setLanguage t lang)
  (.doOCR t img))

(defn -main []
  (println "Hello, World!" ))

(defn -main1 []
  (let [tesseract (prepare-tesseract tesseract-data-dir)
        result    (ocr tesseract language (new File test-file))]
    (println result)))


(def vin-chars "ABCDEFGHJKMNPRSTUVWXYZ0123456789")

(defn gen-vin-str [x] 
  (apply str 
    (take x (repeatedly #(rand-nth (seq vin-chars))))))


(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))

(defn str->img [string filename]
  (let [file (File. (str "./" filename ".png"))
        width 450
        height 100
        image (BufferedImage. width height BufferedImage/TYPE_INT_ARGB)
        graphics (.createGraphics image)
        font-size 20
        font (Font. "HYMyeongJo-Extra" Font/BOLD font-size)]
    (.setColor graphics Color/BLACK)
    (.setFont graphics font)
    (.drawString graphics string 10 25)
    (ImageIO/write image "png" file)))  

(defn split-vin [x] (string/split x #"[~%]"))
(defn new-file-name [x] (str ((split-vin x) 1) ".png"))
(defn get-files [] (map #(.getName %) (.listFiles (File. "./resources/cloud_test"))))

(str (split-vin (apply str (take 1 (get-files)))) 1)

(defn copy-file [x]
  (let [new-name (str "./resources/test/" (new-file-name x))
        old-name (str "./resources/cloud_test/" x)]
    (do
      (str "old: " old-name " new: " new-name)
      (io/copy (io/file old-name) (io/file new-name)))))      
      
(take 2(map #(copy-file %) (get-files)))  

(defn get-file-from [x]
   (map #(.getName %) (.listFiles (File. x))))
  
(defn scan-ocr [x]
  (let [tesseract (prepare-tesseract tesseract-data-dir)
        result    (ocr tesseract language (new File x))]
    result))

(defn scan-all [x]
  (map #(scan-ocr (str x "/" %)) (get-file-from x)))
    
  


