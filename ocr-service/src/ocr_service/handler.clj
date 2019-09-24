(ns ocr-service.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ocr-service.ocr :as ocr]))

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
    (println (str "filename " filename " tempfile: " tempfile " params: " params))
    (ocr/scan-ocr-file tempfile))
  (route/not-found "Not Found"))

          
      
    

(def app
  (wrap-defaults app-routes 
    (assoc-in site-defaults [:security :anti-forgery] false)))
