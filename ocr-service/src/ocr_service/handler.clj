(ns ocr-service.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ocr-service.ocr :as ocr]))

(defroutes app-routes
  (GET "/" [] "Hello World")
  (route/not-found "Not Found")
  (POST "/ocr" [file] (ocr/scan-ocr-file file)))

(def app
  (wrap-defaults app-routes site-defaults))
