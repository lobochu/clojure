(ns ocr-service.handler
  (:import 
           [java.io File])
  (:require 
            [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ocr-service.ocr :as ocr]
            [clojure.java.io :as io]))
  
            

(defn new-file [filename tempfile]
  (let [file (File. filename)]
    do (
        (println tempfile)
        (.renameTo (io/file tempfile) file) 
        (class file))))
    
  


(defroutes app-routes
  (GET "/" [] "Hello World")
  
  
  (POST "/ocr" request
    (let [file (-> request :file)
          content (-> request :content)]
      do(
         (println (str "file:-------->>>>>>>>>>" file  "content: " content))
         (ocr/scan-ocr-file content))))
  (POST "/ocr1"
    {form-params :form-params} "ocr1")
  (POST "/ocr2" request
    (str (:form-params request)))
  ;this line must be last !!
  (POST "/ocr3" 
    {{ {tempfile  :tempfile filename :filename} :file} :params :as params}
    (println (str "================================filename " filename " tempfile: " (.getName tempfile) " params: " params))
    (io/copy tempfile (io/file filename))
    (println (str "new file name" (.getName tempfile)))
    ;(ocr/scan-ocr-file (File. "resources/test/06L5ZP35RAPHZ9FUK.png"))
    (ocr/scan-ocr-file (io/file filename)))
  
  (route/not-found "Not Found"))

          
      
    

(def app
  (wrap-defaults app-routes 
    (assoc-in site-defaults [:security :anti-forgery] false)))
