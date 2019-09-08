(ns ocr-test.core
  (:import net.sourceforge.tess4j.Tesseract)
  (:import [java.awt Graphics2D Color Font]
           [java.awt.image BufferedImage]
           [javax.imageio ImageIO]
           [java.io File])
  (require '[clojure.string :as string]))


;; settings
(def tesseract-data-dir "/usr/share/tessdata")
(def language "eng")
(def test-file "eurotext.png")


(defn prepare-tesseract [data-path]
  (let [t (. Tesseract getInstance)]
    (.setDatapath t data-path)
    t))


(defn ocr [t lang img]
  (.setLanguage t lang)
  (.doOCR t img))


(defn -main []
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
(defn get-files [] (map #(.getName %) (.listFiles (File. "./resources/cloud_test"))))


